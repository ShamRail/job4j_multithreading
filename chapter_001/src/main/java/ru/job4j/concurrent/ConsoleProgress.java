package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        String[] symbols = {".", "..", "..."};
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\rLoading " + symbols[index]);
                index = (index + 1) % symbols.length;
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.out.println();
                System.out.println(Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
                System.out.print("\rLoaded");
                System.out.println();
                System.out.println(Thread.currentThread().isInterrupted());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(3000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt(); //
    }

}
