package me.prassi.loadermethodcache;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class CacheMethodInterceptorTest {
    @Test
    public void testMethodWithMethodCache() {
        Injector injector = Guice.createInjector(new TestModule());
        ClassUnderTest testClass = injector.getInstance(ClassUnderTest.class);
        MethodCacheManager.init();
        final String str1 = testClass.getRandomString();
        final String str2 = testClass.getRandomString();
        MethodCacheManager.flush();
        Assert.assertEquals(str1, str2);
    }

    @Test
    public void testMethodWithoutMethodCache() {
        Injector injector = Guice.createInjector(new TestModule());
        ClassUnderTest testClass = injector.getInstance(ClassUnderTest.class);
        final String str1 = testClass.getRandomString();
        final String str2 = testClass.getRandomString();
        Assert.assertNotEquals(str1, str2);
    }
}

class TestModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new MethodCacheModule());
        bind(ClassUnderTest.class);
    }
}

class ClassUnderTest {
    @CacheMethod
    public String getRandomString() {
        return UUID.randomUUID().toString();
    }
}
