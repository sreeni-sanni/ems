package com.abn.ems.mapper;

import com.abn.ems.model.EmployeeDataRequest;
import com.abn.ems.model.EmployeeDataResponse;
import com.abn.ems.model.EmployeeRequest;
import com.abn.ems.model.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


/**
 * Mapper interface for mapping EmployeeRequest to EmployeeDataRequest and EmployeeDataResponse to EmployeeResponse.
 *
 * <p>This interface uses MapStruct to generate the implementation for converting
 * between the {@link EmployeeDataRequest}  and the {@link EmployeeRequest}.
 * The goal of the mapper is to separate the data transformation logic from the service layer.</p>
 *
 * <p>The generated implementation will automatically implement the methods defined
 * in this interface.</p>
 */
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
