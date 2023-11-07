package com.visual.Utils;

import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScreenCaptureUtility {
    public void takeScreenshot(WebDriver webDriver, String fileName) {
        Screenshot screen = new AShot().takeScreenshot(webDriver);
        BufferedImage bi = screen.getImage();
        System.out.println("Path: " + System.getProperty("user.dir") + File.separator + "src" + File.separator + "images"
                + File.separator + "screenshots" + File.separator + "image.png");
        File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "images"
                + File.separator + "screenshots" + File.separator + fileName + ".png");
        try {
            ImageIO.write(bi, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
