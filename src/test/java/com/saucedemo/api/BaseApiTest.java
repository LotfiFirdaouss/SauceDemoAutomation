package com.saucedemo.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

/**
 * Base class for all API test classes.
 * Centralizes HTTP configuration, common headers, and conditional logging.
 */
public abstract class BaseApiTest {
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    @BeforeClass(alwaysRun = true)
    public void setupApiSpecs() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                // Log request details ONLY if the assertion fails — keeps CI logs clean
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                // Log response details ONLY if the assertion fails — keeps CI logs clean
                .log(LogDetail.STATUS)
                .log(LogDetail.BODY)
                .build();
    }
}
