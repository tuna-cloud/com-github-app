package com.github.app.api.plugin;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

public class HandlerPerformanceMonitor {
    private static MetricRegistry metrics = new MetricRegistry();

    public static Timer timerMeter(String resourceCode) {
        return metrics.timer(resourceCode);
    }
}
