package com.abn.ems.auth.service;

import com.abn.ems.model.EmployeeResponse;

public interface AuthService {

    EmployeeResponse getEmployee(String UserName);
}
