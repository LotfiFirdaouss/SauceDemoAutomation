# Full-Stack Test Automation Framework (UI + API)

[![Selenium UI & API Tests](https://github.com/LotfiFirdaouss/SauceDemoAutomation/actions/workflows/tests.yml/badge.svg)](https://github.com/LotfiFirdaouss/SauceDemoAutomation/actions/workflows/tests.yml)

A multi-layered test automation framework demonstrating the **Test Automation Pyramid**:
1. **UI Layer:** End-to-end browser automation for [SauceDemo](https://www.saucedemo.com/) using Java 17, Selenium WebDriver 4, TestNG, and Page Object Model (POM).
2. **API Layer:** Backend REST API validation for [ReqRes](https://reqres.in/) using REST Assured, Hamcrest matchers, and Java 17 `record` DTOs.
3. **CI/CD Pipeline:** Fully automated regression suite running headless on Ubuntu runners in GitHub Actions.

---

## Test Architecture & Scenarios (10 Total Tests)

### Layer 1: UI Web Automation (Selenium 4 + POM)

| # | Scenario | Class | Type | Verification |
|---|----------|-------|------|--------------|
| 1 | Successful login | `LoginTest` | Positive | Valid credentials redirect user to the Products inventory page |
| 2 | Incorrect password | `LoginTest` | Negative | Error banner is displayed: *"Epic sadface: Username and password do not match..."* |
| 3 | Add item to cart | `CartTest` | Positive | Cart badge increments to `1` and selected item displays in cart |
| 4 | Checkout validation: missing First Name | `CheckoutTest` | Negative (DDT) | Form rejects submission: *"Error: First Name is required"* |
| 5 | Checkout validation: missing Last Name | `CheckoutTest` | Negative (DDT) | Form rejects submission: *"Error: Last Name is required"* |
| 6 | Checkout validation: missing Postal Code | `CheckoutTest` | Negative (DDT) | Form rejects submission: *"Error: Postal Code is required"* |


> **Note:** Scenarios 4–6 are implemented via a single data-driven test method using TestNG's `@DataProvider`.

### Layer 2: API Automation (REST Assured + Java 17 Records)

| # | Scenario | Endpoint | Method | Key Concepts Demonstrated |
|---|----------|----------|--------|---------------------------|
| 7 | Retrieve Single User | `/api/users/2` | `GET` | Centralized Request/Response specifications, status 200, JSONPath assertions |
| 8 | Create User (DTO) | `/api/users` | `POST` | Java 17 `record` DTO serialization/deserialization, contract validation |
| 9 | E2E User Lifecycle | `/api/users` | `CRUD` | Stateful chaining: POST ➔ extract dynamic ID ➔ PUT update ➔ DELETE (204) |
| 10 | Missing Password Validation | `/api/register` | `POST` | Negative boundary test asserting 400 Bad Request and error schema |


---

## Tech Stack

- **Language:** Java 17
- **UI Automation:** Selenium WebDriver 4, Chrome DevTools
- **API Automation:** REST Assured 5, Hamcrest, Jackson Databind
- **Test Framework:** TestNG (Suites, DataProviders, Lifecycle Hooks)
- **Design Patterns:** Page Object Model (POM), Data-Driven Testing (DDT), DTO Pattern (Java Records)
- **Build Tool:** Maven 3
- **CI/CD:** GitHub Actions (Ubuntu runner, headless Chrome, test artifact archiving)

---

## Project Structure

```text
.github/
└── workflows/
    └── tests.yml               # GitHub Actions CI pipeline configuration
src/test/
├── java/com/saucedemo/
│   ├── api/
│   │   ├── BaseApiTest.java    # Shared Request/Response specifications
│   │   ├── UserApiTest.java    # REST Assured test scenarios (CRUD, DTO, negative)
│   │   └── models/
│   │       ├── UserRequest.java   # Java 17 record request DTO
│   │       └── UserResponse.java  # Java 17 record response DTO
│   ├── pages/
│   │   ├── BasePage.java       # Shared driver, explicit waits, reusable UI actions
│   │   ├── LoginPage.java      # Login locators and actions
│   │   ├── InventoryPage.java  # Products list and cart badge interactions
│   │   ├── CartPage.java       # Shopping cart view and checkout entry
│   │   └── CheckoutPage.java   # Customer information form and error handling
│   └── tests/
│       ├── BaseTest.java       # Browser setup/teardown, headless toggle, failure screenshots
│       ├── LoginTest.java      # UI Scenarios 1 & 2
│       ├── CartTest.java       # UI Scenario 3
│       └── CheckoutTest.java   # UI Scenarios 4–6 (Data-Driven with @DataProvider)
└── resources/
    └── testng.xml              # Multi-suite configuration (UI + API)
```

---

## How to Run Locally

### Run All Tests (UI Headed + API)
```bash
mvn clean test
```

### Run All Tests Headless (CI-compatible)
```bash
mvn clean test -Dheadless=true
```

### Run Only API Tests
```bash
mvn clean test -Dtest=UserApiTest
```

### Run Only UI Tests
```bash
mvn clean test -Dtest=*Test -Dheadless=true
```

---

## Continuous Integration (CI)

Every `push` and `pull_request` to `master` triggers the GitHub Actions workflow:
1. Spawns an Ubuntu Linux environment with Java 17 (Temurin).
2. Runs all 10 tests (6 UI tests headless + 4 API tests).
3. Archives Surefire HTML reports and any failure screenshots as downloadable build artifacts (retained for 14 days).

---

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
- [x] Integrate REST Assured API test suite with Java 17 records (Phase 7)
