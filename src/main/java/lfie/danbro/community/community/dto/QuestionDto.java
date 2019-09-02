package lfie.danbro.community.community.dto;


import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.User;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "question",type = "question")

public class QuestionDto extends Question {
    private User user;
}
