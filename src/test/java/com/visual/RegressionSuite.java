package com.visual;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.visual.enums.ImagesTypes;
import com.visual.Utils.ScreenCaptureUtility;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

public class RegressionSuite {
    WebDriver driver;

    ScreenCaptureUtility screenCaptureUtility = new ScreenCaptureUtility();

    ExtentReports extent;
    ExtentSparkReporter sparkReporter;
    ExtentTest test;


    @BeforeClass
    public void cleanDirectories() {
        extent = new ExtentReports();
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "reports" + File.separator + "report.html");
        extent.attachReporter(sparkReporter);
        try {
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "images"
                    + File.separator + "screenshots"));
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "images"
                    + File.separator + "diffImages"));
        } catch (Exception ignored) {}
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @DataProvider(name = "urls")
    public static Object[][] urls() {
        return new Object[][] {
                {"https://demo.guru99.com/test/newtours/", "homePage"},
                {"https://demo.guru99.com/test/newtours/reservation.php", "reservationPage"},
                {"https://demo.guru99.com/test/newtours/register.php", "registerPage"},
                {"https://demo.guru99.com/test/newtours/register_sucess.php", "confirmationRegisterPage"}
        };
    }

    @Test(dataProvider = "urls")
    public void compareImagesTest(String url, String name, Method method, ITestContext context) {
        context.getCurrentXmlTest().addParameter("image", name);
        test = extent.createTest(method.getName() + " in the url: " + url);
        driver.get(url);
        screenCaptureUtility.takeScreenshot(driver, name, ImagesTypes.SCREENSHOTS);
        assertThat(screenCaptureUtility.areImagesEqual(name, name)).isTrue();
    }

    @AfterMethod
    public void afterMethod(ITestResult result, ITestContext context) {
        String image = context.getCurrentXmlTest().getParameter("image");
        if(result.getStatus() == ITestResult.SUCCESS)  test.pass("Test passed");
        if(result.getStatus() == ITestResult.FAILURE) {
            System.out.println(String.format("%s/src/images/diffImages/%s.png", System.getProperty("user.dir"), image));
            test.addScreenCaptureFromPath(String.format("%s/src/images/diffImages/%s.png", System.getProperty("user.dir"), image), "Screenshot");
            test.log(Status.FAIL, result.getThrowable());
        }
        if(result.getStatus() == ITestResult.SKIP) test.log(Status.SKIP, "Test skipped");
    }

    @AfterClass
    public void quit() {
        driver.quit();
        extent.flush();
    }
}
