package me.prassi.loadermethodcache;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class MethodCacheModule extends AbstractModule {
    @Override
    protected void configure() {
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(CacheMethod.class), new MethodCacheInterceptor());
    }
}
