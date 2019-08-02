package lfie.danbro.community.community.controller;


import lfie.danbro.community.community.dto.AccessTokenDto;
import lfie.danbro.community.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    GitHubProvider gitHubProvider;


    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id("06fdb966a8cbcf08c463");
        accessTokenDto.setClient_secret("a5d60bc8db4b9665ec82e31a04c809e57d4e4ea3");
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri("http://localhost:8888/callback");
        accessTokenDto.setState("1");
        gitHubProvider.getAccessToken(accessTokenDto);
        return "index";
    }

}
