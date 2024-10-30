package tests;

import dto.ContactDTOLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.RetryAnalyzer;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

import static utils.PropertiesReader.getProperty;

public class EditContactsTests extends ApplicationManager {

    //UserDto user = new UserDto("qa_mail@mail.com", "Qwerty123!");
    UserDto user = new UserDto(getProperty("data.properties","email"),
            getProperty("data.properties","password"));
    ContactPage contactPage;

    @BeforeMethod(alwaysRun = true)
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        contactPage = loginPage.typeLoginForm(user).clickBtnLoginPositive();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, groups = "smoke")
    public void editContactPositiveTest(){
        ContactDTOLombok newContact = ContactDTOLombok.builder()
                .name("new-"+generateString(5))
                .lastName("new-"+generateString(10))
                .phone("000"+generatePhone(7))
                .email("new-"+generateEmail(12))
                .address("new-"+generateString(20))
                .description("new-"+generateString(10))
                .build();
        contactPage.clickFirstElementOfContactsList();
        contactPage.fillEditForm(newContact);
        contactPage.clickBtnSaveContact();
        ContactDTOLombok contact = contactPage.getContactFromDetailedCard();
        Assert.assertEquals(newContact, contact);
    }
}