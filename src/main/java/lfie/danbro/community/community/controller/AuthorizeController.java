package lfie.danbro.community.community.controller;


import lfie.danbro.community.community.dto.AccessTokenDto;
import lfie.danbro.community.community.dto.GitHubUser;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.provider.GitHubProvider;
import lfie.danbro.community.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.sercet}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    UserService userService;

    @GetMapping("/login/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response,
                           HttpServletRequest request) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDto);
        GitHubUser gitHubUser = gitHubProvider.getGitUser(accessToken);
        //登录成功
        if (gitHubUser != null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setName(gitHubUser.getLogin());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            //添加用户到数据库里
            userService.updateOrInsert(user);
            //添加token到cookie里
            User dbUser = userService.selectUserByAccountId(user.getAccountId());
            response.addCookie(new Cookie("token",user.getToken()));
            request.getSession().setAttribute("user",dbUser);
            return "redirect:/";
            //登录失败
        }else {
//            log.error("github callback is error {}",gitHubUser);
            return "redirect:/";
        }
    }


    /**
     * 退出登录
      * @param request 请求
     * @param response 响应
     * @return 渲染index页面
     */
    @GetMapping("/logout")
    public String logOut(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String logIn(){
        return "login";
    }
}
