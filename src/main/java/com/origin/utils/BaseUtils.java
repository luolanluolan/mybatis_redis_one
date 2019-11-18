package com.origin.utils;

public class BaseUtils {

    private static int i =0 ;
    private static int threadCount =0 ;

    public synchronized static int addNum(){
        return ++i;
    }
    public synchronized static int addThreadNum(){
        return ++threadCount;
    }
}
