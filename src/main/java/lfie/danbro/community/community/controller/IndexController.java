package lfie.danbro.community.community.controller;


import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.cache.HotTagCache;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.mapper.ArticleMapper;
import lfie.danbro.community.community.model.Article;
import lfie.danbro.community.community.model.ArticleExample;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.repository.QuestionRepository;
import lfie.danbro.community.community.service.ArticleService;
import lfie.danbro.community.community.service.QuestionService;
import lfie.danbro.community.community.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;
    @Autowired
    HotTagCache hotTagCache;

    @Autowired
    ArticleService articleService;
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                        @RequestParam(value = "search", required = false) String search,
                        @RequestParam(value = "tag", required = false) String tag,
                        @RequestParam(value = "category",defaultValue = "question") String category) {
        if (category.equals("question")) {
            if (search != null){
                Pageable pageable = PageRequest.of(page-1, size);
                Page<Question> questionsPageInfo = questionRepository.findByTitleLike(search, pageable);
                List<Integer> pageList = PageUtils.getPageList(questionsPageInfo.getTotalPages(), page, 5);
                model.addAttribute("questionsPageInfo",questionsPageInfo);
                model.addAttribute("pagelist",pageList);
                model.addAttribute("search",search);
            }
            else {
                PageInfo<QuestionDto> questionDtos = questionService.getALLQuestionDtos(page, size);
                model.addAttribute("questions", questionDtos);
            }
        }
        else if (category.equals("article")) {
            PageInfo<Article> articles = articleService.selectAllArticleByExample(page, size);
            model.addAttribute("articles",articles);
        }
        model.addAttribute("hotTags", hotTagCache.getPriorityQueue());
        return "index";
    }


}
