package com.lin.pattern.delegate.simple;

public class EmployeeA implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("员工A，执行" +  command);
    }
}
