package tests;

import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.TestNGListener;

import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

@Listeners(TestNGListener.class)

public class RegistrationTests extends ApplicationManager {

    @Test(groups = "smoke")
    public void registrationPositiveTest() {
        String email = generateEmail(10);
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm(email, "Pasword888!")
                .clickBtnRegistrationPositive()
                .isElementContactPresent());
    }

    @Test
    public void registrationNegativeTest_wrongEmail() {
        String email = generateString(10);
        UserDto user = new UserDto(email,"Qwerty123!");
        new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm(user)
                .clickBtnRegistrationNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage1("Registration failed with code 400")
        ;
    }
}