package com.visual.Utils;

import com.visual.ImagesTypes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.File;

import static org.assertj.core.api.Assertions.*;

public class RegressionSuite {
    WebDriver driver;

    ScreenCaptureUtility screenCaptureUtility = new ScreenCaptureUtility();

    @BeforeClass
    public void cleanDirectories() {
        try {
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "images"
                    + File.separator + "screenshots"));
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "images"
                    + File.separator + "diffImages"));
        } catch (Exception ignored) {}
    }

    @BeforeMethod()
    public void setup() {
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
    public void compareImagesTest(String url, String name) {
        driver.get(url);
        screenCaptureUtility.takeScreenshot(driver, name, ImagesTypes.SCREENSHOTS);
        assertThat(screenCaptureUtility.areImagesEqual(name, name)).isTrue();
    }



    @AfterMethod
    public void quit() {
        driver.quit();
    }
}
