package com.github.app.api.handler;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * json format
 * {
 * "code" : 0,
 * "msg"  : "success",
 * "data": {}
 * }
 */
public interface UriHandler {
    Logger logger = LoggerFactory.getLogger(UriHandler.class);
    /**
     * api operation success
     */
    int CODE_SUCCESS = 0;
    /**
     * JWT token illegal
     */
    int CODE_JWT_TOKEN_INVALIDATE = -1;
    /**
     * JWT token timeout
     */
    int CODE_JWT_TOKEN_TIMEOUT = -2;
    /**
     * JWT token not filled into http headers with name {X-Token}
     */
    int CODE_JWT_TOKEN_MISSING = -3;
    /**
     * This user have logined
     */
    int CODE_USER_HAVE_LOGINED = -4;
    /**
     * api operation failed
     */
    int CODE_API_OPERATION_FAILED = -5;
    /**
     * api operation authentication failed
     */
    int CODE_API_AUTHENTICATION_FAILED = -6;


    /**
     * @param router
     */
    void registeUriHandler(Router router);

    /**
     * @param routingContext
     * @param code
     * @param msg
     */
    default void response(RoutingContext routingContext, Integer code, String msg) {
        response(routingContext, code, msg, null);
    }

    /**
     * @param routingContext
     * @param code
     * @param data
     */
    default void response(RoutingContext routingContext, Integer code, Object data) {
        response(routingContext, code, null, data);
    }

    /**
     * @param routingContext
     * @param code
     * @param msg
     * @param data
     */
    default void response(RoutingContext routingContext, Integer code, String msg, Object data) {
        JsonObject jsonObject = new JsonObject().put("code", code);
        if (msg != null) {
            jsonObject.put("msg", msg);
        }
        if (data != null) {
            jsonObject.put("data", data);
        }
        Buffer buffer = jsonObject.toBuffer();
        logger.info(routingContext.request().path() + "-->" + buffer.toString());
        routingContext.response().end(jsonObject.toBuffer());
    }

    /**
     * @param routingContext
     * @param msg
     */
    default void responseFailure(RoutingContext routingContext, String msg) {
        response(routingContext, CODE_API_OPERATION_FAILED, msg);
    }

    /**
     * @param routingContext
     * @param msg
     */
    default void responseSuccess(RoutingContext routingContext, String msg) {
        response(routingContext, CODE_SUCCESS, msg);
    }

    /**
     * @param routingContext
     * @param data
     */
    default void responseSuccess(RoutingContext routingContext, Object data) {
        response(routingContext, CODE_SUCCESS, null, data);
    }

    /**
     * @param routingContext
     * @param msg
     * @param data
     */
    default void responseSuccess(RoutingContext routingContext, String msg, Object data) {
        response(routingContext, CODE_SUCCESS, msg, data);
    }

}
