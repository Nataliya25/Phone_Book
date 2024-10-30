package tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import data_provider.DPAddContact;
import dto.ContactDTOLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;

@Listeners(TestNGListener.class)

public class AddContactsTests extends ApplicationManager {

    UserDto user = new UserDto("qa_mail@mail.com", "Qwerty123!");
    AddPage addPage;

    @BeforeMethod(alwaysRun = true)
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);
    }

    @Test(groups = "smoke")
    public void addNewContactPositiveTest() {
        ContactDTOLombok contact = ContactDTOLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isLastPhoneEquals(contact.getPhone()))
        ;
    }

    @Test
    public void addNewContactNegativeTest_nameIsEmpty() {
        ContactDTOLombok contact = ContactDTOLombok.builder()
                .name("")
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .urlContainsAdd())
        ;
    }

    @Test
    public void addNewContactNegativeTest_wrongEmail() {
        ContactDTOLombok contact = ContactDTOLombok.builder()
                .name(generateString(4))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5))
        ;
    }

    @Test(dataProvider = "addNewContactDP", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongEmailDP(ContactDTOLombok contact) {
        System.out.println("--> " + contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5))
        ;
    }
    @Test(dataProvider = "addNewContactDPFile", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongEmailDPFile(ContactDTOLombok contact) {
        System.out.println("--> " + contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5))
        ;
    }
}