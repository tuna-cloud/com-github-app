package com.github.app.api.services;

import com.github.app.api.dao.domain.Role;
import com.github.app.api.dao.domain.RolePopedom;

import java.util.List;

public interface RoleService {

	Role getRoleById(Integer roleId);

	void saveOrUpdate(Role role);

	void deleteRoleById(Integer roleId);

	List<Role> list(Integer offset, Integer rows);

	long count();

	void addRolePopedoms(List<RolePopedom> rolePopedomList);

	void deleteRolePopedomById(Integer roleId, Integer menuId);

	/**
	 * 是否对某项资源有操作权限
	 * 
	 * @param code
	 * @param roleId
	 * @return
	 */
	boolean isAuthOperation(String code, Integer roleId);

	void truncate();
}
