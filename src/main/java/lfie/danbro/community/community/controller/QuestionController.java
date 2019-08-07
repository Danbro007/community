package lfie.danbro.community.community.controller;


import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String questionView(@PathVariable("id") Integer id,
                               Model model){
        Question question = questionService.getQuestionById(id);
        model.addAttribute("question",question);
        questionService.incQuestionView(question.getId());
        return "question";
    }

}
