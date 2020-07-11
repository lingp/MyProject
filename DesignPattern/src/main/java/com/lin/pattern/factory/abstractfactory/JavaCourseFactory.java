package com.lin.pattern.factory.abstractfactory;

import com.lin.pattern.factory.ICourse;
import com.lin.pattern.factory.JavaCourse;

public class JavaCourseFactory implements ICourseFactory {
    @Override
    public ICourse createCourse() {
        return new JavaCourse();
    }

    @Override
    public INote createNote() {
        return new JavaNote();
    }

    @Override
    public IVedio createVedio() {
        return new JavaVedio();
    }
}
