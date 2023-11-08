package com.visual.Utils;

import com.visual.ImagesTypes;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScreenCaptureUtility {

    public boolean areImagesEqual(String baseLineTitle, String screenshotTitle) {
        BufferedImage baseLineImage = null;
        BufferedImage screenshotImage = null;

        try {
            baseLineImage = ImageIO.read(new File(System.getProperty("user.dir") +
                    File.separator + "src" + File.separator + "images"
                    + File.separator + "baseline" + File.separator + baseLineTitle + ".png"));
            screenshotImage = ImageIO.read(new File(System.getProperty("user.dir") +
                    File.separator + "src" + File.separator + "images"
                    + File.separator + "screenshots" + File.separator + screenshotTitle + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Sucessfully read");
        ImageDiff imageDiff = new ImageDiffer().makeDiff(baseLineImage, screenshotImage);
        boolean areImagesDifferent =  imageDiff.hasDiff();

        if(areImagesDifferent) {
            BufferedImage imageDifferences = imageDiff.getMarkedImage();
            try{
                ImageIO.write(imageDifferences, "png", new File(System.getProperty("user.dir") +
                        File.separator + "src" + File.separator + "images"
                        + File.separator + "diffImages" + File.separator + baseLineTitle + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return !areImagesDifferent;
    }

    public void takeScreenshot(WebDriver webDriver, String fileName, ImagesTypes imageType) {
        Screenshot screen = new AShot().takeScreenshot(webDriver);
        BufferedImage bi = screen.getImage();
        File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "images"
                + File.separator + imageType.getValue() + File.separator + fileName + ".png");
        try {
            ImageIO.write(bi, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takeScreenshotByElement(WebDriver webDriver, String fileName, WebElement webElement) {
        Screenshot screen = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(webDriver, webElement);
        BufferedImage bi = screen.getImage();
        File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "images"
                + File.separator + "screenshots" + File.separator + fileName + ".png");
        try {
            ImageIO.write(bi, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
