package com.saucedemo.tests;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
    LoginTest : Decides what behavior to verify
 */
public class LoginTest extends BaseTest{

    @Test
    public void successfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        // Verify that login succeeded
        Assert.assertEquals(
                inventoryPage.getPageTitle(),
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

        // Verify that login succeeded
        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service",
                "An error message should appear after a failed login attempt."
        );
    }


}
