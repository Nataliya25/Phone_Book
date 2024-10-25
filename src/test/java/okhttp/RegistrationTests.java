package okhttp;

import dto.ErrorMessageDTO;
import dto.TokenDTO;
import dto.UserDTO;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static utils.RandomUtils.*;


public class RegistrationTests implements BaseApi {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void registrationPositiveTest(){
        UserDTO user = new UserDTO(generateEmail(10),"Yjfhdfsf456!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_PATH)
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

    @Test
    public void registrationPositiveTest_validateToken() throws IOException {
        UserDTO user = new UserDTO(generateEmail(10),"Yjfhdfsf456!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response  = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(response.isSuccessful()){
            TokenDTO token = GSON.fromJson(response.body().string(),TokenDTO.class);
            System.out.println("TOKEN:  " + token.getToken());
            Assert.assertEquals(response.code(),200);
        }else{
            ErrorMessageDTO errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDTO.class);
            System.out.println("ERROR:  " + errorMessage.getError());
            Assert.fail("response code -->" + response.code());
        }
    }

    @Test
    public void registrationNegativeTest_() throws IOException {
        UserDTO user = new UserDTO(generateEmail_withSpace(10),"Wjfhdfsf45!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response  = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ErrorMessageDTO errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDTO.class);
       softAssert.assertEquals(response.code(),400);
       softAssert.assertEquals(errorMessage.getStatus(),400);
       softAssert.assertEquals(errorMessage.getError(),"Bad Request");
       softAssert.assertTrue(errorMessage.getMessage().toString().contains("must be a well-formed email address"));
       softAssert.assertAll();
    }

}
