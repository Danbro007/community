package lfie.danbro.community.community;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.SourceTree;
import lfie.danbro.community.community.Enum.CommentTypeEnum;
import lfie.danbro.community.community.dto.CommentDto;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.mapper.CommentExtMapper;
import lfie.danbro.community.community.mapper.CommentMapper;
import lfie.danbro.community.community.mapper.QuestionExtMapper;
import lfie.danbro.community.community.model.Comment;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.service.CommentService;
import lfie.danbro.community.community.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "lfie.danbro.community.community.mapper")
public class CommunityApplicationTests {

    @Autowired
    QuestionService questionService;

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


    @Test
    public void testGetReleatedQuestions(){
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(2L);
        questionDto.setTag("Java,spring");
        List<QuestionDto> relatedQuestions = questionService.getRelatedQuestions(questionDto);
        for (QuestionDto relatedQuestion : relatedQuestions) {
            System.out.println(relatedQuestion.getTitle());
        }
    }
    @Test
    public void testStringUtils(){
        String tag = "spring,Java";
        String replace = StringUtils.replace(tag, ",", "|");
        System.out.println(replace);
    }

}
