package ru.job4j.nonblocking;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;

public class BaseCacheTest {

    @Test
    public void test() throws InterruptedException {
        AtomicReference<Exception> ex = new AtomicReference<>();
        BaseCache baseCache = new BaseCache();
        Base base = new Base(1, "name");
        baseCache.add(base);
        Thread thread1 = new Thread(
                () -> {
                    try {
                        baseCache.update(new Base(1, "one"));
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    try {
                        baseCache.update(new Base(1, "second"));
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assert.assertThat(ex.get().getMessage(), is("Invalid version"));
    }

}