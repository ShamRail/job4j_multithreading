package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownload {

    private static final int KB = 1024;

    private static final int SECOND = 1000;

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }
        // "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml"
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.printf("Time: %.2f", ((double) (end - start)) / 1000);
    }
}
