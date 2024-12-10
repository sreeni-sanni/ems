package com.abn.ems.mapper;

import com.abn.ems.model.EmployeeDataRequest;
import com.abn.ems.model.EmployeeDataResponse;
import com.abn.ems.model.EmployeeRequest;
import com.abn.ems.model.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface EmployeeMapper {

    @Mapping(target = "name", source = ".", qualifiedByName = "combineName")
    EmployeeDataRequest toEmployeeDataRequest(EmployeeRequest employeeRequest);

    @Mapping(target = "firstName", source = "name", qualifiedByName = "getFirstName")
    @Mapping(target = "surName", source = "name", qualifiedByName = "getSurName")
    EmployeeResponse toEmployeeResponse(EmployeeDataResponse employeeDataResponse);

    @Named("combineName")
    default String combineName(EmployeeRequest employeeRequest) {
        return employeeRequest.firstName() + " " + employeeRequest.surName();
    }

    @Named("getFirstName")
    default String extractFirstName(String name) {
        return name.split(" ")[0];
    }

    @Named("getSurName")
    default String extractSurName(String name) {
        return name.split(" ")[1];
    }

}
