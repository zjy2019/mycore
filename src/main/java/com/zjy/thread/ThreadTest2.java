package com.zjy.thread;

/**
 * @author zjy
 * @des
 * @date 2019/6/3
 */
public class ThreadTest2 {
    public static void main(String[] args) {

        Thread thread=new MyThread2("thread2");
        thread.start();
        System.out.println(" thread started");
    }
}

class MyThread2 extends Thread{
    public MyThread2(String name) {
        super(name);
    }

    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(currentThread().getName() + ": " + i);
                Thread.sleep(2000);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
