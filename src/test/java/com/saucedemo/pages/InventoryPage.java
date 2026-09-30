package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object representing the SauceDemo product inventory page.
 */
public class InventoryPage extends BasePage {

    private final By pageTitle = By.cssSelector("[data-test='title']");

    private final By addBackpackButton = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public void addBackpackToCart() {
        click(addBackpackButton);
    }

    public String getCartItemCount() {
        return getText(cartBadge);
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }

}
