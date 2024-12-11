package com.abn.emsdata.service;

import com.abn.emsdata.model.Role;
import com.abn.emsdata.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.abn.emsdata.constant.Constant.ROLE_DELETED;

@Transactional
@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService{
    RoleRepository roleRepository;

    @Override
    public String deleteRole(Role role) {
        roleRepository.deleteRoleById(role.id());
        return ROLE_DELETED;
    }
}
