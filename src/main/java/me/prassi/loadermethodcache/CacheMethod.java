package me.prassi.loadermethodcache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which enables caching of the method's result, within the thread context.
 *
 * To init method caching,
 * --> MethodCacheManager.init() for starting the cache.
 * --> MethodCacheManager.flush() for clearing the cache.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheMethod { }
