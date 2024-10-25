package com.gec.system.util;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolFactory {
    public static ThreadPoolExecutor myThreadPool  = new ThreadPoolExecutor(10,20,1L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(10), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    
}
