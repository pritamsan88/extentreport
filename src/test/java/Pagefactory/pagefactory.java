package Pagefactory;

import org.openqa.selenium.WebDriver;

public class pagefactory {

    WebDriver driver=null;

    String url="https://thosewoofguys.com/my-account/";

   public pagefactory(WebDriver driver)
    {
        this.driver=driver;
    }

    public void openurl()
    {
        driver.get(url);
    }

}
