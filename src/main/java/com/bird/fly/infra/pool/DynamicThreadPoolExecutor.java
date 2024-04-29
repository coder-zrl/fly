package com.bird.fly.infra.pool;

import java.util.concurrent.*;

/**
 * todo 待实现 动态线程池，支持动态调整核心线程数和最大线程数
 * 使用方法：
 *
 * @author zhangruilong <zhangruilong03@kuaishou.com>
 * Created on 2024-04-29
 */
public class DynamicThreadPoolExecutor extends ThreadPoolExecutor {
    private int targetCorePoolSize;
    private int targetMaximumPoolSize;

    public DynamicThreadPoolExecutor(int initialCorePoolSize, int initialMaximumPoolSize, long keepAliveTime, TimeUnit unit) {
        super(initialCorePoolSize, initialMaximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<>());
        this.targetCorePoolSize = initialCorePoolSize;
        this.targetMaximumPoolSize = initialMaximumPoolSize;
    }

    public void setCorePoolSize(int newCorePoolSize) {
        synchronized (this) {
            if (newCorePoolSize > targetCorePoolSize) {
                targetCorePoolSize = newCorePoolSize;
                for (int i = 0; i < newCorePoolSize - getCorePoolSize(); i++) {
                    prestartCoreThread();
                }
            } else if (newCorePoolSize < targetCorePoolSize) {
                targetCorePoolSize = newCorePoolSize;
                while (getPoolSize() > newCorePoolSize) {
                    try {
                        super.shutdownNow();
                        break;
                    } catch (Exception e) {
                        // Handle exception
                    }
                }
            }
        }
    }

    public void setMaximumPoolSize(int newMaximumPoolSize) {
        synchronized (this) {
            if (newMaximumPoolSize > targetMaximumPoolSize) {
                targetMaximumPoolSize = newMaximumPoolSize;
            } else if (newMaximumPoolSize < targetMaximumPoolSize) {
                targetMaximumPoolSize = newMaximumPoolSize;
                while (getLargestPoolSize() > newMaximumPoolSize) {
                    try {
                        super.shutdownNow();
                        break;
                    } catch (Exception e) {
                        // Handle exception
                    }
                }
            }
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        // 在这里可以添加额外的逻辑，比如监控线程池状态并根据需要调整大小
    }

    // 其他可能需要重写的方法，如beforeExecute和terminated
    public static void main(String[] args) {
        // 初始化动态线程池
        DynamicThreadPoolExecutor executor = new DynamicThreadPoolExecutor(
                5, // 初始核心线程数
                10, // 初始最大线程数
                60, // 空闲线程存活时间
                TimeUnit.SECONDS // 时间单位
        );

        // 提交任务
        for (int i = 0; i < 100; i++) {
            Runnable worker = () -> {
                printThreadPoolInfo(executor);
                System.out.println("Worker " + Thread.currentThread().getName() + " is working");
                try {
                    Thread.sleep(1000); // 模拟耗时操作

                    // 动态调整线程池大小
                    executor.setCorePoolSize(7); // 增加核心线程数
                    executor.setMaximumPoolSize(8); // 减小最大线程数
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(worker);
        }


        // 关闭线程池
        executor.shutdown();
    }
    private static void printThreadPoolInfo(DynamicThreadPoolExecutor executor) {
        System.out.println("Current Core Pool Size: " + executor.getCorePoolSize());
        System.out.println("Current Max Pool Size: " + executor.getMaximumPoolSize());
    }
}