package com.abn.ems.service;

import com.abn.ems.enums.Role;
import com.abn.ems.exception.EmployeeDataAPIException;
import com.abn.ems.exception.RoleNotFoundException;
import com.abn.ems.mapper.EmployeeMapper;
import com.abn.ems.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static com.abn.ems.constants.Constant.*;

/**
 * Implementation of the EmployeeService interface, providing business logic for employee management.
 * The implementation is integrated with Spring Retry to automatically retry specific operations
 * in case of transient failures.
 */
@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Value("${api.path}")
    private String basePath;

    private final RestTemplate restTemplate;
    private final EmployeeMapper employeeMapper;

    /**
     * Retrieves an employee by their ID with retry functionality.
     *
     * <p>This method retrieves a specific employee by their ID. If a api call failure occurs,
     * it will automatically retry up to three times with exponential backoff.</p>
     *
     * @param id the ID of the employee to retrieve
     * @return the employee object
     */
    @Retryable(retryFor = {ResourceAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 20000, multiplier = 2, maxDelay = 120000))
    @Override
    public EmployeeResponse getEmployee(Long id) {
        EmployeeDataResponse employeeDataResponse = restTemplate.getForObject(basePath + API_EMPLOYEE_BY_ID, EmployeeDataResponse.class, id);
        return employeeMapper.toEmployeeResponse(employeeDataResponse);
    }

    /**
     * Retrieves an employee by their name with retry functionality.
     *
     * <p>This method retrieves a specific employee by their ID. If a api call failure occurs,
     * it will automatically retry up to three times with exponential backoff.</p>
     *
     * @param userName the userName of the employee to retrieve
     * @return the employee object
     */
    @Retryable(retryFor = {ResourceAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2, maxDelay = 120000))
    public EmployeeResponse getUser(String userName) {
        EmployeeDataResponse employeeDataResponse;

        try {
            employeeDataResponse = restTemplate.getForObject(basePath + API_EMPLOYEE_BY_NAME, EmployeeDataResponse.class, userName);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new EmployeeDataAPIException(e.getResponseBodyAsString());
        }

        return employeeMapper.toEmployeeResponse(employeeDataResponse);
    }

    /**
     * Creates a new employee in the system with retry functionality.
     *
     * <p>This method accepts an {@link EmployeeRequest} object and call api call to create it database.</p>
     *
     * @param employeeRequest the employee object to create
     * @return the newly created EmployeeResponse object
     */
    @Retryable(retryFor = {ResourceAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2, maxDelay = 12000))
    @Override
    public EmployeeResponse create(EmployeeRequest employeeRequest, String role) {
        EmployeeDataResponse employeeDataResponse;
        EmployeeDataRequest request = employeeMapper.toEmployeeDataRequest(employeeRequest);
        request.setRoleId(Role.getRoleId(role));
        try {
            employeeDataResponse = restTemplate.postForObject(basePath + API_EMPLOYEE, request, EmployeeDataResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new EmployeeDataAPIException(e.getResponseBodyAsString());
        }
        return employeeMapper.toEmployeeResponse(employeeDataResponse);
    }

    /**
     * Updates the details of an existing employee with retry functionality.
     *
     * <p>This method accepts an {@link EmployeeRequest} object and to updates the corresponding employee record
     * in the database it will call EmployeeData api . If a transient failure occurs, the method will be retried according to the retry
     * configuration specified in the {@link Retryable} annotation.</p>
     *
     * @param id              the ID of the employee to update
     * @param employeeRequest the employee object containing updated details
     * @return the updated employee object
     */

    @Retryable(retryFor = {ResourceAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2, maxDelay = 12000))
    @Override
    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest) {
        if (Role.getRole(employeeRequest.roleId()) != null) {
            EmployeeDataRequest request = employeeMapper.toEmployeeDataRequest(employeeRequest);
            HttpEntity<EmployeeDataRequest> requestEntity = new HttpEntity<>(request);
            EmployeeDataResponse employeeResponse;
            try {
                employeeResponse = restTemplate.exchange(basePath + API_EMPLOYEE_BY_ID, HttpMethod.PUT, requestEntity, EmployeeDataResponse.class, id).getBody();
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                throw new EmployeeDataAPIException(e.getResponseBodyAsString());
            }
            return employeeMapper.toEmployeeResponse(employeeResponse);
        }
        throw new RoleNotFoundException(ERROR_MESSAGE_INVALID_ROLE_UPDATE);
    }

    /**
     * Deletes an employee by their ID with retry functionality.
     *
     * <p>This method deletes an employee record from the database based on the provided ID. If a transient
     * failure occurs, it will automatically retry up to three times with exponential backoff.</p>
     *
     * @param id the ID of the employee to delete
     */

    @Retryable(retryFor = {ResourceAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2, maxDelay = 12000))
    @Override
    public ResponseMessage delete(Long id) {
        try {
            return restTemplate.exchange(basePath + API_EMPLOYEE_BY_ID, HttpMethod.DELETE, getHeaders(), ResponseMessage.class, id).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new EmployeeDataAPIException(e.getResponseBodyAsString());
        }
    }

    private HttpEntity<?> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
