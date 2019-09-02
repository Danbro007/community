package lfie.danbro.community.community.controller;


import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.dto.NotificationDto;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.service.NotificationService;
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
    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String viewProfile(@PathVariable(value = "action") String action,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size,
                              HttpServletRequest request,
                              Model model) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "index";
        } else {
            //点击了问题列表
            if (action.equals("questions")) {
                model.addAttribute("sectionName", "我的问题");
                model.addAttribute("section", action);
                PageInfo<QuestionDto> questionDtos = questionService.getAllQuestionDtosByUserId(user.getId(), page, size);
                model.addAttribute("questions", questionDtos);
            } else {
                //点击了回复列表
                model.addAttribute("sectionName", "我的回复");
                model.addAttribute("section", action);
            }
            PageInfo<NotificationDto> notifications = notificationService.getNotifications(user.getId(), page, size);
            Long unReadCount = notificationService.getUnReadCount(user.getId());
            model.addAttribute("unReadCount",unReadCount);
            model.addAttribute("notifications",notifications);
            return "profile";
        }
    }
}
