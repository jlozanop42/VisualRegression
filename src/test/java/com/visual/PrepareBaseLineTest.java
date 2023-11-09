package com.visual;

import com.visual.Utils.ScreenCaptureUtility;
import com.visual.enums.ImagesTypes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.File;

public class PrepareBaseLineTest {
    WebDriver driver;

    ScreenCaptureUtility screenCaptureUtility;

    @BeforeClass()
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        screenCaptureUtility = new ScreenCaptureUtility();
        try {
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "images"
                    + File.separator + "baseline"));
        } catch (Exception ignored) {}
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
    public void prepareBaseLine(String url, String name) {
        driver.get(url);
        screenCaptureUtility.takeScreenshot(driver, name, ImagesTypes.BASELINE);
    }

    @AfterClass
    public void quit() {
        driver.quit();
    }
}
