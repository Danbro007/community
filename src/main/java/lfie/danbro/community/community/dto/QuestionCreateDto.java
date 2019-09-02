package lfie.danbro.community.community.dto;


import lombok.Data;

import javax.validation.constraints.*;
@Data
public class QuestionCreateDto {
    private Long id;

    @NotBlank(message = "标题不能为空!")
    private String title;

    @NotBlank(message = "标签不能为空!")
    private String tag;

    private String description;
}
