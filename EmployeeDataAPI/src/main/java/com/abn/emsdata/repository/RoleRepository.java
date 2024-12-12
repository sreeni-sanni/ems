package com.abn.emsdata.repository;

import com.abn.emsdata.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value="CALL DeleteRoleWithEmployeesAndReassignProjects(:id,1300)", nativeQuery = true)
    void deleteRoleById(@Param("id")Long id);

}
