package com.abn.ems.service;

import com.abn.ems.Enums.Role;
import com.abn.ems.exception.RoleNotFoundException;
import com.abn.ems.mapper.EmployeeMapper;
import com.abn.ems.model.EmployeeDataRequest;
import com.abn.ems.model.EmployeeDataResponse;
import com.abn.ems.model.EmployeeRequest;
import com.abn.ems.model.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;

import static com.abn.ems.constant.Constant.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeService {

    @Value("${api.path}")
    private String basePath;

    private final RestTemplate restTemplate;
    private final EmployeeMapper employeeMapper;

    @Retryable(retryFor = {ResourceAccessException.class, ConnectException.class}, maxAttempts = 3, backoff = @Backoff(delay = 20000, multiplier = 2, maxDelay = 120000))
    public EmployeeResponse getEmployee(Long id) {
        EmployeeDataResponse employeeDataResponse = restTemplate.getForObject(basePath + API_EMPLOYEE_BY_ID, EmployeeDataResponse.class, id);
        return employeeMapper.toEmployeeResponse(employeeDataResponse);
    }

    @Retryable(retryFor = {ResourceAccessException.class, ConnectException.class}, maxAttempts = 3, backoff = @Backoff(delay = 20000, multiplier = 2, maxDelay = 120000))
    public EmployeeResponse getUser(String userName) {
        EmployeeDataResponse employeeDataResponse = restTemplate.getForObject(basePath + API_EMPLOYEE_BY_NAME, EmployeeDataResponse.class, userName);
        return employeeMapper.toEmployeeResponse(employeeDataResponse);
    }

    @Retryable(retryFor = {ResourceAccessException.class,ConnectException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2, maxDelay = 12000))
    public EmployeeResponse create(EmployeeRequest employeeRequest, String role) {
        EmployeeDataRequest request = employeeMapper.toEmployeeDataRequest(employeeRequest);
        request.setRoleId(Role.getRoleId(role));
        EmployeeDataResponse employeeDataResponse = restTemplate.postForObject(basePath + API_EMPLOYEE, request, EmployeeDataResponse.class);
        return employeeMapper.toEmployeeResponse(employeeDataResponse);
    }

    @Retryable(retryFor = {ResourceAccessException.class,ConnectException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2, maxDelay = 12000))
    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest) {
        if (Role.getRole(employeeRequest.roleId()) != null) {
            EmployeeDataRequest request = employeeMapper.toEmployeeDataRequest(employeeRequest);
            HttpEntity<EmployeeDataRequest> requestEntity = new HttpEntity<>(request);
            EmployeeDataResponse employeeResponse = restTemplate.exchange(basePath + API_EMPLOYEE_BY_ID, HttpMethod.PUT, requestEntity, EmployeeDataResponse.class, id).getBody();
            return employeeMapper.toEmployeeResponse(employeeResponse);
        }
        throw new RoleNotFoundException(ERROR_MESSAGE_INVALID_ROLE_UPDATE);
    }

    @Retryable(retryFor = {ResourceAccessException.class,ConnectException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2, maxDelay = 12000))
    public String delete(Long id) {
        return restTemplate.exchange(basePath + API_EMPLOYEE_BY_ID, HttpMethod.DELETE, getHeaders(), String.class, id).getBody();
    }

    private HttpEntity<?> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
