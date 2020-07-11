package com.lin.pattern.singleton.lazy;

import java.io.Serializable;

// 性能最优的一种写法
public class LazyInnerClassSingleton implements Serializable {

    // 虽然构造方法私有了，但是可以被反射
    private LazyInnerClassSingleton() {
        if (LazyHolder.LAZY_INNER_CLASS_SINGLETON != null) {
            throw new RuntimeException("不允许构建多个实例");
        }
    }

    /**
     * 懒汉式单例
     *
     * 巧妙利用了内部类的特点
     * JVM底层执行逻辑，完美避免了线程安全问题
     * @return
     */
    public static final LazyInnerClassSingleton getInstance() {
        return LazyHolder.LAZY_INNER_CLASS_SINGLETON;
    }


    private static class LazyHolder {
        private static final LazyInnerClassSingleton LAZY_INNER_CLASS_SINGLETON = new LazyInnerClassSingleton();
    }

    /**
     * 防止序列化破坏实例
     * */
    private Object readResolve() {
        return getInstance();
    }

}
