package tests;

import dataProvider.DP_AddContact;
import dto.ContactDTOLombok;
import dto.UserDTO;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;


public class AddContactsTests extends ApplicationManager {

    UserDTO user = new UserDTO("qa_mail@mail.com", "Qwerty123!");
    AddPage addPage;


    @BeforeMethod
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);
    }

    @Test
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

    //HW-8  ________________________________________________
    @Test
    public void addNewCont_NegativeTest_WOUT_name() {  //similarly, we can remove each required field one by one
        ContactDTOLombok contact = ContactDTOLombok.builder()
                .name("")
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();

        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isElementContactPresent())
        ;
    }

    @Test
    public void addNewCont_NegativeTest_WrongEmail() {
        ContactDTOLombok contact = ContactDTOLombok.builder()
                .name(generateString(8))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();

        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .closeAlert_add()
                .isElementContactPresent())
        ;
    }

    @Test
    public void addNewCont_NegativeTest_WrongEmail_WithSpace() {
        ContactDTOLombok contact = ContactDTOLombok.builder()
                .name(generateString(8))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail_withSpace(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();

        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .closeAlert_add()
                .isElementContactPresent())
        ;
    }
    //-------------------------------------------------------------

    @Test
    public void addNewCont_NegativeTest_nameIsEmpty() {
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
    public void addNewCont_NegativeTest_wrongEmail() {
        ContactDTOLombok contact = ContactDTOLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(2))
                .address(generateString(20))
                .description(generateString(10))
                .build();

        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5))
        ;
    }

    @Test(dataProvider = "addNewContactDP", dataProviderClass = DP_AddContact.class)
    public void addNewContactNegativeTest_wrongEmailDP(ContactDTOLombok contact) {
        System.out.println("-->" + contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5))
        ;
    }

    @Test(dataProvider = "addNewContactDP_File", dataProviderClass = DP_AddContact.class)
    public void addNewContactNegativeTest_wrongEmailDP_File(ContactDTOLombok contact) {
        System.out.println("-->" + contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5))
        ;
    }
}