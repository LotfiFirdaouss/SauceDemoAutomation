package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object representing the SauceDemo checkout page.
 */
public class CheckoutPage extends BasePage {

    private final By pageTitle = By.cssSelector("[data-test='title']");

    private final By firstNameInput = By.cssSelector("[data-test='firstName']");
    private final By lastNameInput = By.cssSelector("[data-test='lastName']");
    private final By postalCodeInput = By.cssSelector("[data-test='postalCode']");
    private final By continueButton = By.cssSelector("[data-test='continue']");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public void fillAndSubmitCheckoutForm(String firstName, String lastName, String postalCode)
    {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
        click(continueButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

}
