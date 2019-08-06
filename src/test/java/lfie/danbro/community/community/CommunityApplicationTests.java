package lfie.danbro.community.community;

import com.github.pagehelper.PageHelper;
import lfie.danbro.community.community.mapper.QuestionMapper;
import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

    @Autowired
    QuestionMapper questionMapper;

    @Test
    public void testPanigtion() {
        PageHelper.startPage(1, 5);
        List<Question> questions = questionMapper.getQuestionList();
        for (Question question : questions) {
            System.out.println(question.getTitle());
        }

    }

}
