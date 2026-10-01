package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By itemName = By.cssSelector("[data-test='inventory-item-name']");
    private final By checkoutButton = By.cssSelector("[data-test='checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getItemName() {
        return getText(itemName);
    }

    public CheckoutPage openCheckout() {
        // Wait for the checkout button to be clickable, then click
        click(checkoutButton);
        // Wait until we actually navigate away from the cart to the checkout page
        waitForUrlContains("checkout-step-one");
        return new CheckoutPage(driver);
    }
}
