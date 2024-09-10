package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@name='email']")
    WebElement inputEmail;

    @FindBy(xpath = "//input[@name='password']")
    WebElement inputPassword;

    @FindBy(xpath = "//button[@name='login']")
    WebElement btnLoginSubmit;

    @FindBy(xpath = "//button[@name='registration']")
    WebElement btnRegistration;

    public LoginPage typeLoginForm(String email, String password){
        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
        return this;
    }

    public ContactPage clickBtnLoginPositive(){
        btnLoginSubmit.click();
        return new ContactPage(driver);
    }

    public ContactPage clickBtnREgistrationPositive(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        btnRegistration.click();
        return new ContactPage(driver);
    }
    //qa44_mail@gmail.com
    //Gywer!!@
}
