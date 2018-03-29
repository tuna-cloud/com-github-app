package com.github.app.api.plugin;

import com.codahale.metrics.MetricRegistry;
import com.github.app.api.dao.domain.Popedom;
import com.github.app.api.utils.PopedomContext;
import io.vertx.ext.dropwizard.reporters.JmxReporter;
import io.vertx.ext.web.RoutingContext;

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
}
