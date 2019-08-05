package lfie.danbro.community.community.model;


import lombok.Data;

@Data
public class User {

    private String name;
    private Integer id;
    private String token;
    private String accountId;
    private Long gmtCreate;
    private Long gmtModified;

}
