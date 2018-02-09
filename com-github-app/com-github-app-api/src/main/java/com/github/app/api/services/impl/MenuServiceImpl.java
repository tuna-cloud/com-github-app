package com.github.app.api.services.impl;

import com.github.app.api.dao.domain.Menu;
import com.github.app.api.dao.domain.MenuExample;
import com.github.app.api.dao.mapper.MenuMapper;
import com.github.app.api.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper mapper;

    @Override
    public Menu getMenuById(Integer menuId) {
        return mapper.selectByPrimaryKey(menuId);
    }

    @Override
    public void saveOrUpdate(Menu menu) {
        if (ObjectUtils.isEmpty(menu.getMenuId())) {
            mapper.insert(menu);
        } else {
            mapper.updateByPrimaryKey(menu);
        }
    }

    @Override
    public void deleteById(Integer menuId) {
        mapper.deleteByPrimaryKey(menuId);
    }

    @Override
    public List<Menu> find(Integer parentId, Integer offset, Integer rows) {
        MenuExample example = new MenuExample();
        if (!ObjectUtils.isEmpty(parentId)) {
            example.createCriteria().andParentIdEqualTo(parentId);
        }
        example.setOffset(offset);
        example.setRows(rows);

        return mapper.selectByExample(example);
    }

    @Override
    public List<Menu> findRootMenu() {
        MenuExample example = new MenuExample();
        example.createCriteria().andParentIdIsNull();
        return mapper.selectByExample(example);
    }

    @Override
    public long count(Integer parentId) {
        MenuExample example = new MenuExample();
        if (!ObjectUtils.isEmpty(parentId)) {
            example.createCriteria().andParentIdEqualTo(parentId);
        }
        return mapper.countByExample(example);
    }

    @Override
    public List<Menu> findMenuByRoleId(Integer roleId) {
        return mapper.findMenuByRoleId(roleId);
    }

    @Override
    public List<Menu> findRootMenuByRoleId(Integer roleId) {
        return mapper.findRootMenuByRoleId(roleId);
    }

    @Override
    public List<Menu> findChildMenuByMenuId(Integer menuId) {
        return mapper.findChildMenuByMenuId(menuId);
    }

    @Override
    public void truncate() {
        mapper.truncate();
    }

}
