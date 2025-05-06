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
import java.util.List;

public class methodoveriddingchild extends methosoveriddingbase {


    //WebDriver driver = null;
    pagefactory page;
    ExtentSparkReporter pappu;
    ExtentReports pritam;
    ExtentTest aritra;

    @BeforeClass
    public void reportsetup() {
        pappu = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/extentReport1.html");
        pritam = new ExtentReports();
        pritam.attachReporter(pappu);
        pappu.config().setDocumentTitle("Twg trialpack flow Automation");
        pappu.config().setTheme(Theme.STANDARD);
        pappu.config().setReportName("TWg trialpack Functionality");
        pappu.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");


    }

    @BeforeTest
    @Override
    public void browseropen() {
        super.browseropen();

    }

    @BeforeMethod
    public void createtest(Method method) {
        aritra = pritam.createTest(method.getName());
    }

    @Test
    public void trialpack() throws InterruptedException {
        aritra.log(Status.INFO, "Test Start trialpage");
        page = new pagefactory(driver);
        page.trialpage();
        page.trialpackageoperate();
        Thread.sleep(2000);

        List<String[]> credentials = new ArrayList<>();
        credentials.add(new String[]{"smith_doe@yopmail.com", "Testing$$$123"});

        for (String[] ele : credentials) {
            String email = ele[0];
            String pass = ele[1];
            aritra.log(Status.INFO, "Enter email :");
            page.username(email);
            aritra.log(Status.INFO, "Enter password :");
            page.password(pass);
            aritra.log(Status.INFO, "login button click : ");
            page.loginbutton();
            aritra.log(Status.INFO, "Trial pack page functionality check :");
            page.trialpackrandompuplist();
            aritra.log(Status.INFO, "Delete item from cart :");
            page.deleteitem();
            aritra.log(Status.INFO, "empty cart validation :");
            page.emptycartvalidate();
            aritra.log(Status.INFO, "Go to myaccount page :");
            page.myaccount();
            aritra.log(Status.INFO, "Logout from myaccount :");
            page.logout();
        }


    }

    @AfterMethod
    public void getresult(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.SUCCESS) {
            aritra.log(Status.PASS, result.getThrowable());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            aritra.log(Status.FAIL, result.getThrowable());
            String screenshot = getscreenshot(result.getName());
            aritra.addScreenCaptureFromPath(screenshot,"Failed Screenshot");


        }else {
            aritra.log(Status.SKIP, result.getThrowable());

        }

    }

    public String getscreenshot(String screenshotname) throws IOException {
        TakesScreenshot prema = (TakesScreenshot) driver;
        File srcfile = prema.getScreenshotAs(OutputType.FILE);
        String descfile = System.getProperty("user.dir") + "/test-output/extentReport1.html" + screenshotname + ".jpeg";
        File descfile1 = new File(descfile);
        try {
            FileUtils.copyFile(srcfile, descfile1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return descfile;
    }

    @AfterTest
    public void quit() throws InterruptedException {
        super.quit();


    }

    @AfterClass
    public void reportend() {
        pritam.flush();
    }
}
