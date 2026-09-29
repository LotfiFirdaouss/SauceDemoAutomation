package com.saucedemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest{

    @Test
    public void successfulLogin() {
        // Open the login page
        driver.get("https://www.saucedemo.com/");

        // Enter valid credentials
        driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");

        // Submit the login form
        driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

        // Wait until the Products heading is visible
        WebElement pageTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-test='title']"
                )
        ));

        // Verify that login succeeded
        Assert.assertEquals(
                pageTitle.getText(),
                "Products",
                "The Products page should appear after a successful login."
                // custom message if the assertion fails
        );
    }

    @Test
    public void failedLogin() {
        // Open the login page
        driver.get("https://www.saucedemo.com/");

        // Enter invalid credentials
        driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("incorrect_sauce");

        // Submit the login form
        driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

        // Wait until the Products heading is visible
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-test='error']"
                        )
                ));

        // Verify that login succeeded
        Assert.assertEquals(
                errorMessage.getText(),
                "Epic sadface: Username and password do not match any user in this service",
                "An error message should appear after a failed login attempt."
        );
    }


}
