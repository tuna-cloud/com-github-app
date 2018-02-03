package com.github.app.api.services;

import org.springframework.stereotype.Component;

@Component
public interface AccountService {

    boolean authLogin(String account, String password);


}
