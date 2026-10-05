package com.saucedemo.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Response payload returned by the server on user creation.
 * ignoreUnknown ignores any extra metadata fields the server might return.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserResponse(
       String name,
       String job,
       String id,
       String createdAt
) {}
