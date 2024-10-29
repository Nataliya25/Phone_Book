package okhttp;

import dto.ErrorMessageDTO;
import dto.UserDto;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.generateEmail_withSpace;



public class LoginTests implements BaseApi {

    @Test
    public void loginPostiveTest (){
        UserDto user = new UserDto(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response  = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(response.isSuccessful());
    }

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void loginNegativeTest_WrongEmail (){
        UserDto user = new UserDto(generateEmail_withSpace(10),"Wjfhdfsf45!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response  = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ErrorMessageDTO errorMessage = null;
        try {
            errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        softAssert.assertEquals(response.code(),401);
        softAssert.assertEquals(errorMessage.getStatus(),401);
        softAssert.assertEquals(errorMessage.getError(),"Unauthorized");
        //System.out.println(errorMessage.getMessage());
        softAssert.assertTrue(errorMessage.getMessage().toString().contains("Login or Password incorrect"));

        softAssert.assertAll();
    }
}
