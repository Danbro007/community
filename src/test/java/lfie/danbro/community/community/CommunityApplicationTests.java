package lfie.danbro.community.community;

import lfie.danbro.community.community.dto.CommentDto;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.mapper.CommentExtMapper;
import lfie.danbro.community.community.mapper.QuestionExtMapper;
import lfie.danbro.community.community.model.User;
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
        List<CommentDto> commentDtos = commentExtMapper.getCommentsByQuestionId(6L);
        for (CommentDto commentDto : commentDtos) {
            System.out.println(commentDto);
        }

    }





}
