package lfie.danbro.community.community.controller;


import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.Enum.CommentTypeEnum;
import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.dto.CommentCreateDto;
import lfie.danbro.community.community.dto.CommentDto;
import lfie.danbro.community.community.dto.ResultDto;
import lfie.danbro.community.community.model.Comment;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseBody
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     *
     * @param commentCreateDto 接收到的评论对象
     * @param request 接收到请求
     * @return 新的评论
     */
    @PostMapping("/comment")
    public ResultDto comment(@Valid @RequestBody CommentCreateDto commentCreateDto,
                          BindingResult result,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (!result.hasErrors()){
            Comment comment = new Comment();
            BeanUtils.copyProperties(commentCreateDto,comment);
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(comment.getGmtCreate());
            comment.setCommenter(user.getId());
            comment.setLikeCount(0L);
            commentService.insert(comment);
            return ResultDto.successOf(CustomizeErrorCode.SUCCESS);
        }else {
            List<ObjectError> allErrors = result.getAllErrors();
            Map<String,String> errorMap = new HashMap<>();
            for (ObjectError error : allErrors) {
                FieldError fieldError = (FieldError) error;
                errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return ResultDto.errorOf(4007,errorMap);
        }
    }

    /**
     * 通过父评论id找到所有子评论
     * @param id 父评论id
     * @return 子评论
     */
    @GetMapping("/comment/{id}")
    public ResultDto getSubComment(@PathVariable("id") Long id,
                                   @RequestParam(value = "page",defaultValue = "1") Integer page,
                                   @RequestParam(value = "size",defaultValue = "5") Integer size){
        PageInfo<CommentDto> subComments = commentService.getCommentsByTargetId(id,page,size,CommentTypeEnum.COMMENT);
        return ResultDto.successOf(CustomizeErrorCode.SUCCESS,subComments) ;
    }

}
