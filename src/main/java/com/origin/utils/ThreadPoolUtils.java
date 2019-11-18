package com.origin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author LL
 * @date 2019/10/29 9:43
 * 创建多个线程，，模拟并发访问
 */

public class ThreadPoolUtils {

    Logger  logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) throws Exception {

        //创建线程池
        ExecutorService es = new ThreadPoolExecutor(2000, 4000, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(4000) , new ThreadFactory(){
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("线程编号 "+BaseUtils.addNum()+"");
                System.out.println("创建线程"+thread.getName()+ System.currentTimeMillis());
                return thread;
            }
        });

        while(true){
            String next = null;
            int count = 0;
            try{
                System.out.println("--------------------请输入访问地址------------------");
                Scanner scannerUrl = new Scanner(System.in);
                next = scannerUrl.next();
                System.out.println("-------------------请输入访问次数-------------------");
                Scanner scannerCount = new Scanner(System.in);
                count = scannerCount.nextInt();
            }catch (Exception e){
                e.printStackTrace();
                Thread.sleep(1000);
                continue;
            }

            //通过线程池访问连接
            for(int i=0;i<count;i++){
                UrlTest oneUrlTest = ThreadPoolUtils.getOneUrlTest(next);
                Future<?> future = es.submit(oneUrlTest);
            }

            //查看线程池所有线程是否执行完毕 线程池的活跃数
            while(true){
                int activeCount = ((ThreadPoolExecutor) es).getActiveCount();
                if(activeCount==0){
                    Thread.sleep(5000);//线程等待0.5s,让worker线程把数据处理完成
                    break;
                }
            }
        }

    }

    public static UrlTest getOneUrlTest(String url){
        return new UrlTest(url); //通过输入的url获取连接测试
    }

    private static class UrlTest implements Callable{

        private String url;

        public UrlTest(String url){
            this.url = url;
        }

        @Override
        public Object call() throws Exception {
            String s = "";
            try{
                s =  ThreadPoolUtils.visitUrl(url); //url访问连接
            }catch (Exception e){
                System.out.println(Thread.currentThread().getName()+"执行访问url的时候出现异常");
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"执行完毕,结果是    "+s);
            return s;
        }
    }

    //url访问连接
    public static String visitUrl(String url) throws Exception{
        URL target = new URL(url);
        URLConnection connection = target.openConnection();
        InputStream inputStream = connection.getInputStream();
        byte[] bytes=new byte[1024];
        int len=0;
        StringBuilder sb=new StringBuilder();
        while((len=inputStream.read(bytes))!=-1){
            sb.append(new String (bytes,0,len));
        }
        inputStream.close();
        return sb.toString();
    }
}
