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
import java.util.ArrayList;

public class TwgExtentReport {

    WebDriver driver = null;
    ExtentSparkReporter htmlreporter;
    ExtentReports extent;
    ExtentTest test;
    pagefactory page;

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

    @Test
    public void login() {
        test.log(Status.INFO, "This is test log message ");
        page = new pagefactory(driver);
        page.openurl();

        ArrayList<String[]> logincredentials = new ArrayList<>();
        logincredentials.add(new String[]{"pritam.sanyal@yopmail.com", "Sanyal88888@@"});


        for (String[] ele : logincredentials) {
            String usernamefield=ele[0];
            String passwordfield=ele[1];

            page.username(usernamefield);
            page.password(passwordfield);
            page.loginbutton();
            page.verifydashboard();
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

        Thread.sleep(2000);
        driver.quit();
    }

    @AfterClass
    public void teardown() {

        extent.flush();

    }

}
