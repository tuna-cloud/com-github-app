package com.github.app.api.services.impl;

import com.github.app.api.dao.domain.*;
import com.github.app.api.dao.mapper.MenuMapper;
import com.github.app.api.dao.mapper.RoleMapper;
import com.github.app.api.dao.mapper.RolePopedomMapper;
import com.github.app.api.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RolePopedomMapper rolePopedomMapper;
	@Autowired
	private MenuMapper menuMapper;

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
	public void deleteRoleById(Integer roleId) {
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

	@Override
	public void addRolePopedoms(List<RolePopedom> rolePopedomList) {
		rolePopedomMapper.batchInsert(rolePopedomList);
	}

	@Override
	public void deleteRolePopedomById(Integer roleId, Integer menuId) {
		RolePopedomExample example = new RolePopedomExample();
		if (!ObjectUtils.isEmpty(roleId)) {
			example.createCriteria().andRoleIdEqualTo(roleId);
		}
		if (!ObjectUtils.isEmpty(menuId)) {
			example.createCriteria().andMenuIdEqualTo(menuId);
		}
		rolePopedomMapper.deleteByExample(example);
	}

	@Override
	public boolean isAuthOperation(String code, Integer roleId) {
		MenuExample menuExample = new MenuExample();
		menuExample.createCriteria().andCodeEqualTo(code);
		Menu menu = menuMapper.selectOneByExample(menuExample);

		if(ObjectUtils.isEmpty(menu))
			return false;

		RolePopedomExample rolePopedomExample = new RolePopedomExample();
		rolePopedomExample.createCriteria().andMenuIdEqualTo(menu.getMenuId());
		rolePopedomExample.createCriteria().andRoleIdEqualTo(roleId);

		long count = rolePopedomMapper.countByExample(rolePopedomExample);
		if(count == 1)
			return true;

		return false;
	}

	@Override
	public void truncate() {
		rolePopedomMapper.truncate();
		roleMapper.truncate();
	}

}
