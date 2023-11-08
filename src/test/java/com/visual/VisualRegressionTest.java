package com.visual;

import com.visual.Utils.ScreenCaptureUtility;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class VisualRegressionTest {
    WebDriver driver;

    ScreenCaptureUtility screenCaptureUtility = new ScreenCaptureUtility();;

    @BeforeMethod()
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demo.guru99.com/test/newtours/");
        assertThat(driver.getTitle()).containsIgnoringCase("Welcome: Mercury Tours");
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

    @Test
    public void prepareBaseLine(String url, String name) {

    }

    @Test
    public void takeScreenshotOfEntireScreen() {
        screenCaptureUtility.takeScreenshot(driver, "HomePage", ImagesTypes.SCREENSHOTS);
    }

    @Test
    public void takeScreenshotByElement() {
        WebElement mercuryLogo = driver.findElement(By.cssSelector("img[alt='Mercury Tours']"));
        screenCaptureUtility.takeScreenshotByElement(driver, "MercuryLogo", mercuryLogo);
    }

    @Test
    public void areImagesEqualTest() {
        screenCaptureUtility.takeScreenshot(driver, "screenHome", ImagesTypes.SCREENSHOTS);
        assertThat(screenCaptureUtility.areImagesEqual("HomePage",
                "screenHome")).isTrue();
    }

    @Test
    public void comparingImagesToFail() {
        driver.findElement(By.cssSelector("input[name = 'userName']")).sendKeys("tutorial");
        screenCaptureUtility.takeScreenshot(driver, "screenHome", ImagesTypes.SCREENSHOTS);
        assertThat(screenCaptureUtility.areImagesEqual("HomePage",
                "screenHome")).isTrue();

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
