package com.abn.ems.model;

import java.util.UUID;


public record EmployeeDataResponse(UUID id,String name, UUID roleId) {
}
