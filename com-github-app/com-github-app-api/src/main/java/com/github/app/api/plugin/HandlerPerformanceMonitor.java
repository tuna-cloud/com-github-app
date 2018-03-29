package com.github.app.api.plugin;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.github.app.api.dao.domain.Popedom;
import com.github.app.api.utils.PopedomContext;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.reporters.JmxReporter;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HandlerPerformanceMonitor {
    private static MetricRegistry metrics;

    static {
        metrics = new MetricRegistry();
        JmxReporter.forRegistry(metrics).build().start();
    }

    public static void update(RoutingContext routingContext, long duration) {
        Popedom popedom = PopedomContext.getInstance().match(routingContext);
        if (popedom != null) {
            metrics.timer(popedom.getCode()).update(duration, TimeUnit.NANOSECONDS);
        }
    }

    public static JsonArray listMetric(int rows, int offset, String sort) {
        List<JsonObject> list = new ArrayList<>();
        for (Popedom popedom : PopedomContext.getInstance().getPopedoms()) {
            JsonObject jsonObject = JsonObject.mapFrom(popedom);
            jsonObject.put("count", metrics.timer(popedom.getCode()).getCount());
            jsonObject.put("meanRate", metrics.timer(popedom.getCode()).getMeanRate());
            jsonObject.put("fifteenMinuteRate", metrics.timer(popedom.getCode()).getFifteenMinuteRate());
            jsonObject.put("fiveMinuteRate", metrics.timer(popedom.getCode()).getFiveMinuteRate());
            jsonObject.put("oneMinuteRate", metrics.timer(popedom.getCode()).getOneMinuteRate());
            Snapshot snapshot = metrics.timer(popedom.getCode()).getSnapshot();
            jsonObject.put("stdDev", snapshot.getStdDev());
            jsonObject.put("max", snapshot.getMax());
            jsonObject.put("min", snapshot.getMin());
            jsonObject.put("50thPercentile", snapshot.getMedian());
            jsonObject.put("75thPercentile", snapshot.get75thPercentile());
            jsonObject.put("95thPercentile", snapshot.get95thPercentile());
            jsonObject.put("98thPercentile", snapshot.get98thPercentile());
            jsonObject.put("99thPercentile", snapshot.get99thPercentile());
            jsonObject.put("999thPercentile", snapshot.get999thPercentile());
            jsonObject.put("mean", snapshot.getMean());
            list.add(jsonObject);
        }

        list.sort((o1, o2) -> Double.compare(o2.getDouble(sort), o1.getDouble(sort)));

        JsonArray jsonArray = new JsonArray();

        for (int i = rows; i < rows + offset; i++) {
            jsonArray.add(list.get(i));
        }

        return jsonArray;
    }

    public static int totalSize() {
        return metrics.getTimers().size();
    }
}
