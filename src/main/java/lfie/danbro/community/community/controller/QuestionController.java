package lfie.danbro.community.community.controller;


import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.service.CommentService;
import lfie.danbro.community.community.dto.CommentDto;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String questionView(@PathVariable("id") Long id,
                               @RequestParam(value = "page",defaultValue = "1")Integer page,
                               @RequestParam(value = "size",defaultValue = "5") Integer size,
                               Model model) {
        QuestionDto questionDto = questionService.getQuestionById(id);
        model.addAttribute("question", questionDto);
        questionService.increaseViewCount(questionDto.getId());
        PageInfo<CommentDto> commentDtos = commentService.getCommentsByQuestionId(id,page,size);
        model.addAttribute("comments", commentDtos);
        return "question";
    }

}
