package lfie.danbro.community.community.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import lfie.danbro.community.community.mapper.QuestionExtMapper;
import lfie.danbro.community.community.mapper.QuestionMapper;
import lfie.danbro.community.community.mapper.UserMapper;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.QuestionExample;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    /**
     * 获取分页后的问题列表
     *
     * @param page 页码
     * @param size 每页数量
     * @return 问题列表
     */
    public PageInfo<Question> getQuestionList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria();
        List<Question> questions = questionMapper.selectByExample(questionExample);
        PageInfo<Question> info = new PageInfo<>(questions);
        for (Question question : info.getList()) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            question.setUser(users.get(0));
        }
        return info;
    }

    /**
     * 通过用户id找到问题
     *
     * @param userId 用户id
     * @param page   页数
     * @param size   每页展示的问题数量
     * @return 问题列表
     */
    public PageInfo<Question> getQuestionByUserId(Integer userId, Integer page, Integer size) {
        //分页器
        PageHelper.startPage(page, size);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExample(questionExample);
        PageInfo<Question> info = new PageInfo<>(questions);
        for (Question question : info.getList()) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            question.setUser(users.get(0));
        }
        return info;
    }

    /**
     * 通过问题id查找问题
     *
     * @param id 问题id
     * @return 问题对象
     */
    public Question getQuestionById(Integer id) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);

        List<Question> questions = questionMapper.selectByExample(questionExample);
        if (questions.size() == 0) {
            throw new CustomizeExpection(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        Question question = questions.get(0);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(question.getCreator());
        question.setUser(userMapper.selectByExample(userExample).get(0));
        return question;
    }

    /**
     * 更新或者创建新的问题
     *
     * @param question 问题对象
     */
    public void updateOrInsert(Question question) {
        if (question.getId() == null) {
            questionMapper.insertSelective(question);
        } else {
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int i = questionMapper.updateByExampleSelective(question, questionExample);
            if (i != 1) {
                throw new CustomizeExpection(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }


    /**
     * 更新浏览数
     *
     * @param id 问题id
     */
    public void incQuestionView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incQuestionView(question);
    }
}
