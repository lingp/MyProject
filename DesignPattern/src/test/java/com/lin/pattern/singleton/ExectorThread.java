package com.lin.pattern.singleton;


import com.lin.pattern.singleton.lazy.LazyDoubleCheckSingleton;
import com.lin.pattern.singleton.lazy.LazySimpleSingleton;

public class ExectorThread implements Runnable {
    @Override
    public void run() {
//        LazySimpleSingleton lazySimpleSingleton = LazySimpleSingleton.getInstance();
        LazyDoubleCheckSingleton lazyDoubleCheckSingleton = LazyDoubleCheckSingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + ":" + lazyDoubleCheckSingleton);
    }
}
