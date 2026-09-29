package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object representing the SauceDemo product inventory page.
 */
public class InventoryPage extends BasePage {

    private final By pageTitle = By.cssSelector("[data-test='title']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }
}
