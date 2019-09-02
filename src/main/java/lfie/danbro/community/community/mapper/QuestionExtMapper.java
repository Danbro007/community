package lfie.danbro.community.community.mapper;

import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionExtMapper {
    int increaseViewCount(Question question);
    int increaseCommentCount(Question question);
    List<QuestionDto> getAllQuestionDtos();
    List<QuestionDto> getAllQuestionDtosByUserId(Integer id);
    List<QuestionDto> getRelatedQuestions(Question question);
    Integer getRelatedQuestionNumByTag(String tag);
}