package com.zjy.thread;

/**
 * @author zjy
 * @des
 * @date 2019/6/3
 */
public class ThreadTest {
    public static void main(String[] args) {
        MyThread myThread=new MyThread("thread1");
        Thread thread=new Thread(myThread);
        thread.start();
        System.out.println(" thread started");

        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        System.out.println("thred2" + ": " + i);
                        Thread.sleep(2000);
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        thread1.start();

        System.out.println(" thread started2");

    }
}

class MyThread implements Runnable {

    private String name;

    public MyThread(String name) {
        this.name = name;
    }
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(name + ": " + i);
                Thread.sleep(2000);
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
