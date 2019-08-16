package lfie.danbro.community.community.controller;


import lfie.danbro.community.community.Enum.NotificationTypeEnum;
import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import lfie.danbro.community.community.model.Notification;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notificationView(@PathVariable("id") Long id,
                                   HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        Notification notification = notificationService.getNotificationByUserId(id, user.getId());
        if (notification == null){
            throw new CustomizeExpection(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getType() == NotificationTypeEnum.Reply_Comment.getType()
                || notification.getType() == NotificationTypeEnum.Reply_Question.getType()) {
            return "redirect:/question/" + notification.getOuterId();
        }else {
            return "redirect:/";
        }

    }

}
