package com.lin.pattern.delegate.simple;

public class EmployeeB implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("员工B，执行" +  command);
    }
}
