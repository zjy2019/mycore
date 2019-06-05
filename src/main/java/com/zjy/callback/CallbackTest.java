package com.zjy.callback;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static javafx.scene.input.KeyCode.F;

/**
 * @author zjy
 * @des
 * @date 2019/6/5
 */
public class CallbackTest {
    public static void main(String[] args) {

        //调用异步任务
        new AsynTask().task(new MyCallback() {
            //实现回调方法
            @Override
            public void callback(Object object) {
                System.out.println("异步回调处理：值 "+object);
            }
        });

        System.out.println("主线程等待异步输出");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

interface MyCallback {

    /**
     * 定义处理回调方法
     * @param object
     */
    void callback(Object object);
}

class AsynTask {

    /**
     * 处理任务
     * @param myCallback 处理完任务后的回调
     */
    public void task(final MyCallback myCallback){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //线程睡眠3秒，模拟该线程执行时间过长，也就是上面说的【B口有东西塞住】
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //完成0到99的累加
                int sum=0;
                for(int i=0;i<100;i++){
                    sum+=i;
                }
                //将结果交给接口的实现类取处理
                myCallback.callback(sum);
            }
        });
        //启动线程
        thread.start();
    }
}
