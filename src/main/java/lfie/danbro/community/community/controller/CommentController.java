package lfie.danbro.community.community.controller;


import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import lfie.danbro.community.community.dto.CommentDto;
import lfie.danbro.community.community.dto.ResultDto;
import lfie.danbro.community.community.model.Comment;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    //@RequestBody注解是把json反序列化成对象
    public Object comment(@RequestBody CommentDto commentDto,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
//        if (user == null){
//            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
//        }
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setContent(commentDto.getContent());
        comment.setLikeCount(0L);
        comment.setCommenter(1);
        comment.setType(commentDto.getType());
        commentService.insert(comment);
        return ResultDto.successOf(CustomizeErrorCode.SUCCESS);
    }

}
