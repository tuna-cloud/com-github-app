package com.github.app.api.bll;

import io.vertx.core.json.JsonObject;

public interface SystemOperationService {
    /**
     *
     * @param jsonObject
     */
    void install(JsonObject jsonObject);

    void backup(JsonObject jsonObject);

    void restore(JsonObject jsonObject, String fileName);
}
