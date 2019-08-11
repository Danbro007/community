package lfie.danbro.community.community.dto;

import lfie.danbro.community.community.model.Comment;
import lfie.danbro.community.community.model.User;
import lombok.Data;

@Data
public class CommentDto extends Comment {

    private User user;

}
