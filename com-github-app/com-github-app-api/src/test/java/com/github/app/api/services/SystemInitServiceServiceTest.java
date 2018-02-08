package com.github.app.api.services;

import com.github.app.api.dao.domain.Account;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SystemInitServiceServiceTest extends BaseServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
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
