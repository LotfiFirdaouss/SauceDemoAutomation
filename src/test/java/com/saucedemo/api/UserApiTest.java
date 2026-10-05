package com.saucedemo.api;

import com.saucedemo.api.models.UserRequest;
import com.saucedemo.api.models.UserResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserApiTest extends BaseApiTest {

    @Test(description = "GET /api/users/2 - Retrieve a single user by ID")
    public void getSingleUser() {
        given()
                .spec(requestSpec) // Use the base request specification
        .when()
            .get("/users/2")
        .then()
            .spec(responseSpec) // Use the base response specification
            .statusCode(200)
            .body("data.id", equalTo(2))
            .body("data.first_name", equalTo("Janet"))
            .body("data.last_name", equalTo("Weaver"))
            .body("data.email", equalTo("janet.weaver@reqres.in"));

    }

    @Test(description = "POST /api/users - Create a new user using record serialization and deserialization")
    public void createUserWithRecordSerialization() {
        UserRequest userRequest = new UserRequest("John Doe", "Software Engineer");

        UserResponse response = given()
            .spec(requestSpec) // Use the base request specification
            .body(userRequest) // Serialize the record to JSON
        .when()
            .post("/users")
        .then()
            .spec(responseSpec)
            .statusCode(201)
            .extract().as(UserResponse.class); // Deserialize the response to UserResponse record

        Assert.assertEquals(response.name(), "John Doe");
        Assert.assertEquals(response.job(), "Software Engineer");
        Assert.assertNotNull(response.id(), "Server should generate a user ID");
        Assert.assertNotNull(response.createdAt(), "Server should generate a user creation timestamp");
    }
}
