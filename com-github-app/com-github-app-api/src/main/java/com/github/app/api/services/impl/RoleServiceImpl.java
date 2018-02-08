package com.github.app.api.services.impl;

import com.github.app.api.dao.domain.Role;
import com.github.app.api.dao.domain.RoleExample;
import com.github.app.api.dao.mapper.RoleMapper;
import com.github.app.api.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getRoleById(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public void saveOrUpdate(Role role) {
        if (ObjectUtils.isEmpty(role.getRoleId())) {
            roleMapper.insert(role);
        } else {
            roleMapper.updateByPrimaryKey(role);
        }
    }

    @Override
    public void delteById(Integer roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public List<Role> list(Integer offset, Integer rows) {
        RoleExample roleExample = new RoleExample();
        roleExample.setOffset(offset);
        roleExample.setRows(rows);
        return roleMapper.selectByExample(roleExample);
    }

    @Override
    public long count() {
        return roleMapper.countByExample(new RoleExample());
    }

}
