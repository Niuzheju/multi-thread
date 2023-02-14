package com.niuzj.task;

import java.util.concurrent.RecursiveTask;

/**
 * @author niuzheju
 * @date 15:59 2021/3/16
 */
public class MyForkJoinTask extends RecursiveTask<Integer> {

    private final int MAX = 200;

    private Integer startValue;

    private Integer endValue;

    public MyForkJoinTask(Integer startValue, Integer endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
    }

    @Override
    protected Integer compute() {
        if (endValue - startValue < 200) {
            System.out.println("开始计算, " + startValue + "-->" + endValue);
            int totalValue = 0;
            for (int i = startValue; i <= endValue; i++) {
                totalValue += i;
            }
            return totalValue;
        } else {
            MyForkJoinTask subTask1 = new MyForkJoinTask(startValue, (startValue + endValue) / 2);
            subTask1.fork();
            MyForkJoinTask subTask2 = new MyForkJoinTask((startValue + endValue) / 2 + 1, endValue);
            subTask2.fork();
            return subTask1.join() + subTask2.join();
        }
    }
}
