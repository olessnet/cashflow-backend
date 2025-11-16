package app.theblu.cashflow.cs.zone.recognizer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
@Slf4j
public class CacheClearService {
    private final CacheManager cacheManager;

    public CacheClearService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void clearCache() {
        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
    }

    public HashMap<String, Object> printCache() {
        var names = cacheManager.getCacheNames();
        var map = new HashMap<String, Object>();
        for (String name : names) {
            var cache = cacheManager.getCache(name);
            CaffeineCache caffeineCache = (CaffeineCache) cache;
            Set<Object> allKeys = caffeineCache.getNativeCache().asMap().keySet();

            map.put(name, allKeys);
        }
        return map;
    }
}
