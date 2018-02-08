package com.github.app.api.services;

import com.github.app.api.dao.domain.Role;

import java.util.List;

public interface RoleService {

    Role getRoleById(Integer roleId);

    void saveOrUpdate(Role role);

    void delteById(Integer roleId);

    List<Role> list(Integer offset, Integer rows);

    long count();
}
