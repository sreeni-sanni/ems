package com.abn.emsdata.mapper;

import com.abn.emsdata.entity.Employee;
import com.abn.emsdata.model.EmployeeDataRequest;
import com.abn.emsdata.model.EmployeeDataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper
public interface EmployeeMapper {

    @Mapping(target = "firstName", source = "name", qualifiedByName = "getFirstName")
    @Mapping(target = "surName", source = "name", qualifiedByName = "getSurname")
    Employee toEmployee(EmployeeDataRequest employeeDataRequest);

    @Mapping(target = "name", source = ".", qualifiedByName = "combineName")
    @Mapping(target = "roleId", source = "role.id")
    EmployeeDataResponse toEmployeeDataResponse(Employee employee);

    @Mapping(target = "firstName", source = "name", qualifiedByName = "getFirstName")
    @Mapping(target = "surName", source = "name", qualifiedByName = "getSurname")
    void updateEmployeeEntity(EmployeeDataRequest employeeDataRequest, @MappingTarget Employee employee);

    @Named("combineName")
    default String combineName(Employee employee) {
        return employee.getFirstName() + " " + employee.getSurName();
    }

    @Named("getFirstName")
    default String extractFirstName(String name) {
        return name.split(" ")[0];
    }

    @Named("getSurname")
    default String extractLastName(String name) {
        return name.split(" ")[1];
    }
}
