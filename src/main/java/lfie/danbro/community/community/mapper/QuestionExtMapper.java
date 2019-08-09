package lfie.danbro.community.community.mapper;

import lfie.danbro.community.community.model.Question;

public interface QuestionExtMapper {
    int increaseViewCount(Question question);
    int increaseCommentCount(Question question);
}