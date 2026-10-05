package com.saucedemo.api;

import com.saucedemo.api.models.UserRequest;
import com.saucedemo.api.models.UserResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
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

    @Test(description = "GET /api/users/2 - Retrieve a single user by ID")
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

    @Test(description = "POST /api/users - Create a new user using record serialization and deserialization")
    public void createUserWithRecordSerialization() {
        UserRequest userRequest = new UserRequest("John Doe", "Software Engineer");

        UserResponse userResponse = given()
            .contentType(ContentType.JSON)
            .body(userRequest) // Serialize the record to JSON
        .when()
            .post("/api/users")
        .then()
            .statusCode(201)
            .extract()
            .as(UserResponse.class); // Deserialize the response to UserResponse record

        Assert.assertEquals(userResponse.name(), "John Doe");
        Assert.assertEquals(userResponse.job(), "Software Engineer");
        Assert.assertNotNull(userResponse.id(), "Server should generate a user ID");
        Assert.assertNotNull(userResponse.createdAt(), "Server should generate a user creation timestamp");
    }
}
