package pages;

import dto.ContactDTOLombok;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddPage extends BasePage{

    public AddPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputName;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastName;
    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputPhone;
    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement inputEmail;
    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement inputAddress;
    @FindBy(xpath = "//input[@placeholder='description']")
    WebElement inputDescription;

    @FindBy(xpath = "//button/b")
    WebElement btnSaveContact;

    public AddPage fillContactForm(ContactDTOLombok contact) {

        inputName.sendKeys(contact.getName());
        inputLastName.sendKeys(contact.getLastName());
        inputPhone.sendKeys(contact.getPhone());
        inputEmail.sendKeys(contact.getEmail());
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.sendKeys(contact.getDescription());

        return this;
    }

    public ContactPage clickBtnSaveContactPositive(){
        btnSaveContact.click();
        return new ContactPage(driver);
    }

    public AddPage clickBtnSaveContactNegative(){
        btnSaveContact.click();
        return this;
    }

    public boolean isElementContactPresent(){
        return btnSaveContact.isDisplayed();
    }

    public AddPage closeAlert_add() {
        pause(3);
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.alertIsPresent());

        System.out.println(alert.getText());
        alert.accept();
        return new AddPage(driver);
    }


}
