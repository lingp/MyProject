package com.lin.pattern.singleton.lazy;

public class LazyDoubleCheckSingleton {

    private volatile static LazyDoubleCheckSingleton lazySimpleSingleton = null;


    private LazyDoubleCheckSingleton() {

    }

    /**
     * 双重检查锁
     */
    public  static LazyDoubleCheckSingleton getInstance() {
        if (null == lazySimpleSingleton) {
            synchronized (LazyDoubleCheckSingleton.class) {
                if (null == lazySimpleSingleton) { // 避免另一个线程重复初始化
                    lazySimpleSingleton = new LazyDoubleCheckSingleton();
                }
            }
        }
        return lazySimpleSingleton;
    }



}
