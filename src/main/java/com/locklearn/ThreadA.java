package com.locklearn;

/**
 * @author LL
 * @date 2019/11/1 14:14
 * @describe
 */
public class ThreadA extends Thread{
    private Service service;

    public ThreadA(Service service){
        this.service = service;
    }

    public void run(){
        service.seckill();
    }

}
