package lfie.danbro.community.community.dto;


import lfie.danbro.community.community.model.Question;
import lfie.danbro.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDto extends Question {
    private User user;
}
