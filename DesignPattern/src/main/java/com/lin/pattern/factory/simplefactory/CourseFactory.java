package com.lin.pattern.factory.simplefactory;

import com.lin.pattern.factory.ICourse;
import com.lin.pattern.factory.JavaCourse;
import com.lin.pattern.factory.PythonCourse;

public class CourseFactory {

    public ICourse create(String name) {
        if ("java".equals(name)) {
            return new JavaCourse();
        } else if ("python".equals(name)) {
            return new PythonCourse();
        } else {
            return null;
        }
    }

    public ICourse create(Class clazz) {

        try {
            if (null != clazz) {
                return (ICourse) clazz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
