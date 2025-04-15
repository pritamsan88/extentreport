package Pagefactory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;

public class pagefactory {

    WebDriver driver = null;
    JavascriptExecutor js = null;
    @FindBy(id = "username")
    WebElement usernamefield;

    @FindBy(id = "password")
    WebElement passwordfield;

    @FindBy(xpath = "//button[@name='login']")
    WebElement submitbutton;

    @FindBy(xpath = "//h2[contains(text(),'Dashboard')]")
    WebElement verifydashboard;

    @FindBy(xpath = "//a[contains(text(),'Log out')]")
    WebElement logout1;

    String url = "https://thosewoofguys.com/my-account/";

    public pagefactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openurl() {

        driver.get(url);
        driver.manage().window().maximize();
    }


    public void username(String username) {
        usernamefield.sendKeys(username);
    }

    public void password(String password) {
        passwordfield.sendKeys(password);
    }

    public void loginbutton() {
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", submitbutton);
        js.executeScript("arguments[0].click();", submitbutton);
        //submitbutton.click();
    }

    public void verifydashboard() {
        String text = verifydashboard.getText().trim();
        if (text.contains("Dashboard")) {
            System.out.println("Welcome dashboard ");
        } else {
            System.out.println("You are not enter dashboard");
        }


    }

    public void logout() {
        logout1.click();
    }


}
