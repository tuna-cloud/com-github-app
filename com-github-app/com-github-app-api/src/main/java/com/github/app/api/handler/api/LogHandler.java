package com.github.app.api.handler.api;

import com.github.app.api.dao.domain.Log;
import com.github.app.api.handler.UriHandler;
import com.github.app.api.services.LogService;
import com.github.app.api.utils.RequestUtils;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogHandler implements UriHandler {

	@Autowired
	private LogService logService;

	@Override
	public void registeUriHandler(Router router) {
		router.get().path("/log").produces(CONTENT_TYPE).blockingHandler(this::list, false);
		router.delete().path("/log").produces(CONTENT_TYPE).blockingHandler(this::truncate, false);
	}

	public void list(RoutingContext routingContext) {
		MultiMap map = routingContext.queryParams();

		List<Log> list = logService.find(map.get("code"), map.get("account"), RequestUtils.getLong(map, "startTime"), RequestUtils.getLong(map, "endTime"), RequestUtils.getInteger(map, "offset"), RequestUtils.getInteger(map, "rows"));
		long total = logService.count(map.get("code"), map.get("account"), RequestUtils.getLong(map, "startTime"), RequestUtils.getLong(map, "endTime"));

		JsonObject data = new JsonObject();
		data.put("list", list);
		data.put("total", total);

		responseSuccess(routingContext, data);
	}

	public void truncate(RoutingContext routingContext) {
		logService.truncate();
		responseSuccess(routingContext);
	}
}
