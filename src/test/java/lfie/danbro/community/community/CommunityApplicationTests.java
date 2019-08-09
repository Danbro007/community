package lfie.danbro.community.community;

import com.github.pagehelper.PageHelper;
import lfie.danbro.community.community.dto.QuestionDto;
import lfie.danbro.community.community.mapper.QuestionExtMapper;
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
    QuestionExtMapper questionExtMapper;

    @Test
    public void testPanigtion() {


    }

}
