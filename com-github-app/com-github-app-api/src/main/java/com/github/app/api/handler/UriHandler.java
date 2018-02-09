package com.github.app.api.handler;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * json format
 * {
 * "code" : 0,
 * "msg"  : "success",
 * "data": {}
 * }
 */
public interface UriHandler {
    Logger logger = LogManager.getLogger(UriHandler.class);

    String CONTENT_TYPE = "application/json;charset=UTF-8";

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
        Map map = new HashMap();
        map.put("code", code);
        if (msg != null) {
            map.put("msg", msg);
        }
        if (data != null) {
            map.put("data", data);
        }
        Buffer buffer = JsonObject.mapFrom(map).toBuffer();
        logger.info(routingContext.request().path() + "-->" + buffer.toString());
        routingContext.response().end(buffer);
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
    default void responseOperationAuthFailure(RoutingContext routingContext, String msg) {
        response(routingContext, CODE_API_AUTHENTICATION_FAILED, msg);
    }

    /**
     *
     * @param routingContext
     */
    default void responseFailure(RoutingContext routingContext) {
        if(routingContext.failed()) {
            responseFailure(routingContext, routingContext.failure());
        } else {
            responseFailure(routingContext, "unknow server error");
        }
    }

    /**
     *
     * @param routingContext
     * @param e
     */
    default void responseFailure(RoutingContext routingContext, Exception e) {
        responseFailure(routingContext, e);
    }

    /**
     *
     * @param routingContext
     * @param throwable
     */
    default void responseFailure(RoutingContext routingContext, Throwable throwable) {
        throwable.printStackTrace();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        throwable.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        responseFailure(routingContext, exception);
    }

    /**
     *
     * @param routingContext
     */
    default void responseSuccess(RoutingContext routingContext) {
        response(routingContext, CODE_SUCCESS, "success");
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
