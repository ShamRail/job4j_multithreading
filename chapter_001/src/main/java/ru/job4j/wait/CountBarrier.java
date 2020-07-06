package ru.job4j.wait;

public class CountBarrier {

    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        count++;
    }

    public void await() {
        synchronized (monitor) {
            try {
                count();
                if (count < total) {
                    System.out.println(String.format("%s wait", Thread.currentThread().getName()));
                    wait();
                } else {
                    count = 0;
                    System.out.println();
                    System.out.println("Starting all threads");
                    System.out.println();
                    notifyAll();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    private static class ExampleThread implements Runnable {

        private final CountBarrier barrier;

        public ExampleThread(CountBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            barrier.await();
            System.out.println(String.format("%s work", Thread.currentThread().getName()));
        }
    }

    public static void main(String[] args) {
        int count = 10;
        CountBarrier barrier = new CountBarrier(count);
        for (int i = 0; i < count; i++) {
            new Thread(new ExampleThread(barrier)).start();
        }

    }

}