package pages;

import dto.ContactDTOLombok;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ContactPage extends BasePage {
    public ContactPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[text()='CONTACTS']")
    WebElement btnContact;
    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']//div[@class='contact-item_card__2SOIM'][last()]/h3")
    WebElement lastPhoneInList;

    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']//div[@class='contact-item_card__2SOIM']")
    WebElement firstContactOnList;

    @FindBy(xpath = "//button[text()='Remove']")
    WebElement btnRemoveContact;
    //================================
    @FindBy(xpath = "//button[text()='Edit']")
    WebElement btnEditContact;
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
    @FindBy(xpath = "//input[@placeholder='desc']")
    WebElement inputDescription;
    @FindBy(xpath = "//button[text()='Save']")
    WebElement btnSaveContact;
    //===============================
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/h2")
    WebElement contactCardNameLastName;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']")
    WebElement contactCardPhone;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/br")
    WebElement contactCardEmail;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/br[last()]")
    WebElement contactCardAddress;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/h3")
    WebElement contactCardDescription;

    public boolean isElementContactPresent() {
        return btnContact.isDisplayed();
    }

    public boolean isLastPhoneEquals(String phone) {
        return lastPhoneInList.getText().equals(phone);
    }

    public boolean urlContainsAdd() {
        return urlContains("add", 3);
    }

    public boolean isAlertPresent(int time) {
        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(time))
                    .until(ExpectedConditions.alertIsPresent());
            System.out.println(alert.getText());
            alert.accept();
            return true;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clickFirstElementOfContactsList() {
        firstContactOnList.click();
    }

    public void clickBtnRemoveContact() {
        btnRemoveContact.click();
    }

    public int getContactNumber() {
        pause(2);
        return driver.findElements(By.xpath("//div[@class='contact-item_card__2SOIM']")).size();
    }

    public void fillEditForm(ContactDTOLombok newContact) {
        btnEditContact.click();
        inputName.clear();
        inputName.sendKeys(newContact.getName());
        inputLastName.clear();
        inputLastName.sendKeys(newContact.getLastName());
        inputPhone.clear();
        inputPhone.sendKeys(newContact.getPhone());
        inputEmail.clear();
        inputEmail.sendKeys(newContact.getEmail());
        inputAddress.clear();
        inputAddress.sendKeys(newContact.getAddress());
        inputDescription.clear();
        inputDescription.sendKeys(newContact.getDescription());
    }

    public void clickBtnSaveContact() {
        btnSaveContact.click();
    }

    public ContactDTOLombok getContactFromDetailedCard() {
        pause(3);
        //System.out.println("name lastName --> " +contactCardNameLastName.getText());
        //System.out.println("phone --> "+contactCardPhone.getText());
        //System.out.println("email --> "+contactCardEmail.getText());
        //System.out.println("address --> "+contactCardAddress.getText());
        //System.out.println("desc --> "+contactCardDescription.getText());

        String name = contactCardNameLastName.getText().split(" ")[0]; //new-n8k2u new-krq036xo9y
        String lastName = contactCardNameLastName.getText().split(" ")[1];
        String phone = contactCardPhone.getText().split("\n")[1];
        String email = contactCardPhone.getText().split("\n")[2];
        String address = contactCardPhone.getText().split("\n")[3];
        String description = contactCardDescription.getText().split(": ")[1];

        ContactDTOLombok contact = ContactDTOLombok.builder()
                .name(name)
                .lastName(lastName)
                .phone(phone)
                .address(address)
                .email(email)
                .description(description)
                .build();
        System.out.println(contact);
        return contact;
    }

    public int getQuantityListContact() {
        return new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='contact-item_card__2SOIM']"))).size();
        //return driver.findElements(By.xpath("//div[@class='contact-item_card__2SOIM']")).size();
    }

    public boolean isElementDeleteFromContactList(int quantityBeforeDelete) {
        try {
            List<WebElement> listEl = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                    ExpectedConditions.numberOfElementsToBeLessThan(
                            By.xpath("//div[@class='contact-item_card__2SOIM']"), quantityBeforeDelete));
            System.out.println("after delete --> " + listEl.size());
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }
}