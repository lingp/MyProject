package com.lin.pattern.factory.abstractfactory;

import com.lin.pattern.factory.ICourse;

public interface ICourseFactory {
    public ICourse createCourse();

    public INote createNote();

    public IVedio createVedio();
}
