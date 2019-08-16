package lfie.danbro.community.community.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.Enum.CommentTypeEnum;
import lfie.danbro.community.community.Enum.NotificationStatusEnum;
import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import lfie.danbro.community.community.dto.CommentDto;
import lfie.danbro.community.community.mapper.*;
import lfie.danbro.community.community.model.Comment;
import lfie.danbro.community.community.model.Notification;
import lfie.danbro.community.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentExtMapper commentExtMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    QuestionMapper questionMapper;

    /**
     * 添加评论
     *
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
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {//找不到父评论抛出异常
                throw new CustomizeExpection(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null){//找不到问题抛出异常
                throw new CustomizeExpection(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insertSelective(comment);
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.increaseCommentCount(parentComment);
            createNotification(comment,comment.getContent(), dbComment.getCommenter(),CommentTypeEnum.COMMENT,question.getId());
        } else {//类型为问题
            Question question = questionService.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeExpection(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //添加评论
            commentMapper.insertSelective(comment);
            //更新评论数
            questionService.increaseCommentCount(question.getId());
            createNotification(comment,question.getTitle(),question.getCreator(),CommentTypeEnum.QUESTION,question.getId());


        }
    }

    /**
     * 通过通知者id 被通知者id 和评论类型插入一条通知记录
     * @param comment 评论对象
     * @param reciever 被通知者id
     * @param commentTypeEnum 评论类型
     */
    private void createNotification(Comment comment, String tittle, Integer reciever, CommentTypeEnum commentTypeEnum, Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommenter());
        notification.setOuterTittle(tittle);
        notification.setOuterId(outerId);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setType(commentTypeEnum.getType());
        notification.setReciever(reciever);
        notificationMapper.insert(notification);
    }


    /**
     * 通过问题ID找到所有问题的评论
     *
     * @return 评论
     */
    public PageInfo<CommentDto> getCommentsByTargetId(Long id, Integer page, Integer size, CommentTypeEnum type) {
        PageHelper.startPage(page, size);
        List<CommentDto> comments = commentExtMapper.getCommentsByTargetId(id, type.getType());
        return new PageInfo<>(comments);
    }
}
