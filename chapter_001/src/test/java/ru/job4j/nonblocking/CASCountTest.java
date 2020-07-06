package ru.job4j.nonblocking;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

public class CASCountTest {

    @Test
    public void test() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(() -> IntStream.rangeClosed(1, 30).forEach(n -> casCount.increment()));
        Thread thread2 = new Thread(() -> IntStream.rangeClosed(1, 30).forEach(n -> casCount.increment()));
        Thread thread3 = new Thread(() -> IntStream.rangeClosed(1, 30).forEach(n -> casCount.increment()));
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        Assert.assertEquals(90, casCount.get());
    }

}