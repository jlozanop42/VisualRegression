package com.visual;

import com.visual.Utils.ScreenCaptureUtility;
import com.visual.enums.ImagesTypes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PrepareBaseLineTest {
    WebDriver driver;

    ScreenCaptureUtility screenCaptureUtility = new ScreenCaptureUtility();;

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
    public void prepareBaseLine(String url, String name) {
        driver.get(url);
        screenCaptureUtility.takeScreenshot(driver, name, ImagesTypes.BASELINE);
    }

    @AfterMethod
    public void quit() {
        driver.quit();
    }
}
