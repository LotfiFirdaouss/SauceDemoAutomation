package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    // 1. Define the DataProvider
    @DataProvider(name = "formData")
    public Object[][] getData() {
        return new Object[][] {
                {"", "lotfi","20000","First Name"},
                {"firdaouss", "","20000", "Last Name"},
                {"firdaouss", "lotfi","", "Postal Code"}
        };
    }

    @Test(dataProvider = "formData", description = "Validate mandatory fields on checkout form")
    public void validateCheckoutMandatoryFields(String firstname, String lastname, String postalCode, String exceptionMessage) {
        // 1 - login
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        // 2 - adding to cart + navigating to cart page
        inventoryPage.addBackpackToCart();
        CartPage cartPage = inventoryPage.openCart();

        // 3 - opening checkout page and filling info
        CheckoutPage checkoutPage = cartPage.openCheckout();
        checkoutPage.fillAndSubmitCheckoutForm(firstname, lastname, postalCode);

        // 4 - Assert or verify error message on the same page
        Assert.assertEquals(
                checkoutPage.getErrorMessage(),
                "Error: "+ exceptionMessage +" is required",
                "An error message should appear when the "+ exceptionMessage +" is missing."
        );

    }
}
