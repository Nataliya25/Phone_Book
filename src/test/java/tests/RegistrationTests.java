package tests;

import dto.UserDTO;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

import java.util.Random;

public class RegistrationTests extends ApplicationManager {

    @Test
    public void registrationPositiveTest() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("Email123!@gmail.com", "Pasword888!")
                .clickBtnRegistrationPositive()
                .isElementContactPresent());
    }

    @Test
    public void registrationNegativeTest_wrongEmail() {
        String email = generateString(10);
        UserDTO user = new UserDTO(email,"Qwerty123!");
        new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm(user)
                .clickBtnRegistrationNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage1("Registration failed with code 400")
        ;
    }
}