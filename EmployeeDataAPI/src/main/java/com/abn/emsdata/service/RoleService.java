package com.abn.emsdata.service;

import com.abn.emsdata.mapper.RoleMapper;
import com.abn.emsdata.model.Role;
import com.abn.emsdata.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public List<Role> getRoles() {
       return roleMapper.toRoleDto(roleRepository.findAll());
    }

    public void deleteRole(String roleName) {
        roleRepository.deleteRoleByName(roleName);
    }
}
