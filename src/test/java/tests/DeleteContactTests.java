package tests;

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

public class DeleteContactTests extends ApplicationManager {

    UserDTO user = new UserDTO("qa_mail@mail.com", "Qwerty123!");
    ContactPage contactPage;


    @BeforeMethod
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        contactPage = loginPage.typeLoginForm(user).clickBtnLoginPositive();
    }

    @Test
    public void deletePositiveTest(){
        int quantityBeforeDelete = contactPage.getContactNumber();
        System.out.println("--> " + quantityBeforeDelete);
        contactPage.clickFirstElementOfContactsList();
        contactPage.clickBtnRemoveContact();
        int quantityAfterDelete = contactPage.getContactNumber();
        System.out.println("--> " + quantityAfterDelete);
        Assert.assertEquals(quantityBeforeDelete - 1, quantityAfterDelete);
    }
}
