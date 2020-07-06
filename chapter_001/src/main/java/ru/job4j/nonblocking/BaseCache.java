package ru.job4j.nonblocking;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseCache {

    private Map<Integer, Base> cache = new ConcurrentHashMap<>();

    public boolean add(Base base) {
        return cache.putIfAbsent(base.getId(), base) == base;
    }

    public void update(Base base) {
        cache.computeIfPresent(base.getId(), (key, oldValue) -> {
            if (base.getVersion() != oldValue.getVersion()) {
                throw new OptimisticException("Invalid version");
            }
            oldValue.setVersion(oldValue.getVersion() + 1);
            oldValue.setName(base.getName());
            return oldValue;
        });
    }

    public Base delete(Base base) {
        return cache.remove(base.getId());
    }

}
