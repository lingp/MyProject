package com.lin.pattern.factory.simplefactory;

import com.lin.pattern.factory.ICourse;
import com.lin.pattern.factory.JavaCourse;

public class SimpleFactoryTest {
    public static void main(String[] args) {
        CourseFactory factory = new CourseFactory();
        ICourse course =  factory.create("java");
        course.record();

        ICourse course1 = factory.create(JavaCourse.class);
        course1.record();
    }
}
