package lfie.danbro.community.community.controller;


import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;


    @GetMapping("/profile/{action}")
    public String viewProfile(@PathVariable(value = "action") String action,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "5") Integer size,
                              HttpServletRequest request,
                              Model model) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "index";
        } else {
            if (action.equals("questions")) {
                model.addAttribute("sectionName", "我的问题");
                model.addAttribute("section", action);
                System.out.println(user.getId());
                PageInfo<QuestionDto> questionDtos = questionService.getAllQuestionDtosByUserId(user.getId(), page, size);

                model.addAttribute("questions", questionDtos);
            } else {
                model.addAttribute("sectionName", "我的回复");
                model.addAttribute("section", action);
            }
            return "profile";
        }
    }
}
