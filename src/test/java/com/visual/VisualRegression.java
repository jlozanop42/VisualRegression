package com.visual;

import com.visual.Utils.ScreenCaptureUtility;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class VisualRegression {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void test() {
        driver.get("https://demo.guru99.com/test/newtours/");
        assertThat(driver.getTitle()).containsIgnoringCase("Welcome: Mercury Tours");
        ScreenCaptureUtility screenCaptureUtility = new ScreenCaptureUtility();
        screenCaptureUtility.takeScreenshot(driver, "Emiliano");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
