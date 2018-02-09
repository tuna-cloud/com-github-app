package com.github.app.api.services;

import com.github.app.api.dao.domain.Account;

import java.util.List;

public interface AccountService {

    boolean authLogin(String account, String password);

    Account getAccountByAccountId(Integer accountId);

    Account getAccountByAccountOrMobileOrEmail(String account, String mobile, String email);

    List<Account> findByKeyWord(Integer roleId, String keyword, Integer offset, Integer rows);

    long countByKeyWord(Integer roleId, String keyword);

    void saveOrUpdate(Account account);

    void resetPassword(Integer accountId);

    int deleteByAccountId(Integer accountId);

    void truncate();
}
