package com.github.app.api.services;

import com.github.app.api.dao.domain.Account;
import com.github.app.api.dao.domain.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SystemInitServiceServiceTest extends BaseServiceTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private LogService logService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    public void clearDB() {
        logService.truncate();
        accountService.truncate();
        roleService.truncate();
        menuService.truncate();
    }

    @Test
    public void systemInit() {
        clearDB();
        createAdminRole();
        createAdminAccount();
    }

    public void createAdminRole() {
        Role role = new Role();
        role.setName("超级管理员");
        roleService.saveOrUpdate(role);
    }

    public void createAdminAccount() {

        Account account = new Account();
        account.setAccount("administrator");
        account.setPassword("123456");
        account.setRoleId(1);
        account.setName("超级管理员");
        account.setSex("男");
        account.setEmail("xsy870712@163.com");
        account.setMobile("15315086265");
        account.setIsEnable((short) 1);

        accountService.saveOrUpdate(account);
    }
}
