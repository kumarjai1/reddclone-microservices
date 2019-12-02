package com.example.usermicroservice.service;

import com.example.usermicroservice.exception.EntityNotFoundException;
import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.repository.UserRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class UserRoleServiceTest {

    private UserRole userRole;
    private User user;
    private List<UserRole> userRoles;

    @InjectMocks
    UserRoleServiceImpl userRoleService;

    @Mock
    UserRoleRepository userRoleRepository;

    public UserRoleServiceTest () {
        userRole = new UserRole();
        user = new User();
        userRoles = new ArrayList<>();
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void init () {
        userRole.setId(1);
        userRole.setName("ROLE_USER");
        userRoles.add(userRole);

        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("pw1");
        user.setEmail("user1@email.com");
        user.setUserRoles(userRoles);
    }

    @Test
    public void listRoles_AuthorizedUser_SuccessRolesList() {

        when(userRoleRepository.findAll()).thenReturn(userRoles);

        Iterable<UserRole> returnedRoles = userRoleService.listRoles();

        assertThat(returnedRoles).isNotNull();
        assertThat(returnedRoles).containsSequence(userRoles);
    }

    @Test
    public void getRole_AuthorizedUser_SuccessRetrieveRole () {
        when(userRoleRepository.findByName(any())).thenReturn(userRole);

        UserRole returnedUserRole = userRoleService.getRole("ROLE_USED");
//        System.out.println(returnedUserRole.getName() + " exiting role: " + userRole.getName());
        assertThat(returnedUserRole).isNotNull();
        assertThat(returnedUserRole).isEqualToComparingFieldByField(userRole);
    }

    @Test
    public void getRoleById_RoleExists_SuccessReturnsRole () throws EntityNotFoundException {
        when(userRoleRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(userRole));

        UserRole returnedUserRole = userRoleService.getRoleById(1L);

        assertThat(returnedUserRole).isNotNull();
        assertThat(returnedUserRole).isEqualToComparingFieldByField(userRole);
    }

    @Test (expected = EntityNotFoundException.class)
    public void getRoleById_RoleNotFound_EntityNotFoundError () throws EntityNotFoundException {
        when(userRoleRepository.findAllById(any())).thenReturn(null);

        UserRole returnedUserRole = userRoleService.getRoleById(1L);
        System.out.println(returnedUserRole.getName());
        assertThat(returnedUserRole).isNull();
        assertThat(doThrow(EntityNotFoundException.class));
    }

    @Test
    public void createRole_AuthorizedUser_SuccessCreatedRole () {
        when(userRoleRepository.save(any())).thenReturn(userRole);

        UserRole createdRole = userRoleService.createRole(userRole);

        assertThat(createdRole).isNotNull();
        assertThat(createdRole).isEqualToComparingFieldByField(userRole);
    }
}