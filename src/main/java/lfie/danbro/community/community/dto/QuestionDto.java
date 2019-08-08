package lfie.danbro.community.community.dto;


import lfie.danbro.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Long commentCount;
    private Long viewCount;
    private Long likeCount;
    private String tag;
    private User user;

}
