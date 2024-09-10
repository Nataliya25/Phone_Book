package tests;

import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class LoginTests extends ApplicationManager {
    @Test
    public void LoginPositiveTest(){
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa44mail@gmail.com","Gywer!!222")
                .clickBtnLoginPositive()
                .isElementContantPresent());
    }
}
