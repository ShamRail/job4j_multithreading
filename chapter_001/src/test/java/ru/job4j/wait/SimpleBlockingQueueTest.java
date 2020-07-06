package ru.job4j.wait;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(() -> queue.offer(1));
        Thread consumer = new Thread(() -> queue.poll());
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        Assert.assertEquals(0, queue.size());
    }

}