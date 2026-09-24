package com.saucedemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SmokeTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
    }

     @Test
    public void loginPageLoads() {
        driver.get("https://www.saucedemo.com/");

         Assert.assertEquals(
                 driver.getTitle(),
                 "Swag Labs",
                 "The SauceDemo login page did not load as expected."
         );
    }

    @AfterMethod
    public void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }
}
