package com.abn.emsdata.service;

import com.abn.emsdata.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @InjectMocks
    RoleServiceImpl roleServiceImpl;
    @Mock
    RoleRepository roleRepository;

    @Test
    void testDeleteRole() {
      //  doNothing().when(roleRepository.deleteRoleById(any()))
    }
}
