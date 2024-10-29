package tests;

import dto.UserDto;
import manager.ApplicationManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonsOnHeader;

public class EditContactsTests  extends ApplicationManager {


    UserDto user = new UserDto("qa_mail@mail.com", "Qwerty123!");
    ContactPage contactPage;


    @BeforeMethod(alwaysRun = true)
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        contactPage = loginPage.typeLoginForm(user).clickBtnLoginPositive();
    }
    @Test(groups = "smoke")
    public void editContactPositiveTest(){


    }


}
