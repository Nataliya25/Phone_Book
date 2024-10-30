package okhttp;

import dto.*;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.*;

public class UpdateContactTests implements BaseApi {

    TokenDto token;

    ContactDTOLombok contact;


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
            // System.out.println("Contacts:    " + contacts);
            contact = contacts.getContacts()[0];
            System.out.println("Contact:   "+ contact);
        }else
            System.out.println("Something went wrong ");
    }


    @Test
    public void updateContactPositiveTest(){  //contact    update    contactNew
        ContactDTOLombok contactNew = ContactDTOLombok.builder()
                .id(contact.getId())
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(contactNew), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .put(requestBody)
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
    public void updateContactNegativeTest_WrongPhone() throws IOException {
        ContactDTOLombok contactNew = ContactDTOLombok.builder()
                .id(contact.getId())
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(30))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(contactNew), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .put(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ErrorMessageDTO errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDTO.class);
        System.out.println(errorMessage.getStatus());
        System.out.println(errorMessage.getError());
        Assert.assertEquals(errorMessage.getStatus(), 400);
    }

    @Test
    public void updateContactNegativeTest_ContactNotFound() throws IOException {  //contact    update    contactNew
        ContactDTOLombok contactNew = ContactDTOLombok.builder()
                .id(contact.getId()+"6f6vjygy")
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(contactNew), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .put(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ErrorMessageDTO errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDTO.class);
        //System.out.println(errorMessage.getStatus());
        System.out.println("MESSAGE:   " + errorMessage.getMessage().toString());
        //Assert.assertEquals(errorMessage.getStatus(),400);
        Assert.assertTrue(errorMessage.getMessage().toString().contains("not found"));
    }
    }



