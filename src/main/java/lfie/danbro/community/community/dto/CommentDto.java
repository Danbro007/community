package lfie.danbro.community.community.dto;


import lombok.Data;

@Data
public class CommentDto {
    private Long parentId;
    private int type;
    private String content;
}
