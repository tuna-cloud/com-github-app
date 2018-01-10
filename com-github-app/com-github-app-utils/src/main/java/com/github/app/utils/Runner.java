package com.github.app.utils;

public interface Runner {
    String name();

    void usage(StringBuilder builder);

    void start(String[] args);
}
