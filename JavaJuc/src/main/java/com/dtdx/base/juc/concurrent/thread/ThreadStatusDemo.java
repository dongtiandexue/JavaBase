package com.dtdx.base.juc.concurrent.thread;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author dtdx
 * @ClassName ThreadStatusDemo
 * @Date 2020/2/23
 * <p>
 * 用这个小demo，展示下线程的几种状态时如何切换的，切换的条件是什么
 * NEW(初始状态)
 * RUNNABLE(运行状态)=READY(就绪) + RUNNING(执行)
 * BLKOCKED(阻塞状态)
 * WAITTING(等待状态)
 * TIMED_WAITTING(超时等待状态)
 * TERMINATED(结束状态)
 **/
public class ThreadStatusDemo {

    public static void main(String[] args) throws Exception {


//        演示线程处于Runnable状态
//        runnable();

//        演示线程处于Blocked状态
//        blocked();

//        演示线程处于Time Waitting状态
//        timeWaitting();

//        演示线程处于Waitting状态
//        waitting();

    }

    /**
     * 等待状态
     * Object.wait()、Thread.join()、 LockSupport.park() 这些方法可以让线程进入等待状态
     * 进入该状态的线程需要等待其他线程的通知或中断
     * @throws InterruptedException
     */
    private static void waitting() throws InterruptedException {
        Object obj = new Object();
        new Thread(() -> {
            synchronized (obj) {
                System.out.println(Thread.currentThread().getName() + "\t 获取锁");
                while (true) {
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t 线程运行了");
                }
            }
        }, "AA").start();

        TimeUnit.SECONDS.sleep(1);

        while (true) {
            synchronized (obj) {
                System.out.println(Thread.currentThread().getName() + "\t 线程运行了");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                obj.notifyAll();
            }

            TimeUnit.SECONDS.sleep(2);
        }
    }

    /**
     * 超时等待状态
     * Object.wait(long)、Thread.sleep(long)、Thread.join(long)、LockSupport.parkNanos()、LockSupport.parkUntil 这些方法都可以让线程进入超时等待状态
     * 不同于Waitting,可以在指定时间内自行返回
     */
    private static void timeWaitting() {
        new Thread(() -> {
            synchronized (ThreadStatusDemo.class) {
                System.out.println(Thread.currentThread().getName() + "\t 获取synchronized锁");
                try {
                    TimeUnit.MILLISECONDS.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();
    }

    /**
     * 阻塞状态
     * 线程阻塞于获取一个Monitor锁,进入synchronized修改的同步代码块或同步方法
     * @throws Exception
     */
    private static void blocked() throws Exception {
        new Thread(() -> {
            synchronized (ThreadStatusDemo.class) {
                System.out.println(Thread.currentThread().getName() + "\t 获取synchronized锁");
                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        TimeUnit.MILLISECONDS.sleep(300);

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 等待synchronized锁");
            synchronized (ThreadStatusDemo.class) {
                System.out.println(Thread.currentThread().getName() + "\t ======获取synchronized锁");
            }
        }, "BB").start();
    }

    /**
     * 运行状态
     * 处于可运行状态的线程正在JVM中执行，但它可能正在等待来自操作系统的其他资源，例如处理器。
     * Java线程将操作系统中的就绪(Ready)和运行(Running)两种状态笼统的称作"运行中(Runnbale)"
     */
    private static void runnable() {
        new Thread(() -> {
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "thread name").start();
    }

}
