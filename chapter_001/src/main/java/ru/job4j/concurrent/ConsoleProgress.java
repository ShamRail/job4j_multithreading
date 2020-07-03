package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        try {
            //char[] symbols = {'\\', '|', '/'};
            String[] symbols = {".", "..", "..."};
            int index = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rLoading " + symbols[index]);
                index = (index + 1) % symbols.length;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.print("\rLoaded");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt(); //
    }

}
