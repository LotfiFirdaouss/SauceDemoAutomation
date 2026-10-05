package com.saucedemo.api.models;

/**
 * Request payload for creating or updating a user.
 */
public record UserRequest(String name, String job) {
}
