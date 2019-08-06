package lfie.danbro.community.community.controller;


import lfie.danbro.community.community.mapper.QuestionMapper;
import lfie.danbro.community.community.mapper.UserMapper;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/publish")
    public String publishView() {
        return "publish";
    }

    @PostMapping("/publish")
    public String addPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        } else {
            Question question = new Question();
            question.setTag(tag);
            question.setId(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setTitle(title);
            question.setDescription(description);
            questionMapper.addQuestion(question);
            return "redirect:/";
        }

    }
}

