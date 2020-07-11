package com.lin.pattern.singleton.lazy;

public class LazySimpleSingleton {

    private static LazySimpleSingleton lazySimpleSingleton = null;


    private LazySimpleSingleton() {

    }

    /**
     * synchronized 加在方法里，可能锁住整个类
     */
    public synchronized static LazySimpleSingleton getInstance() {
        if (null == lazySimpleSingleton) {
            lazySimpleSingleton = new LazySimpleSingleton();
        }
        return lazySimpleSingleton;
    }



}
