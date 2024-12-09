package com.abn.emsdata.mapper;

import com.abn.emsdata.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<Role> toRoleDto(List<com.abn.emsdata.entity.Role> role);
}
