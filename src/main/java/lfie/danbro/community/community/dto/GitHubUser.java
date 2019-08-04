package lfie.danbro.community.community.dto;

public class GitHubUser {
    private String login;
    private String bio;
    private Long id;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GitHubUser{" +
                "login='" + login + '\'' +
                ", bio='" + bio + '\'' +
                ", id=" + id +
                '}';
    }
}
