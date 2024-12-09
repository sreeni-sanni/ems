package com.abn.emsdata.model;

import java.time.LocalDateTime;

public record ErrorDetails(String message, String path,LocalDateTime dateTime) {
}
