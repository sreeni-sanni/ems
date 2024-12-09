package com.abn.ems.service;

import com.abn.ems.mapper.EmployeeMapper;
import com.abn.ems.model.EmployeeDataRequest;
import com.abn.ems.model.EmployeeDataResponse;
import com.abn.ems.model.EmployeeRequest;
import com.abn.ems.model.EmployeeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.ConnectException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Slf4j
public class EmployeeService {

    RestTemplate restTemplate;
    EmployeeMapper employeeMapper;

    @Retryable(retryFor ={ConnectException.class},maxAttempts = 3,backoff = @Backoff(delay = 2000, multiplier = 2,maxDelay = 12000))
    public EmployeeResponse getEmployee(Long id) {
        System.out.println("inside getEmployee");
        String url = UriComponentsBuilder.fromPath("/api/employee").queryParam("id", id).build().toUriString();
        EmployeeDataResponse employeeDataResponse=restTemplate.getForObject(url, EmployeeDataResponse.class);
        return employeeMapper.toEmployeeResponse(employeeDataResponse);
    }
    @Retryable(retryFor ={ConnectException.class},maxAttempts = 3,backoff = @Backoff(delay = 2000, multiplier = 2,maxDelay = 12000))
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        EmployeeDataRequest request=employeeMapper.toEmployeeDataRequest(employeeRequest);
        request.setRoleId(1L);
        String url = UriComponentsBuilder.fromPath("http://localhost:8089/api/employee").build().toUriString();
        EmployeeDataResponse employeeDataResponse=restTemplate.postForObject(url,request,EmployeeDataResponse.class);
        return employeeMapper.toEmployeeResponse(employeeDataResponse);
    }
    @Retryable(retryFor ={ConnectException.class},maxAttempts = 3,backoff = @Backoff(delay = 2000, multiplier = 2,maxDelay = 12000))
    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest) {
        EmployeeDataRequest request=employeeMapper.toEmployeeDataRequest(employeeRequest);
        String url = UriComponentsBuilder.fromPath("/").queryParam("id", id).build().toUriString();
        return restTemplate.exchange(url, HttpMethod.PUT,getHeaders(),EmployeeResponse.class).getBody();
    }
    @Retryable(retryFor ={ConnectException.class},maxAttempts = 3,backoff = @Backoff(delay = 2000, multiplier = 2,maxDelay = 12000))
    public String delete(Long id) {
        String url = UriComponentsBuilder.fromPath("/").queryParam("id", id).build().toUriString();
        return restTemplate.exchange(url, HttpMethod.DELETE,getHeaders(),String.class).getBody();
    }

    private HttpEntity<?> getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

    @Recover
    public String recover(ConnectException e) throws URISyntaxException {
        log.info("ConnectException recovered at:{}", LocalDateTime.now());
        return "CouldNotCallAPI";
    }
}
