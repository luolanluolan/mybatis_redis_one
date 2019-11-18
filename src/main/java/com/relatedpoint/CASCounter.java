package com.relatedpoint;

/**
 * @author LL
 * @date 2019/10/31 14:29
 */
public class CASCounter implements Runnable{

    private SimilatedCAS counter = new SimilatedCAS();

    public static void main(String[] args) {
        Runnable run = new CASCounter();
        new Thread(run).start();
        new Thread(run).start();
        new Thread(run).start();
        new Thread(run).start();
        new Thread(run).start();
        new Thread(run).start();

    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(this.increment());
        }
    }

    public int increment(){

        int oldValue = counter.getValue();
        int newValue = oldValue + 1;

        while(!counter.compareAndSwap(oldValue, newValue)){
            oldValue = counter.getValue();
            newValue = oldValue + 1;
        }
        return newValue;
    }
}

class SimilatedCAS{

    private int value;

    public int getValue(){
        return value;
    }

    public synchronized boolean compareAndSwap(int expectedValue, int newValue){
        if(value == expectedValue){
            value = newValue;
            return true;
        }
        return  false;
    }
}