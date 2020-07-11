package com.lin.pattern.singleton;

import com.lin.pattern.delegate.simple.Boss;
import com.lin.pattern.delegate.simple.Leader;

public class DelegateTest {
    public static void main(String[] args) {
        new Boss().command("加密", new Leader());
    }
}
