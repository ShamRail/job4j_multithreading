package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final int poolSize = Runtime.getRuntime().availableProcessors();

    private final List<Thread> pool = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(poolSize);;

    public ThreadPool() {
        this.initPool();
    }

    private void initPool() {
        for (int i = 0; i < poolSize; i++) {
            pool.add(new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Runnable job = tasks.poll();
                        job.run();
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        pool.forEach(Thread::interrupt);
    }

}
