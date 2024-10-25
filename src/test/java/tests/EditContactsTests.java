package tests;

import dto.UserDTO;
import manager.ApplicationManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonsOnHeader;

public class EditContactsTests  extends ApplicationManager {


    UserDTO user = new UserDTO("qa_mail@mail.com", "Qwerty123!");
    ContactPage contactPage;


    @BeforeMethod
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        contactPage = loginPage.typeLoginForm(user).clickBtnLoginPositive();
    }
    @Test
    public void editContactPositiveTest(){


    }


}
