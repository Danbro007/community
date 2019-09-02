package lfie.danbro.community.community.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.dto.ArticleDto;
import lfie.danbro.community.community.mapper.ArticleMapper;
import lfie.danbro.community.community.model.Article;
import lfie.danbro.community.community.model.ArticleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    public PageInfo<Article> selectAllArticleByExample(Integer page,Integer size){
        PageHelper.startPage(page,size);
        ArticleExample articleExample = new ArticleExample();
        List<Article> articles = articleMapper.selectByExample(articleExample);
        return new PageInfo<>(articles);
    }

    public Article selectArticleById(Long id){
        return articleMapper.selectByPrimaryKey(id);
    }

}
