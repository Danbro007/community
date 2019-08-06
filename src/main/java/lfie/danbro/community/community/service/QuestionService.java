package lfie.danbro.community.community.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.mapper.QuestionMapper;
import lfie.danbro.community.community.mapper.UserMapper;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;


    /**
     * 获取分页后的问题列表
     *
     * @param page 页码
     * @param size 每页数量
     * @return 问题列表
     */
    public PageInfo<Question> getQuestionList(Integer page, Integer size) {
        //分页器
        PageHelper.startPage(page, size);
        List<Question> questions = questionMapper.getQuestionList();
        PageInfo<Question> info = new PageInfo<>(questions);
        for (Question question : info.getList()) {
            User user = userMapper.findUserById(question.getCreator());
            question.setUser(user);
        }
        System.out.println(info.getPageNum());
        return info;
    }


}
