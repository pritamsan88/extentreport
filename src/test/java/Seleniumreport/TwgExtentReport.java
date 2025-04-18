package Seleniumreport;

import Pagefactory.pagefactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;

public class TwgExtentReport {

    WebDriver driver = null;
    ExtentSparkReporter htmlreporter;
    ExtentReports extent;
    ExtentTest test;
    pagefactory page;
    ArrayList<String[]> logincredentials;

    @BeforeClass
    public void reportsetup() {

        htmlreporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/extentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlreporter);
        htmlreporter.config().setDocumentTitle("Twg Automation");
        htmlreporter.config().setTheme(Theme.DARK);
        htmlreporter.config().setReportName("TWg Automation Functionality");
        htmlreporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

    }

    @BeforeTest
    public void browseropen() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        String projecturl = System.getProperty("user.dir");
        System.out.println(projecturl);


    }

    @BeforeMethod
    public void createtest(Method method) {
        test = extent.createTest(method.getName());
    }

    /*@DataProvider(name = "logincredentials")
    public Object[][] logindetails() {
        return new Object[][]{{"pritam.sanyal@yopmail.com", "Sanyal88888@@"}, {"smith_doe@yopmail.com", "Testing$$$123"}};

    }*/

    @Test

    public void logintest() throws InterruptedException {
        test.log(Status.INFO, "Start test login  ");
        page = new pagefactory(driver);
        page.openurl();

        logincredentials = new ArrayList<>();
        logincredentials.add(new String[]{"pritam.sanyal@yopmail.com", "Sanyal88888@@"});
        // logincredentials.add(new String[]{"smith_doe@yopmail.com", "Testing$$$123"});

        for (String[] ele : logincredentials) {
            String usernamefield = ele[0];
            String passwordfield = ele[1];

            page.username(usernamefield);
            page.password(passwordfield);
            page.loginbutton();
            test.log(Status.INFO, "verify dashboard");
            page.verifydashboard();
            test.log(Status.INFO, "Home page");
            // page.homepage();
            test.log(Status.INFO, "Subscribe page");
            page.newsubcription();

            test.log(Status.INFO, "My account ");
            page.myaccount();
            test.log(Status.INFO, "Logout functionality");
            page.logout();


        }
    }


    @AfterMethod
    public void getresult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {

            test.log(Status.FAIL, result.getThrowable());
            String screenshotPath = capturescreenshot(result.getName());
            test.addScreenCaptureFromPath(screenshotPath, "Failed Screenshot");

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, result.getThrowable());

        } else {
            test.log(Status.SKIP, result.getThrowable());

        }

    }

    public String capturescreenshot(String screenshotname) {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") + "/test-output/extentReport.html" + screenshotname + ".jpeg";

        File fl = new File(dest);
        try {
            FileUtils.copyFile(src, fl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest;

    }

    @AfterTest
    public void quit() throws InterruptedException {

        Thread.sleep(40000);

        driver.quit();
    }

    @AfterClass
    public void teardown() {

        extent.flush();

    }

}


