package com.locklearn;

/**
 * @author LL
 * @date 2019/11/1 14:15
 * @describe
 */
public class TestThreadA {
    public static void main(String[] args){
        Service service = new Service();
        for (int i = 0; i < 50; i++) {
            ThreadA threadA = new ThreadA(service);
            threadA.start();
        }
    }
}
