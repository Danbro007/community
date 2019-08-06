package lfie.danbro.community.community.dto;

import lombok.Data;

@Data
public class GitHubUser {
    private String login;
    private String bio;
    private Long id;
    private String avatarUrl;

}
