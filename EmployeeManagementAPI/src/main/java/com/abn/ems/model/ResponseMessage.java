package com.abn.ems.model;

/**
 * Represents a standardized response message for delete API response.
 * <p>
 * This record is used to encapsulate the details of a response message, such as
 * a descriptive message. It provides a uniform structure for API
 * responses to improve consistency and readability.
 * </p>
 */
public record ResponseMessage(String message) {
}
