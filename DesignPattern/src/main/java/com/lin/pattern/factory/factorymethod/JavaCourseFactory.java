package com.lin.pattern.factory.factorymethod;

import com.lin.pattern.factory.ICourse;
import com.lin.pattern.factory.JavaCourse;

public class JavaCourseFactory implements ICourseFactory {
    @Override
    public ICourse create() {
        return new JavaCourse();
    }
}
