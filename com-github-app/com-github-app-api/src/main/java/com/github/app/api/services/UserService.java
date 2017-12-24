package com.github.app.api.services;

import io.vertx.core.json.JsonObject;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    boolean auth(String account, String password);

    JsonObject selectUserInfoByAccount(String account);
}
