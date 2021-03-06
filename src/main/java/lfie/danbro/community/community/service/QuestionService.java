package lfie.danbro.community.community.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import lfie.danbro.community.community.mapper.QuestionMapper;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.QuestionExample;
import lfie.danbro.community.community.model.UserExample;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.mapper.QuestionExtMapper;
import lfie.danbro.community.community.mapper.UserMapper;
import lfie.danbro.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public PageInfo<QuestionDto> getALLQuestionDtos(Integer page, Integer size,String search,String tag) {
        PageHelper.startPage(page, size);
        List<QuestionDto> questionDtos = questionExtMapper.getAllQuestionDtos(StringUtils.replace(search, " ", "|"),tag);
       return new PageInfo<>(questionDtos);
    }

    /**
     * 通过用户id找到问题
     *
     * @param userId 用户id
     * @param page   页数
     * @param size   每页展示的问题数量
     * @return 问题列表
     */
    public PageInfo<QuestionDto> getAllQuestionDtosByUserId(Integer userId, Integer page, Integer size) {
        //分页器
        PageHelper.startPage(page, size);
        List<QuestionDto> questionDtos = questionExtMapper.getAllQuestionDtosByUserId(userId);
        return new PageInfo<>(questionDtos);
    }

    /**
     * 通过问题id查找问题
     *
     * @param id 问题id
     * @return 问题对象
     */
    public QuestionDto getQuestionById(Long id) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);

        List<Question> questions = questionMapper.selectByExample(questionExample);
        if (questions.size() == 0) {
            throw new CustomizeExpection(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        Question question = questions.get(0);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(question.getCreator());
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        questionDto.setUser(userMapper.selectByExample(userExample).get(0));
        return questionDto;
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
    public void increaseViewCount(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1L);
        questionExtMapper.increaseViewCount(question);
    }

    public void increaseCommentCount(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setCommentCount(1L);
        questionExtMapper.increaseCommentCount(question);
    }

    /**
     * 通过问题id查找问题
     *
     * @param id 问题id
     * @return 查找的问题
     */
    public Question selectByPrimaryKey(Long id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    /**
     * 通过问题的tag属性找到相关的问题
     * @param questionDto 要查询相关的问题
     * @return 相关问题列表
     */
    public List<QuestionDto> getRelatedQuestions(QuestionDto questionDto) {
        QuestionDto relatedQuestionDto = new QuestionDto();
        relatedQuestionDto.setTag(StringUtils.replace(questionDto.getTag(),",","|"));
        relatedQuestionDto.setId(questionDto.getId());
        return  questionExtMapper.getRelatedQuestions(relatedQuestionDto);
    }
}
