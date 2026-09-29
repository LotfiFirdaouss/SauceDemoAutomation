# SauceDemo UI Test Automation

UI test automation project for
[SauceDemo](https://www.saucedemo.com/) using Java, Selenium WebDriver,
TestNG, Maven, and the Page Object Model.

The goal is to automate a focused set of user scenarios while maintaining a
clear separation between test logic, page interactions, and browser setup.

## Current test coverage

- Successful login with valid credentials
- Rejected login with an incorrect password
- Add the Sauce Labs Backpack to the cart
- Verify the cart badge updates correctly
- Verify the selected product appears in the cart

## Planned test coverage

- Checkout form validation

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
│   ├── InventoryPage.java
│   └── CartPage.java
└── tests/
    ├── BaseTest.java
    ├── LoginTest.java
    └── CartTest.java
```

## Class responsibilities


| Class | Responsibility |
|---|---|
| `BaseTest` | Creates a fresh browser before each test and closes it afterward |
| `BasePage` | Provides shared WebDriver access, explicit waits, and reusable UI actions |
| `LoginPage` | Contains the login-page locators and login actions |
| `InventoryPage` | Represents the products page and provides product and cart actions |
| `CartPage` | Provides access to products displayed in the shopping cart |
| `LoginTest` | Verifies successful and unsuccessful login behavior |
| `CartTest` | Verifies that a product can be added to and displayed in the cart |


## Test design

Each test receives a new browser session through TestNG's `@BeforeMethod` and
`@AfterMethod` annotations. This keeps the tests independent and prevents
browser state from leaking between scenarios.

The project uses the Page Object Model:

- Page classes contain locators and browser interactions.
- Test classes contain scenarios and assertions.
- `BasePage` contains reusable Selenium operations and explicit waits.
- `BaseTest` manages the browser lifecycle.

Tests use explicit waits instead of fixed `Thread.sleep()` delays.

## Run the tests

### Prerequisites

- Java 17 or newer
- Maven
- Google Chrome

Run the complete test suite from the project root:

```bash
mvn clean test
```

You can also run individual test classes directly from IntelliJ.

Maven test reports are generated under:

```text
target/surefire-reports/
```

## Roadmap

- [x] Set up Maven, Selenium, and TestNG
- [x] Complete the initial browser smoke check
- [x] Automate successful and unsuccessful login
- [x] Introduce the Page Object Model
- [x] Automate adding a product to the cart
- [ ] Automate checkout form validation
- [ ] Add a TestNG suite configuration
- [ ] Capture screenshots when tests fail
- [ ] Add configurable headless execution
- [ ] Run tests automatically with GitHub Actions
- [ ] Upload test reports and failure screenshots as CI artifacts
