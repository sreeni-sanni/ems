package com.abn.ems.model;

import java.time.LocalDateTime;

public record ErrorResponse(String message, int statusCode, String path, LocalDateTime dateTime) {
}
