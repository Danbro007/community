package lfie.danbro.community.community.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import lfie.danbro.community.community.Enum.CommentTypeEnum;
import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import lfie.danbro.community.community.dto.CommentDto;
import lfie.danbro.community.community.mapper.CommentMapper;
import lfie.danbro.community.community.mapper.UserMapper;
import lfie.danbro.community.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserMapper userMapper;

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
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (parentComment == null) {//找不到父评论抛出异常
                throw new CustomizeExpection(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {//类型为问题
            Question question = questionService.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeExpection(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //添加评论
            commentMapper.insert(comment);
            //更新评论数
            questionService.increaseCommentCount(question.getId());
        }
    }


    /**
     * 通过问题ID找到所有问题的评论
     *
     * @return 评论
     */
    public PageInfo<Comment> getCommentByQuestionId(Long id, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        if (comments.size() == 0) {
            return new PageInfo<>(new ArrayList<>());
        }
        //找到所有评论这个问题的用户ID集合
        Set<Integer> commenters = comments.stream().map(comment -> comment.getCommenter()).collect(Collectors.toSet());
        List<Integer> userIdList = new ArrayList<>();
        userIdList.addAll(commenters);
        //获得user的对象
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIdList);
        List<User> users = userMapper.selectByExample(userExample);
        //得到user的map k->user.id v->user
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //comment转换成commentDto
//        List<CommentDto> commentDtos = comments.stream().map(comment -> {
//            CommentDto commentDto = new CommentDto();
//            BeanUtils.copyProperties(comment, commentDto);
//            commentDto.setUser(userMap.get(commentDto.getCommenter()));
//            return commentDto;
//        }).collect(Collectors.toList());

        for (Comment comment : commentPageInfo.getList()) {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setUser(userMap.get(comment.getCommenter()));
            comment = commentDto;
//            commentPageInfo.getList().add(commentDto);
        }

        return commentPageInfo;
    }
}
