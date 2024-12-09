package com.abn.ems.model;

import java.util.UUID;

public record EmployeeResponse (UUID  id,String firstName,String lastName, UUID roleId){

}
