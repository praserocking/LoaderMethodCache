##LoaderMethodCache

Thread Local cache for a method's return value.

#### How it works?
* Wraps itself around a method by using Guice AOP's Method Interceptor.
* Uses a Manager which holds a Thread Local map.
* Manager holds the wrapped method's result on Thread local map.
* Returns the result from thread local when queried repeatedly.

#### How to use?
* Apply `@CacheMethod` annotation over the method you want to be cached.
* Call `MethodCacheManager.init()` before calling the method which needs to be cached.
* After finishing all the processing on thread, call `MethodCacheManager.flush()` for clearing the cache.

#### Best Usecases
* Multiple external GET calls on resource ***which is not bound to change*** across the thread's processing time.

#### Limitations
* Can be used only on resources which are not bound to change across the thread's time.
* Should be used only on GET resources with a `non-void` return type.
* Cache Invalidation is not possible within the thread's time.

Contact: Shenbaga Prasanna - shenbagaprasanna@gmail.com

