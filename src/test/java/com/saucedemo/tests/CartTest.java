package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Verifies shopping-cart behavior.
 */
public class CartTest extends BaseTest {

    @Test
    public void addBackpackToCart() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addBackpackToCart();

        Assert.assertEquals(
                inventoryPage.getCartItemCount(),
                "1",
                "The cart badge should show one item."
        );

        CartPage cartPage = inventoryPage.openCart();

        Assert.assertEquals(
                cartPage.getItemName(),
                "Sauce Labs Backpack",
                "The selected product should appear in the cart."
        );
    }

}
