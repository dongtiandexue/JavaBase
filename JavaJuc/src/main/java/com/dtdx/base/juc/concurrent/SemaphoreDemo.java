package com.dtdx.base.juc.concurrent;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author dtdx
 * @ClassName SemaphoreDemo
 * @Date 2020/2/17
 * @Version 1.0
 * Semaphore 是JUC包中的一个工具类，中文含义是信号量
 **/
public class SemaphoreDemo {

    public static void main(String[] args) {

        //模拟三个停车位
        Semaphore semaphore = new Semaphore(3);

        //模拟6辆汽车
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 车\t 抢到停车位。。。");
                    int stopTime = new Random().nextInt(10);
                    TimeUnit.SECONDS.sleep(stopTime);
                    System.out.println(Thread.currentThread().getName() +" 车 "+stopTime+"秒后离开。。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
