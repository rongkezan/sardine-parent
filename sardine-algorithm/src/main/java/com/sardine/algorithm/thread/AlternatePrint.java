package com.sardine.algorithm.thread;

/**
 * 多线程交替打印 1-100
 * <p>
 * 使用到三个方法:
 * wait()       一旦执行此方法，当前线程就会进入阻塞状态，并释放同步监视器
 * notify()     一旦执行此方法，就会唤醒被wait的一个线程，如果有多个线程被wait，就唤醒优先级高的那个
 * notifyAll()  一旦执行此方法，就会唤醒所有被wait的线程
 * <p>
 * 说明:
 * 1. wait(), notify(), notifyAll() 只能出现在同步代码块中
 * 2. wait(), notify(), notifyAll() 的调用者必须是同步代码块或同步方法中的同步监视器
 * <p>
 * 面试题: sleep() 和 wait() 异同？
 * 1. 相同点: 都可以让当前线程进入阻塞状态
 * 2. 不同点:  (1) 声明的位置不同: Thread类声明sleep()，Object类声明wait()
 * (2) 调用范围不同: sleep()可以在任何场景调用，wait()方法必须使用在同步代码块或同步方法中
 * (3) 关于是否释放同步监视器: 如果两个方法都使用在同步代码块或同步方法中,sleep()不会释放锁，wait()会释放锁
 */
public class AlternatePrint {

    public static void main(String[] args) {
        Printer printer = new Printer();
        for (int i = 1; i <= 10; i++) {
            new Thread(printer::print, String.valueOf(i)).start();
        }
    }

    private static class Printer {

        private int count = 1;

        public void print() {
            while (count <= 100) {
                synchronized (this) {
                    System.out.println(count);
                    count++;
                    notifyAll();
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
