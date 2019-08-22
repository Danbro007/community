package lfie.danbro.community.community.controller;


import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.cache.HotTagCache;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;
    @Autowired
    HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                        @RequestParam(value = "search", required = false) String search,
                        @RequestParam(value = "tag",required = false) String tag){
        PageInfo<QuestionDto> questionDtos = questionService.getALLQuestionDtos(page, size, search,tag);
        model.addAttribute("hotTags",hotTagCache.getPriorityQueue());
        model.addAttribute("questions", questionDtos);
        return "index";
    }


}
