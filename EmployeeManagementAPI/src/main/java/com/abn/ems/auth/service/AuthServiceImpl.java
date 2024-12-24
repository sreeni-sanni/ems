package com.abn.ems.auth.service;

import com.abn.ems.exception.EmployeeDataAPIException;
import com.abn.ems.mapper.EmployeeMapper;
import com.abn.ems.model.EmployeeDataResponse;
import com.abn.ems.model.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static com.abn.ems.constants.Constant.API_EMPLOYEE_BY_NAME;

/**
 *
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${api.path}")
    private String basePath;

    private final RestTemplate restTemplate;
    private final EmployeeMapper employeeMapper;

    /**
     * @param userName
     * @return
     */
    @Retryable(retryFor = {ResourceAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 1, maxDelay = 60000))
    @Override
    public EmployeeResponse getEmployee(String userName) {
        EmployeeDataResponse employeeDataResponse;

        try {
            employeeDataResponse = restTemplate.getForObject(basePath + API_EMPLOYEE_BY_NAME, EmployeeDataResponse.class, userName);
        } catch (HttpClientErrorException e) {
            throw new EmployeeDataAPIException(e.getResponseBodyAsString());
        }

        return employeeMapper.toEmployeeResponse(employeeDataResponse);
    }
}
