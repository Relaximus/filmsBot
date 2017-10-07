package org.telegram.bot.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private Map<String, Object> storage;

    Session() {
        storage = new HashMap<>();
    }

    public <T> T getAttribute(String name) {
        return (T) storage.get(name);
    }

    public <T> void addAttribute(String name, T param){
        storage.put(name, param);
    }
}
