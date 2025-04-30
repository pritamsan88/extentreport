package Seleniumreport;

import Pagefactory.pagefactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TrialPacks {

    WebDriver driver = null;
    pagefactory page;

    @BeforeTest
    public void browseropen() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        String projecrurl1 = System.getProperty("user.dir");
        System.out.println(projecrurl1);

    }

    @Test
    public void trialpack() throws InterruptedException {
        page = new pagefactory(driver);
        page.trialpage();
        page.trialpackageoperate();
        Thread.sleep(2000);

        ArrayList<String[]> credentials = new ArrayList<>();
        credentials.add(new String[]{"smith_doe@yopmail.com", "Testing$$$123"});

        for (String[] ele : credentials) {
            String email = ele[0];
            String pass = ele[1];

            page.username(email);
            page.password(pass);
            page.loginbutton();
            page.trialpackrandompuplist();
            page.deleteitem();
            page.emptycartvalidate();
            page.myaccount();
            page.logout();
        }


    }

    @AfterTest
    public void quit() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();

    }
}
