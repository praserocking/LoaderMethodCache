package me.prassi.loadermethodcache;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Method interceptor which enables method result caching with thread local.
 */
@Slf4j
public class MethodCacheInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
        if (MethodCacheManager.isEnabled()) {
            final Method method = methodInvocation.getMethod();
            final Optional<Object> cachedObj = MethodCacheManager.get(method);
            if (cachedObj.isPresent()) {
                log.info("Method CacheHit");
                return cachedObj.get();
            } else {
                final Object result = methodInvocation.proceed();
                log.info("Method CacheMiss, Caching");
                MethodCacheManager.put(method, result);
                return result;
            }
        } else {
            log.info("MethodCache is disabled");
            return methodInvocation.proceed();
        }
    }
}
