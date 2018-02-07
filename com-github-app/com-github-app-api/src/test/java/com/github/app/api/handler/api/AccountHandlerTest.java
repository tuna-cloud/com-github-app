package com.github.app.api.handler.api;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

public class AccountHandlerTest extends BaseRestClientTest{

    @Test
    public void testCurrentLogin(TestContext context) {
        Async async = context.async();
        post("/api/account/list", new JsonObject(), rep -> {
            JsonObject jsonObject = rep.result().bodyAsJsonObject();
            System.out.println(jsonObject.toString());
            context.assertEquals(jsonObject.getInteger("code"), 0);
            async.complete();
        });
    }
}