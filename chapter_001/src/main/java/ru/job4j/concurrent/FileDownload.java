package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class FileDownload {

    private static final int KB = 1024;

    private static final int SECOND = 1000;

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }
        new Thread(new FileDownload1(args)).start();
    }

    public static class FileDownload1 implements Runnable {

        private final String[] args;

        public FileDownload1(String[] args) {
            this.args = args;
        }

        @Override
        public void run() {
            try {
                String file = args[0];
                int kbs = Integer.parseInt(args[1]);
                URL url = new URL(file);
                long start = System.currentTimeMillis();
                try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
                    byte[] dataBuffer = new byte[kbs * KB];
                    int bytesRead;
                    do {
                        bytesRead = in.read(dataBuffer, 0, kbs * KB);
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                        if (bytesRead >= kbs * KB) {
                            Thread.sleep(SECOND);
                        }
                    } while (bytesRead != -1);
                }
                long end = System.currentTimeMillis();
                System.out.printf("Time: %.2f", ((double) (end - start)) / 1000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    public static class FileDownload2 implements Runnable {

        private final String[] args;

        public FileDownload2(String[] args) {
            this.args = args;
        }

        @Override
        public void run() {
            try {
                String file = args[0];
                int kbs = Integer.parseInt(args[1]);
                URL url = new URL(file);
                long start = System.currentTimeMillis();
                try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
                    byte[] dataBuffer = new byte[kbs * KB];
                    int bytesRead;
                    do {
                        long startLoading = System.currentTimeMillis();
                        bytesRead = in.read(dataBuffer, 0, kbs * KB);
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                        long endLoading = System.currentTimeMillis();
                        long duration = endLoading - startLoading;
                        if (duration < SECOND) {
                            Thread.sleep(SECOND - duration);
                        }
                    } while (bytesRead != -1);
                }
                long end = System.currentTimeMillis();
                System.out.printf("Time: %.2f", ((double) (end - start)) / 1000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

}
