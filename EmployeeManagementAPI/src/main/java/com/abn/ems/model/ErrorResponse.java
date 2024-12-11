package com.abn.ems.model;

import java.time.LocalDateTime;

/**
 * Represents the structure of an error response returned by the API.
 * <p>
 * This class encapsulates details about errors encountered during request processing,
 * providing clients with meaningful information to understand and handle the error.
 * It typically includes an error message, error code, and timestamp.
 * </p>
  */
public record ErrorResponse(String message, int statusCode, String path, LocalDateTime dateTime) {
}
