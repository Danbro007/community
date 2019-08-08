package lfie.danbro.community.community.service;


import lfie.danbro.community.community.Enum.CommentTypeEnum;
import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import lfie.danbro.community.community.mapper.CommentMapper;
import lfie.danbro.community.community.model.Comment;
import lfie.danbro.community.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionService questionService;

    /**
     * 添加评论
     * @param comment 要插入的评论
     */
    @Transactional//添加spring的事务注解
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeExpection(CustomizeErrorCode.COMMENT_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeExpection(CustomizeErrorCode.COMMENT_PARAM_ERROR);
        }
        //类型为评论
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //先查找父评论
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (parentComment == null){//找不到父评论抛出异常
                throw new CustomizeExpection(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {//类型为问题
            Question question = questionService.selectByPrimaryKey(comment.getParentId());
            if (question == null){
                throw new CustomizeExpection(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //添加评论
            commentMapper.insert(comment);
            //更新评论数
            questionService.increaseCommentCount(question.getId());
        }
    }
}
