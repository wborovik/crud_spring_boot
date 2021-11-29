package web.crud_spring_boot.service;

import web.crud_spring_boot.model.Role;

import java.util.List;

public interface RoleService {

    Role getRoleByName(String role);

    List<Role> getAllRoles();

    Role getRoleById(long id);
}
