package okhttp;

import dto.ContactDTOLombok;
import dto.ContactsDTO;
import dto.TokenDTO;
import dto.UserDTO;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;

public class GetAllPhonesTests implements BaseApi {


    TokenDTO token;

    @BeforeTest
    public void loginUser() {
        UserDTO user = new UserDTO(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            token = GSON.fromJson(response.body().string(), TokenDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // if(token != null){
        //     System.out.println(token.toString());
        // }else {
        //    System.out.println("Something went wrong !!!");
    }


    @Test
    public void getAllUserPhones_PositiveTest() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH)
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
    public void getAllUserPhonesPositiveTest_getContactList() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH)
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
          ContactsDTO contacts = GSON.fromJson(response.body().string(), ContactsDTO.class);
          for(ContactDTOLombok c: contacts.getContacts()){
              System.out.println(c);
          }
      }else{
          System.out.println("WRONG");
      }
    }
}