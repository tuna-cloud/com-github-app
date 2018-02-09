package com.github.app.api.services;

import com.github.app.api.dao.domain.Menu;

import java.util.List;

/**
 * 针对资源管理用，和权限无关
 */
public interface MenuService {
    Menu getMenuById(Integer menuId);

	void saveOrUpdate(Menu menu);

	void deleteById(Integer menuId);

	default List<Menu> find(Integer parentId) {
		return find(parentId, null, null);
	}

	List<Menu> find(Integer parentId, Integer offset, Integer rows);

    long count(Integer parentId);

	List<Menu> findRootMenu();

	List<Menu> findMenuByRoleId(Integer roleId);

	List<Menu> findRootMenuByRoleId(Integer roleId);

	List<Menu> findChildMenuByMenuId(Integer menuId);

	void truncate();
}
