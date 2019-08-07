package lfie.danbro.community.community.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.mapper.QuestionMapper;
import lfie.danbro.community.community.mapper.UserMapper;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Question> questions = questionMapper.getAllQuestions();
        PageInfo<Question> info = new PageInfo<>(questions);
        for (Question question : info.getList()) {
            User user = userMapper.findUserById(question.getCreator());
            question.setUser(user);
        }
        return info;
    }

    public PageInfo<Question> getQuestionByUserId(Integer userId, Integer page, Integer size) {
        //分页器
        PageHelper.startPage(page, size);
        List<Question> questions = questionMapper.getQuestionsByUserId(userId);
        PageInfo<Question> info = new PageInfo<>(questions);
        for (Question question : info.getList()) {
            User user = userMapper.findUserById(question.getCreator());
            question.setUser(user);
        }
        return info;
    }

    /**
     * 通过问题id查找问题
     * @param id 问题id
     * @return 问题对象
     */
    public Question getQuestionById(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        question.setUser(userMapper.findUserById(question.getCreator()));
        return question;
    }

    /**
     * 添加问题
     * @param question 问题
     */
    public void addQuestion(Question question) {
        questionMapper.addQuestion(question);
    }

    /**
     * 更新或者创建新的问题
     * @param question 问题对象
     */
    public void updateOrInsert(Question question) {
        if (question.getId() == null){
            questionMapper.addQuestion(question);
        }else{
            questionMapper.updateQuestion(question);
        }
    }
}
