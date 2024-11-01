package okhttp;

import dto.*;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.bouncycastle.cert.ocsp.Req;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class DeletePhoneTests implements BaseApi {

    TokenDto token;
    String contactId;

    @BeforeClass
    public void loginUser(){
        UserDto user = new UserDto(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            token  = GSON.fromJson(response.body().string(), TokenDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeMethod
    public void addNewContact(){
        ContactDTOLombok contact = ContactDTOLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            System.out.println("add new contact response code:   " + response.code());
            String message = (GSON.fromJson(response.body().string(), ResponseMessageDto.class)).getMessage();
            System.out.println(message);
            contactId = message.substring(23);
            System.out.println(contactId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void deletePositiveTest(){

        Request request = new Request.Builder()
                .url(BASE_URL+GET_ALL_CONTACTS_PATH+"/"+contactId)
                .addHeader("Authorization", token.getToken())
                .delete()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(response.isSuccessful());
    }

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void deleteNagativeTest_ContactNotFound() throws IOException {

        Request request = new Request.Builder()
                .url(BASE_URL+GET_ALL_CONTACTS_PATH+"/"+contactId+"10000")
                .addHeader("Authorization", token.getToken())
                .delete()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ErrorMessageDTO errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDTO.class);

       // System.out.println("Response code:   "+response.code());
       // Assert.assertEquals(response.code(), 400);

        softAssert.assertEquals(response.code(),400);
        softAssert.assertEquals(errorMessage.getStatus(),400);
        softAssert.assertEquals(errorMessage.getError(),"Bad Request");
        System.out.println(errorMessage.getMessage());
        softAssert.assertTrue(errorMessage.getMessage().toString().contains("not found"));

        softAssert.assertAll();
    }
}
