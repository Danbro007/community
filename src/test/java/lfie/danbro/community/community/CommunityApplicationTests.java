package lfie.danbro.community.community;

import com.github.pagehelper.PageInfo;
import lfie.danbro.community.community.Enum.CommentTypeEnum;
import lfie.danbro.community.community.dto.CommentDto;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.mapper.CommentExtMapper;
import lfie.danbro.community.community.mapper.CommentMapper;
import lfie.danbro.community.community.mapper.QuestionExtMapper;
import lfie.danbro.community.community.model.Comment;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "lfie.danbro.community.community.mapper")
public class CommunityApplicationTests {

    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    CommentExtMapper commentExtMapper;

    @Autowired
    CommentService commentService;
    @Test
    public void testGetAllQuestionDtos() {
        List<QuestionDto> questionDtos = questionExtMapper.getAllQuestionDtos();
        for (QuestionDto questionDto : questionDtos) {
            User user = questionDto.getUser();
            System.out.println(user.getAvatarUrl());
        }

    }

    @Test
    public void testGetCommentByQuestionID() {
        PageInfo<CommentDto> comments = commentService.getCommentsByTargetId(6L, 1, 5, CommentTypeEnum.QUESTION);
        for (CommentDto commentDto : comments.getList()) {
            System.out.println(commentDto);
        }
    }


    @Test
    public void testGetSubComments(){
        PageInfo<CommentDto> comments = commentService.getCommentsByTargetId(6L, 1, 5, CommentTypeEnum.COMMENT);
        for (CommentDto commentDto : comments.getList()) {
            System.out.println(commentDto);
        }
    }
}
