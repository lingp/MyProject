package com.lin.pattern.factory.factorymethod;

import com.lin.pattern.factory.ICourse;
import com.lin.pattern.factory.PythonCourse;

public class PythonCourseFactory implements ICourseFactory {

    @Override
    public ICourse create() {
        return new PythonCourse();
    }
}
