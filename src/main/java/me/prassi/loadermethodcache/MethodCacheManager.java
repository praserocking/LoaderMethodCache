package me.prassi.loadermethodcache;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Method cache manager which provides functionality for
 * * Init cache
 * * Flush cache
 * * Put on cache
 * * Get from cache
 */
@Slf4j
public final class MethodCacheManager {

    /**
     * Thread local map which holds the response object against method signature.
     */
    private static ThreadLocal<Map<String, Object>> threadLocalMap = new ThreadLocal<>();

    /**
     * Flag to capture if cache is enabled for this thread or not.
     */
    private static ThreadLocal<Boolean> isEnabled = ThreadLocal.withInitial(() -> false);

    /**
     * Init the thread local cache by creating a new hashmap for this thread and enabling the flag.
     */
    public static void init() {
        if (threadLocalMap.get() == null) {
            log.info("Init Method Cache Manager - Enabling");
            threadLocalMap.set(new HashMap<>());
            isEnabled.set(true);
        }
    }

    /**
     * Put Impl into thread local map.
     *
     * @param method Method for which result is cached.
     * @param returnValue return value to cache.
     */
    public static void put(final Method method, final Object returnValue) {
        final Map<String, Object> localMap = Optional.ofNullable(threadLocalMap.get()).orElse(new HashMap<>());
        final String methodSignature = computeMethodSignature(method);
        log.info("Caching for method: " + methodSignature);
        localMap.put(methodSignature, returnValue);
        threadLocalMap.set(localMap);
    }

    /**
     * Get Impl from thread local map.
     *
     * @param method Method for which result is needed.
     * @return Optional of the cached value.
     */
    public static Optional<Object> get(final Method method) {
        final Map<String, Object> localMap = threadLocalMap.get();
        final String methodSignature = computeMethodSignature(method);
        log.info("Fetching cache for method: " + methodSignature);
        return Optional.ofNullable(localMap.get(methodSignature));
    }

    /**
     * Flush the thread local cache state and flip the flag.
     */
    public static void flush() {
        final Map<String, Object> localMap = threadLocalMap.get();
        localMap.clear();
        threadLocalMap.set(localMap);
        isEnabled.set(false);
        log.info("Flushing cache");
    }

    /**
     * @return if the cache is enabled or not.
     */
    public static boolean isEnabled() {
        return isEnabled.get();
    }

    /**
     * Method to compute method signature from method.
     */
    private static String computeMethodSignature(final Method method) {
        return method.getDeclaringClass().getCanonicalName() + "." + method.getName();
    }

    /**
     * To avoid instantiation.
     */
    private MethodCacheManager() {
    }
}
