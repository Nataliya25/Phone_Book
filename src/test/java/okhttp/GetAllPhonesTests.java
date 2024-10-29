package okhttp;

import dto.ContactDTOLombok;
import dto.ContactsDto;
import dto.TokenDto;
import dto.UserDto;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static utils.RandomUtils.generateEmail;
import static utils.PropertiesReader.getProperty;

public class GetAllPhonesTests implements BaseApi {

    TokenDto token;
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
//        if(token != null){
//            System.out.println(token.toString());
//        }else {
//            System.out.println("Something went wrong !!!");
//        }
    }

    @Test
    public void getAllUserPhonesPositiveTest(){
        Request request = new Request.Builder()
                .url(BASE_URL+GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .get()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void getAllUserPhonesNegativeTest_withoutToken_401(){
        Request request = new Request.Builder()
                .url(BASE_URL+GET_ALL_CONTACTS_PATH)
                .get()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(),403);
    }

    @Test
    public void getAllUserPhonesNegativeTest_wrongToken_401(){
        String wrongToken = token.getToken()+"123";
        Request request = new Request.Builder()
                .url(BASE_URL+GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", wrongToken)
                .get()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(),401);
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Test
    public void getAllUserPhonesPositiveTest_getContactList() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL+GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .get()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(response.isSuccessful()){
            ContactsDto contacts = GSON.fromJson(response.body().string(), ContactsDto.class);
            System.out.println(contacts);
            for (ContactDTOLombok c:contacts.getContacts()) {
                System.out.println(c);
            }
        }else
            System.out.println("Something went wrong ");
    }
}