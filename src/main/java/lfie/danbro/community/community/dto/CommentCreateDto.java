package lfie.danbro.community.community.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentCreateDto {
    private Long parentId;
    private Integer type;
    @NotBlank(message = "回答内容不能为空")
    private String content;
}
