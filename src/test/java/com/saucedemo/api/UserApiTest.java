package com.saucedemo.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserApiTest {

    @BeforeClass
    public void setUp() {
        // The base domain for all requests in this class
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    public void getSingleUser() {
        given()
            // GIVEN : We expect JSON back
            .contentType(ContentType.JSON)
        .when()
            // WHEN: we send an HTTP GET request to this endpoint
            .get("/api/users/2")
        .then()
            // THEN: 1. Assert that the response status code is 200
            .statusCode(200)
            // THEN: 2. Assert the user's data is as expected
            .body("data.id", equalTo(2))
            .body("data.first_name", equalTo("Janet"))
            .body("data.last_name", equalTo("Weaver"))
            .body("data.email", equalTo("janet.weaver@reqres.in"));

    }
}
