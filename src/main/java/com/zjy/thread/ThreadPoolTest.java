package com.zjy.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author zjy
 * @des
 * @date 2019/6/3
 */
public class ThreadPoolTest {
    public static void main(String[] args) {

        /**
         * corePoolSize 核心线程数
         * maximumPoolSize 最大线程数
         * keepAliveTime 表示线程没有任务执行时最多保持多久时间会终止
         * TimeUnit keepAliveTime的单位
         * workQueue  内部任务队列 一般使用LinkedBlockingQueue和Synchronous。线程池的排队策略与BlockingQueue有关。
         *ArrayBlockingQueue：基于数组的先进先出队列，此队列创建时必须指定大小；
         * LinkedBlockingQueue：基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE；
         * synchronousQueue：这个队列比较特殊，它不会保存提交的任务，而是将直接新建一个线程来执行新来的任务。
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());



        /**
         * 固定核心线程数
         */
        ExecutorService exec2=Executors.newFixedThreadPool(5);
        /**
         * 核心线程数为0 当线程池中的线程都是处于活动状态时，线程池会创建新的线程来处理新任务
         */
        ExecutorService exec3=Executors.newCachedThreadPool();
        /**
         * 核心线程数和最大线程数都为1
         */
        ExecutorService exec4=Executors.newSingleThreadExecutor();


        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();
        for (int i = 0; i < 5; i++) {
            final int taskID = i;
            Future<Integer> future = exec3.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    int r =ThreadLocalRandom.current().nextInt(1000);
                    Thread.sleep(3);
                    return r;
                }
            });
            futureList.add(future);
        }

        for (int i = 0; i < 5; i++) {
            try {
               Integer result=futureList.get(i).get(50,TimeUnit.MILLISECONDS);
               //Integer result=futureList.get(i).get();
               System.out.println(result);
            } catch (Exception e) {
                // System.out.println(i+" 超时: ");
            }
        }
        exec3.shutdown();




    }
}
