package com.lin.pattern.factory;

public class PythonCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("Python课程 录制");
    }
}
