package com.saucedemo.api;

import com.saucedemo.api.models.UserRequest;
import com.saucedemo.api.models.UserResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

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

    /**
     * Demonstrates POJO/Record serialization and deserialization.
     *
     * <p>Instead of relying on inline Hamcrest path assertions (e.g., body("name", equalTo(...))),
     * this test deserializes the JSON response into a strongly-typed Java 17 {@link UserResponse} record.
     *
     * <p>Advantages of this approach:
     * <ul>
     *   <li><b>Contract & Type Safety:</b> Jackson verifies that the payload structure adheres to the DTO schema.</li>
     *   <li><b>Compile-Time Refactoring:</b> Breaking API changes (e.g., renamed fields) cause compile errors
     *       instead of silent runtime assertion failures.</li>
     *   <li><b>State Reusability:</b> The deserialized record is immediately available as a typed Java object
     *       for downstream test chaining or database assertions.</li>
     * </ul>
     */
    @Test(description = "POST /api/users - Create a new user using record serialization and deserialization")
    public void createUserWithRecordSerialization() {
        UserRequest userRequest = new UserRequest("John Doe", "Software Engineer");

        UserResponse response = given()
                .spec(requestSpec)
                .body(userRequest)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .extract().as(UserResponse.class);

        Assert.assertEquals(response.name(), "John Doe");
        Assert.assertEquals(response.job(), "Software Engineer");
        Assert.assertNotNull(response.id(), "Server should generate a user ID");
        Assert.assertNotNull(response.createdAt(), "Server should generate a user creation timestamp");
    }

    @Test
    public void endToEndUserLifecycleTest() {
        // 1. Create User (POST)
        String createdUserResponseId =
                given()
                    .spec(requestSpec) // Use the base request specification
                    .body(new UserRequest("John Doe", "Software Engineer")) // Serialize the record to JSON
                .when()
                    .post("/users")
                .then()
                    .spec(responseSpec)
                    .statusCode(201)
                    .body("name", equalTo("John Doe"))
                    .body("job", equalTo("Software Engineer"))
                    .body("id", notNullValue())
                    .extract().path("id"); // Extract the generated user ID for further operations

        // 2. Update User (PUT)
        given()
                        .spec(requestSpec) // Use the base request specification
                        .body(new UserRequest("Firdaouss Lotfi", "QA Engineer")) // Serialize the record to JSON
                .when()
                        .put("/users/" + createdUserResponseId)
                .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .body("name", equalTo("Firdaouss Lotfi"))
                        .body("job", equalTo("QA Engineer"));

        // 3. Delete User (DELETE)
       given()
                        .baseUri("https://reqres.in")
                        .basePath("/api")
                .when()
                        .delete("/users/" + createdUserResponseId)
                .then()
                        .statusCode(204); // Expecting 204 No Content for successful deletion
    }

    @Test
    public void registerWithoutPasswordReturnsBadRequest() {
        String invalidPayload = """
        {
            "email": "sydney@fife"
        }
        """;
        given()
            .spec(requestSpec)
            .body(invalidPayload)
        .when()
            .post("/register")
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing password"));
    }

}
