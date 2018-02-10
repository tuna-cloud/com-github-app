package com.github.app.api.utils;

import com.github.app.api.dao.domain.Popedom;

import java.util.List;
import java.util.regex.Pattern;

public class PopedomContext {
    private List<Popedom> popedoms;

    private static PopedomContext context;

    public static void init(List<Popedom> list) {
        context = new PopedomContext();
        context.setPopedoms(list);
    }

    public static PopedomContext getInstance() {
        if(context == null)
            throw new RuntimeException("PopedomContext is not initialize");
        return context;
    }

    private PopedomContext() {
    }

    public List<Popedom> getPopedoms() {
        return popedoms;
    }

    public void setPopedoms(List<Popedom> popedoms) {
        this.popedoms = popedoms;
    }

    public Popedom match(String uri) {
        for (Popedom popedom : popedoms) {
            if(Pattern.matches(popedom.getCode(), uri)) {
                return popedom;
            }
        }
        return null;
    }
}
