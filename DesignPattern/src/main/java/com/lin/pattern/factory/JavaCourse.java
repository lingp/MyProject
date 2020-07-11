package com.lin.pattern.factory;

public class JavaCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("Java 课程录制");
    }
}
