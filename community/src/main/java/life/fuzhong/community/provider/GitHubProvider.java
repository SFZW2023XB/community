package life.fuzhong.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import life.fuzhong.community.dto.AccessTokenDTO;
import life.fuzhong.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);

        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("scope", "user_info")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP error: " + response.code());
            }

            String responseBody = response.body().string();
            //
            System.out.println("Response: " + responseBody);

            JSONObject json = JSON.parseObject(responseBody);

            if (json.containsKey("access_token")) {
                String accessToken = json.getString("access_token");
                //
                System.out.println(accessToken);
                return accessToken;

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

//        public GitHubUser getUser(String accesstoken) {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://gitee.com/api/v5/user")
//                .addHeader("Authorization", "Bearer " + accesstoken)
//
//                .build();
//
//        try{
//            Response response = client.newCall(request).execute();
//            String string = response.body().string();
//            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
//            System.out.println(string);
//
//            return gitHubUser;
//        }catch (IOException e) {
//        }
//        return null;
//
//    }

    public GitHubUser getUser(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalArgumentException("Access token is null or empty");
        }
        System.out.println("Access Token: " + accessToken); // 打印access token
        OkHttpClient client = new OkHttpClient();
        //
        System.out.println("Authorization Header: Bearer " + accessToken);
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("User-Agent", "Your-App-Name")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("HTTP error: " + response.code());
                System.out.println("Response body: " + response.body().string());
                throw new IOException("HTTP error: " + response.code());
            }

            String responseBody = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(responseBody, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
