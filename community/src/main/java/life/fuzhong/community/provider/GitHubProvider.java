package life.fuzhong.community.provider;

import com.alibaba.fastjson.JSON;
import life.fuzhong.community.dto.AccessTokenDTO;
import life.fuzhong.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.rmi.AccessException;

@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String token = str.split("&")[0].split("=")[1];

            System.out.println(str);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public GitHubUser getUser(String accesstoken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .addHeader("Authorization", "Bearer " + accesstoken)
                .addHeader("Accept", "application/json")
                .build();

        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);


            return gitHubUser;
        }catch (IOException e) {
        }
        return null;

    }


}
