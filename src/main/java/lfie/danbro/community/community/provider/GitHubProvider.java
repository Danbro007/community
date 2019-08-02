package lfie.danbro.community.community.provider;


import com.alibaba.fastjson.JSON;
import lfie.danbro.community.community.dto.AccessTokenDto;
import lfie.danbro.community.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {

    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            return string;
        } catch (IOException e) {
        }
        return null;
    }

    public GitHubUser getGitUser(String accessToken){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .get()//默认就是GET请求，可以不写
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String string = response.body().toString();
            GitHubUser user = JSON.parseObject(string,GitHubUser.class);
            return user;
        } catch (IOException e) {
        }
        return null;
    }


}
