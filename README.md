# SauceDemo UI Test Automation

UI test automation project for
[SauceDemo](https://www.saucedemo.com/) using Java, Selenium WebDriver,
TestNG, Maven, and the Page Object Model.

The project focuses on a small set of user scenarios and a clean,
maintainable test structure. It is being developed incrementally, starting
with direct Selenium tests and refactoring them into reusable page objects.

## Current test coverage

- Successful login with valid credentials
- Rejected login with an incorrect password

## Planned scenarios

- Add an item to the shopping cart
- Validate required fields during checkout

## Tech stack

- Java 17
- Selenium WebDriver
- TestNG
- Maven
- Google Chrome
- Page Object Model

## Project structure

```text
src/test/java/com/lotfifirdaouss/saucedemo/
├── pages/
│   ├── BasePage.java
│   ├── LoginPage.java
│   └── InventoryPage.java
└── tests/
    ├── BaseTest.java
    └── LoginTest.java
