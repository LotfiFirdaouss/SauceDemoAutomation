package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest{

    @Test
    public void successfulLogin() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

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
        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();
        loginPage.login("standard_user", "invalid_sauce");

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
