package com.abn.emsdata.repository;

import com.abn.emsdata.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value="CALL delete_role_procedure(:name)", nativeQuery = true)
    void deleteRoleByName(@Param("name")String roleName);

}
