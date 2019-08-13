package lfie.danbro.community.community.controller;


import lfie.danbro.community.community.cache.TagCache;
import lfie.danbro.community.community.dto.TagDto;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @PostMapping("/publish")
    public String addPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tags,
            @RequestParam("id") Long id,
            HttpServletRequest request,
            Model model
    ) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        } else {
            String invalid = TagCache.filterTag(tags);
            if (!StringUtils.isEmpty(invalid)) {
                model.addAttribute("error", "含有非法标签" + invalid);
                return "publish";
            }
            Question question = new Question();
            question.setId(id);
            question.setTag(tags);
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setTitle(title);
            question.setDescription(description);
            questionService.updateOrInsert(question);
            return "redirect:/";
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

