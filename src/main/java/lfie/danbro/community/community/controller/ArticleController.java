package lfie.danbro.community.community.controller;


import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import lfie.danbro.community.community.model.Article;
import lfie.danbro.community.community.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/article/{id}")
    public String articlesView(Model model,
                               @PathVariable("id") Long id) {
        Article article = articleService.selectArticleById(id);
        if (article == null) {
            throw new CustomizeExpection(CustomizeErrorCode.ARTICLE_NOT_FOUND);
        }
        model.addAttribute("article", article);
        return "article";
    }


}
