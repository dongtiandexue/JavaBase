package com.dtdx.base.juc.concurrent.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author huawei
 * @ClassName ThreadPoolDemo
 * @Date dtdx
 **/
public class ThreadPoolDemo {

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(1);
    }
}
