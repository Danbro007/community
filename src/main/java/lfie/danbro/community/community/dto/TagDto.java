package lfie.danbro.community.community.dto;

import lombok.Data;

import java.util.List;


@Data
public class TagDto {
    private String categoryEnName;
    private String categoryName;
    private List<String> tags;
}
