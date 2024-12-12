package com.abn.emsdata.service;

import com.abn.emsdata.exception.RoleNotFoundException;
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

    /**
     *  Deletes an existing role from the system.
     *
     * @param roleId the ID of the role to delete
     * @return
     */
    @Override
    public String deleteRole(Long roleId) {
        try {
            roleRepository.deleteById(roleId);
        }catch (Exception e){
            throw new RoleNotFoundException(e.getMessage());
        }
        return ROLE_DELETED;
    }
}
