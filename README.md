# SauceDemo UI Test Automation

A UI test automation framework for [SauceDemo](https://www.saucedemo.com/) built with Java 17, Selenium WebDriver, TestNG, Maven, and the Page Object Model (POM).

The project demonstrates clean test architecture, Data-Driven Testing (DDT), headless execution, automatic failure capture, and strict separation between test logic, UI interactions, and browser lifecycle.

## Automated Scenarios


| # | Scenario | Class | Type | Verification |
|---|----------|-------|------|--------------|
| 1 | Successful login | `LoginTest` | Positive | Valid credentials redirect user to the Products inventory page |
| 2 | Incorrect password | `LoginTest` | Negative | Error banner is displayed: *"Epic sadface: Username and password do not match..."* |
| 3 | Add item to cart | `CartTest` | Positive | Cart badge increments to `1` and selected item displays in cart |
| 4 | Checkout validation: missing First Name | `CheckoutTest` | Negative (DDT) | Form rejects submission: *"Error: First Name is required"* |
| 5 | Checkout validation: missing Last Name | `CheckoutTest` | Negative (DDT) | Form rejects submission: *"Error: Last Name is required"* |
| 6 | Checkout validation: missing Postal Code | `CheckoutTest` | Negative (DDT) | Form rejects submission: *"Error: Postal Code is required"* |


> **Note:** Scenarios 4–6 are implemented via a single test method using TestNG's `@DataProvider` for parameterised, data-driven validation.

## Tech Stack

- **Language:** Java 17
- **Browser Automation:** Selenium WebDriver 4
- **Test Framework:** TestNG
- **Build Tool:** Maven
- **Design Pattern:** Page Object Model (POM) + Data-Driven Testing (DDT)
- **Browser:** Google Chrome (Headed / Headless)

## Project Structure

```text
src/test/
├── java/com/saucedemo/
│   ├── pages/
│   │   ├── BasePage.java       # Shared driver, explicit waits, reusable UI actions
│   │   ├── LoginPage.java      # Login locators and actions
│   │   ├── InventoryPage.java  # Products list and cart badge interactions
│   │   ├── CartPage.java       # Shopping cart view and checkout entry
│   │   └── CheckoutPage.java   # Customer information form and error handling
│   └── tests/
│       ├── BaseTest.java       # Browser setup/teardown, headless toggle, failure screenshots
│       ├── LoginTest.java      # Scenarios 1 & 2
│       ├── CartTest.java       # Scenario 3
│       └── CheckoutTest.java   # Scenarios 4–6 (Data-Driven with @DataProvider)
└── resources/
    └── testng.xml              # TestNG suite configuration
```

## Test Design Highlights

- **Page Object Model (POM):** Test methods contain zero locators and zero Selenium API calls. All UI interactions are encapsulated in page objects.
- **Data-Driven Testing (DDT):** Checkout mandatory fields are verified using a TestNG `@DataProvider`, testing multiple boundary conditions through a single clean test method.
- **Isolated Execution:** Every test method gets its own fresh browser instance via `@BeforeMethod` and `@AfterMethod`, preventing cross-test pollution (cookies, local storage, cart state).
- **Explicit Waits:** No flaky `Thread.sleep()`. All element lookups use `WebDriverWait` with `ExpectedConditions` (visibility, clickability).
- **Automatic Failure Screenshots:** Any test that fails triggers an automatic screenshot capture via TestNG's `ITestResult` hook, saved to `target/screenshots/`.
- **Configurable Headless Execution:** Toggle between visible desktop Chrome and CI-ready headless Chrome via Maven property `-Dheadless=true`.

## How to Run

### Prerequisites
- Java 17+
- Maven 3.8+
- Google Chrome

### Run with visible browser (default)
```bash
mvn clean test
```

### Run headless (faster, CI-compatible)
```bash
mvn clean test -Dheadless=true
```

### Test Artifacts & Reports
- **Surefire / TestNG HTML reports:** `target/surefire-reports/`
- **Failure Screenshots:** `target/screenshots/` (only generated on failure)

## Roadmap

- [x] Set up Maven, Selenium 4, and TestNG
- [x] Initial browser smoke check
- [x] Automate login scenarios (positive and negative)
- [x] Build Page Object Model foundation (`BasePage`, `BaseTest`)
- [x] Automate cart verification
- [x] Automate checkout form validation using TestNG `@DataProvider`
- [x] Configure explicit `testng.xml` suite and connect to Surefire
- [x] Add configurable headless mode & failure screenshots
- [ ] Build GitHub Actions CI pipeline (Phase 6)
