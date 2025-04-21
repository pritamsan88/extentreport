package Pagefactory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class pagefactory {

    WebDriver driver = null;
    JavascriptExecutor js = null;
    Random ran;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    @FindBy(xpath = "//a[contains(text(),'Home')]")
    WebElement homepage;

    @FindBy(xpath = "//a/span[contains(text(),'Subscribe Now')]")
    WebElement subscribe;
    @FindBy(xpath = "//a[@href='https://thosewoofguys.com/my-account/']")
    WebElement myaccount1;
    @FindBy(id = "subs_pack")
    WebElement SubscriptionPlan;
    @FindBy(xpath = "//input[@type='radio']")
    List<WebElement> dogprofile;

    @FindBy(css = "h2.ordr_prdct")
    List<WebElement> subsorderpuct;


    @FindBy(css = "input.ordr_input")
    List<WebElement> subsorderpuctinput;

    @FindBy(xpath = "//a[@class='carts_btn not_match_limit_cart']")
    WebElement subscriptioncart;


    @FindBy(css = " div.site_btn_wppr > a")
    WebElement popupconfirm;


    String url = "https://thosewoofguys.com/my-account/";
    String subscribepage = "https://thosewoofguys.com/subscriptions/";

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


    public void myaccount() throws InterruptedException {
        Thread.sleep(2000);
        js.executeScript("arguments[0].scrollIntoView();", myaccount1);
        js.executeScript("arguments[0].click();", myaccount1);


    }


    public void newsubcription() throws InterruptedException {
        driver.navigate().to(subscribepage);
        js.executeScript("arguments[0].scrollIntoView();", SubscriptionPlan);
        Select dropdown = new Select(SubscriptionPlan);
        dropdown.selectByIndex(2);

        ran = new Random();
        int randomindex = ran.nextInt(dogprofile.size());

        WebElement pupprofile = dogprofile.get(randomindex);

        js.executeScript("arguments[0].click();", pupprofile);
        System.out.println(" Clicked random dog profile index: " + randomindex);
        Thread.sleep(300);
        for (int i = 0; i < subsorderpuct.size(); i++) {
            WebElement product = subsorderpuct.get(i);
            WebElement quantityinput = subsorderpuctinput.get(i);
            Thread.sleep(1000);
            js.executeScript("arguments[0].scrollIntoView(true);", product);
            quantityinput.clear();
            //String numberofquantity = quantity.get(i);
            //quantityinput.sendKeys(numberofquantity);
            quantityinput.sendKeys("10");

            String productname = product.getText().trim();
            String inputquantity = quantityinput.getAttribute("value");
            System.out.println("Selected product: " + productname + " | Quantity: " + inputquantity);
            // js.executeScript("arguments[0].click();", subscriptioncart);


        }
        Thread.sleep(500);


        // Scroll and click subscription cart
        js.executeScript("arguments[0].scrollIntoView(true);", subscriptioncart);
        Thread.sleep(500);
        js.executeScript("arguments[0].click();", subscriptioncart);
        //Thread.sleep(5000);
        Set<String> windowsIs = driver.getWindowHandles();
        System.out.print(windowsIs);
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(popupconfirm));
        // driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(5));
        js.executeScript("arguments[0].click();", popupconfirm);


    }

    public void dyanamicsubscription() throws InterruptedException {
        driver.navigate().to(subscribepage);
        js.executeScript("arguments[0].scrollIntoView();", SubscriptionPlan);
        Select dropdown = new Select(SubscriptionPlan);
        dropdown.selectByIndex(2);
        ran = new Random();
        int randomindex = ran.nextInt(dogprofile.size());
        WebElement pupprofile = dogprofile.get(randomindex);
        js.executeScript("arguments[0].click();", pupprofile);
        System.out.println(" Clicked random dog profile index: " + randomindex);
        Thread.sleep(300);

        List<String> pricetag = Arrays.asList("20", "0", "20","0","20");

            //throw new IllegalStateException("List sizes do not match!");
            for (int i = 0; i < subsorderpuct.size(); i++) {
                WebElement product = subsorderpuct.get(i);
                WebElement quantityinput = subsorderpuctinput.get(i);
                String ele = pricetag.get(i);
                Thread.sleep(1000);
                js.executeScript("arguments[0].scrollIntoView(true);", product);
                quantityinput.clear();
                quantityinput.sendKeys(ele);
                String productname = product.getText().trim();
                String inputquantity = quantityinput.getAttribute("value");
                System.out.println("Selected product: " + productname + " | Quantity: " + inputquantity);


            }


        Thread.sleep(500);


        // Scroll and click subscription cart
        js.executeScript("arguments[0].scrollIntoView(true);", subscriptioncart);
        Thread.sleep(500);
        js.executeScript("arguments[0].click();", subscriptioncart);
        Set<String> windowsIs = driver.getWindowHandles();
        System.out.print(windowsIs);
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.elementToBeClickable(popupconfirm));
        // driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(5));
        js.executeScript("arguments[0].click();", popupconfirm);

    }

}



