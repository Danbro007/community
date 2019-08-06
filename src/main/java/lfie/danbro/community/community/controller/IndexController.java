package lfie.danbro.community.community.controller;


import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.mapper.UserMapper;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {


    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "5") Integer size){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findtUserByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        PageInfo<Question> questions = questionService.getQuestionList(page,size);
        model.addAttribute("questions",questions);
        return "index";
    }

}
