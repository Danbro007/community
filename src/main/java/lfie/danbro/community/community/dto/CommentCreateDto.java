package lfie.danbro.community.community.dto;


import lombok.Data;

@Data
public class CommentCreateDto {
    private Long parentId;
    private int type;
    private String content;
}
