package web.crud_spring_boot.service;

import web.crud_spring_boot.model.Role;

public interface RoleService {

    Role getRoleByName(String role);
}
