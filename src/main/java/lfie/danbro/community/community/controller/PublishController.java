package lfie.danbro.community.community.controller;


import lfie.danbro.community.community.cache.TagCache;
import lfie.danbro.community.community.dto.QuestionCreateDto;
import lfie.danbro.community.community.dto.TagDto;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish")
    public String publishView(Model model) {
        List<TagDto> tagList = TagCache.getTagList();
        model.addAttribute("tags", tagList);
        return "publish";
    }

    /**
     *
     * @param questionCreateDto 接收到的问题数据
     * @param result 数据校验结果
     * @param request 接收到的请求
     * @param model
     * @return 网页
     */
    @PostMapping("/publish")
    public String addPublish(@Valid QuestionCreateDto questionCreateDto,
                             BindingResult result,
                             HttpServletRequest request,
                             Model model
    ) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        } else {
            Map<String,String> errorsMap = new HashMap<>();
            if (!result.hasErrors()) {
                String invalid = TagCache.filterTag(questionCreateDto.getTag());
                if (!StringUtils.isEmpty(invalid)) {
                    errorsMap.put("tag","含有非法标签" + invalid);
                    model.addAttribute("errors", errorsMap);
                    List<TagDto> tagList = TagCache.getTagList();
                    model.addAttribute("tags", tagList);
                    model.addAttribute("title",questionCreateDto.getTitle());
                    model.addAttribute("description",questionCreateDto.getDescription());
                    model.addAttribute("tag",questionCreateDto.getTag());
                    return "publish";
                }else {
                    Question question = new Question();
                    BeanUtils.copyProperties(questionCreateDto, question);
                    question.setCreator(user.getId());
                    question.setGmtCreate(System.currentTimeMillis());
                    question.setGmtModified(question.getGmtCreate());
                    questionService.updateOrInsert(question);
                    return "redirect:/";
                }

            } else {
                List<ObjectError> errors = result.getAllErrors();
                for (ObjectError error : errors) {
                    FieldError fieldError = (FieldError) error;
                    errorsMap.put(fieldError.getField(),error.getDefaultMessage());
                }
                model.addAttribute("title",questionCreateDto.getTitle());
                model.addAttribute("description",questionCreateDto.getDescription());
                model.addAttribute("tag",questionCreateDto.getTag());
                model.addAttribute("errors",errorsMap);
                List<TagDto> tagList = TagCache.getTagList();
                model.addAttribute("tags", tagList);
                return "publish";
            }
        }
    }

    @GetMapping("/publish/{id}")
    public String publishEditOrAdd(@PathVariable("id") Long id,
                                   Model model) {
        List<TagDto> tagList = TagCache.getTagList();
        QuestionDto questionDto = questionService.getQuestionById(id);
        model.addAttribute("title", questionDto.getTitle());
        model.addAttribute("description", questionDto.getDescription());
        model.addAttribute("tag", questionDto.getTag());
        model.addAttribute("id", questionDto.getId());
        model.addAttribute("tags", tagList);
        return "publish";
    }


}

