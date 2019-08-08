package lfie.danbro.community.community.controller;


import lfie.danbro.community.community.dto.CommentDto;
import lfie.danbro.community.community.mapper.CommentMapper;
import lfie.danbro.community.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {
    @Autowired
    CommentMapper commentMapper;


    @ResponseBody
    @PostMapping("/comment")
    //@RequestBody注解是把json反序列化成对象
    public Object comment(@RequestBody CommentDto commentDto){
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setContent(commentDto.getContent());
        comment.setLikeCount(0L);
        comment.setCommenter(1);
        comment.setType(commentDto.getType());
        commentMapper.insert(comment);
        return null;
    }

}
