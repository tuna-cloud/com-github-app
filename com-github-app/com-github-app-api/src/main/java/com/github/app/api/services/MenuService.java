package com.github.app.api.services;

import com.github.app.api.dao.domain.Menu;

public interface MenuService {

    void saveOrUpdate(Menu menu);

    void deleteById(Integer menuId);

    void find();
}
