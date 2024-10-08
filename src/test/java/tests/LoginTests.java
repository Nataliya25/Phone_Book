package tests;

import org.openqa.selenium.TakesScreenshot;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.TestNGListener;

import static utils.TakeScreenShot.takeScreenShot;

@Listeners(TestNGListener.class)


public class LoginTests extends ApplicationManager {

    @Test
    public void loginPositiveTest() {
        boolean result = new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail@mail.com", "Qwerty123!")
                .clickBtnLoginPositive()
                .isElementContactPresent();

        takeScreenShot((TakesScreenshot) getDriver());

        Assert.assertTrue(result);
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail@mail.com", "Qwerty123!----")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage())
        ;
    }

    @Test
    public void loginNegativeTest_wrongEmail_UnregUser(){
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("WillClass123@mail.com", "Qwerty13333!")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage())
        ;
    }

    @Test
    public void loginNegativeTest_wrongEmail_WOAt(){
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("WillClass123mail.com", "Qwerty13333!")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage())
        ;
    }
}