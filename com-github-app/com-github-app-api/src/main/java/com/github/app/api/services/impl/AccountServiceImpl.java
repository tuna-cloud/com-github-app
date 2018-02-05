package com.github.app.api.services.impl;

import com.github.app.api.dao.domain.Account;
import com.github.app.api.dao.domain.AccountExample;
import com.github.app.api.dao.mapper.AccountMapper;
import com.github.app.api.services.AccountService;
import com.github.app.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public boolean authLogin(String account, String password) {
        if(StringUtils.isEmpty(account))
            return false;
        if(StringUtils.isEmpty(password))
            return false;

        AccountExample example = new AccountExample();
        example.createCriteria().andAccountEqualTo(account).andIsEnableEqualTo((short) 1);
        Account acc = accountMapper.selectOneByExample(example);

        if(acc == null)
            return false;

        if (!MD5Utils.validateMd5WithSalt(password, acc.getPassword())) {
            return false;
        }

        return true;
    }

}
