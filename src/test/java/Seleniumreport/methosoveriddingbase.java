package Seleniumreport;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class methosoveriddingbase {

    WebDriver driver;

    public void browseropen()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        String projecrurl1 = System.getProperty("user.dir");
        System.out.println(projecrurl1);
    }
    public void quit() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();

    }
}
