package com.lin.pattern.singleton;

import com.lin.pattern.singleton.seriable.SeriableSingleton;

import java.io.*;

public class LazySimpleSingletonTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        Thread thread1 = new Thread(new ExectorThread());
//        Thread thread2 = new Thread(new ExectorThread());
//        thread1.start();
//        thread2.start();
//        System.out.println("Exector End");

        SeriableSingleton s1 = null;
        SeriableSingleton s2 = null;

        s2 = SeriableSingleton.getInstance();

        FileOutputStream fos = null;

        fos = new FileOutputStream("testSeriableSingleton.obj");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(s2);
        oos.flush();
        oos.close();

        FileInputStream fis = new FileInputStream("testSeriableSingleton.obj");
        ObjectInputStream ois = new ObjectInputStream(fis);
        s1 = (SeriableSingleton) ois.readObject();
        ois.close();

        System.out.println(s1);
        System.out.println(s2);

        System.out.println(s2 == s1);

    }
}
