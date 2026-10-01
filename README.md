# SauceDemo UI Test Automation

[![Selenium UI Tests](https://github.com/LotfiFirdaouss/SauceDemoAutomation/actions/workflows/tests.yml/badge.svg)](https://github.com/LotfiFirdaouss/SauceDemoAutomation/actions/workflows/tests.yml)

A UI test automation framework for [SauceDemo](https://www.saucedemo.com/) built with Java 17, Selenium WebDriver, TestNG, Maven, and the Page Object Model (POM).

The project demonstrates clean test architecture, Data-Driven Testing (DDT), headless execution, automatic failure capture, and continuous integration via GitHub Actions.

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
- **CI/CD:** GitHub Actions (Ubuntu runner, headless Chrome)
- **Browser:** Google Chrome (Headed locally / Headless in CI)

## Project Structure

```text
.github/
└── workflows/
    └── tests.yml               # GitHub Actions CI pipeline configuration
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

## Continuous Integration (CI)

Every `push` and `pull_request` to the main branch triggers the GitHub Actions workflow defined in `.github/workflows/tests.yml`:
1. Provisions a clean Ubuntu Linux container.
2. Sets up JDK 17 (Eclipse Temurin) with Maven dependency caching.
3. Executes the full TestNG test suite in headless Chrome.
4. Archives Surefire HTML reports and failure screenshots as downloadable workflow artifacts (retained for 14 days).

## How to Run Locally

### Prerequisites
- Java 17+
- Maven 3.8+
- Google Chrome

### Run with visible browser (default)
```bash
mvn clean test
```

### Run headless (CI mode)
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
- [x] Build GitHub Actions CI pipeline with artifact archiving
