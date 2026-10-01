package com.saucedemo.tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Base class for test classes.
 * Creates a fresh browser before each test and closes it afterward.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        ChromeOptions options = new ChromeOptions();

        // Read the "headless" system property (passed by Maven or IDE)
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        if (headless) {
            options.addArguments("--headless=new");
        }

        // Standard options for stability across different environments (CI / local)
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        driver = new ChromeDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null) {
            // If the test failed, capture a screenshot before closing the browser
            if (result.getStatus() == ITestResult.FAILURE) {
                takeScreenshot(result.getName());
            }

            driver.quit();
        }
    }

    private void takeScreenshot(String testName) {
        try {
            Path screenshotDir = Path.of("target", "screenshots");
            Files.createDirectories(screenshotDir);

            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")
            );

            // Replace any invalid characters from data-provider or method names
            String safeTestName = testName.replaceAll("[^a-zA-Z0-9-_]", "_");
            Path destination = screenshotDir.resolve(safeTestName + "-" + timestamp + ".png");

            byte[] screenshotBytes = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            Files.write(destination, screenshotBytes);
            System.out.println("Screenshot saved: " + destination.toAbsolutePath());
        } catch (IOException | RuntimeException e) {
            System.err.println("Could not capture failure screenshot: " + e.getMessage());
        }
    }
}
