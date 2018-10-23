package com.niuzj.task;

public class MyTask implements Runnable{

    private int tasknum;

    public MyTask(int tasknum) {
        this.tasknum = tasknum;
    }

    public void run() {
        System.out.println("正在执行task" + tasknum);
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task" + tasknum + "执行完毕");

    }
}
