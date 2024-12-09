package com.abn.emsdata.service;

import com.abn.emsdata.entity.Employee;
import com.abn.emsdata.entity.Role;
import com.abn.emsdata.exception.EmployeeNotFoundException;
import com.abn.emsdata.exception.RoleNotFoundException;
import com.abn.emsdata.mapper.EmployeeMapper;
import com.abn.emsdata.model.EmployeeDataRequest;
import com.abn.emsdata.model.EmployeeDataResponse;
import com.abn.emsdata.repository.EmployeeRepository;
import com.abn.emsdata.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AllArgsConstructor
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private RoleRepository roleRepository;
    private EmployeeMapper employeeMapper;


    public EmployeeDataResponse create(EmployeeDataRequest employeeDataRequest){
        Role role = getRole(employeeDataRequest.roleId());
        Employee employee = employeeMapper.toEmployee(employeeDataRequest);
        employee.setRole(role);
        Employee entity = employeeRepository.saveAndFlush(employee);
        return employeeMapper.toEmployeeDataResponse(entity);
    }

    public EmployeeDataResponse getEmployeeById(Long employeeId){
        return employeeMapper.toEmployeeDataResponse(getEmployee(employeeId));
    }

    public EmployeeDataResponse update(Long employeeId, EmployeeDataRequest employeeDataRequest){
        Employee employee =getEmployee(employeeId);

        if(!employee.getRole().getId().equals(employeeDataRequest.roleId())) {
            employee.setRole(getRole(employeeDataRequest.roleId()));
        }
        employeeMapper.updateEmployeeEntity(employeeDataRequest, employee);
        Employee updatedEmp = employeeRepository.saveAndFlush(employee);
        return employeeMapper.toEmployeeDataResponse(updatedEmp);
    }

    public String delete(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new EmployeeNotFoundException(String.format(
                        "Employee with id [%d] was not found!", employeeId)));
        employeeRepository.delete(employee);
        return "deleted succesfully";
    }

    private Employee getEmployee(Long employeeId) throws EmployeeNotFoundException {
       return employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(String.format(
                "Employee with id [%d] was not found!", employeeId)));
    }

    private Role getRole(Long employeeId) throws RoleNotFoundException {
        return roleRepository.findById(employeeId).orElseThrow(() -> new RoleNotFoundException(String.format(
                "Role with id [%d] was not found!", employeeId)));
    }
}
