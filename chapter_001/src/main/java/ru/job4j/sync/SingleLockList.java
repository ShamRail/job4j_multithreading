package ru.job4j.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final SimpleArray<T> array = new SimpleArray<>();

    public synchronized void add(T model) {
        array.add(model);
    }

    public synchronized void set(int index, T model) {
        array.set(index, model);
    }

    public synchronized T remove(int index) {
        return array.remove(index);
    }

    public synchronized T get(int index) {
        return array.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy().iterator();
    }

    private SimpleArray<T> copy() {
        SimpleArray<T> copied = new SimpleArray<>();
        for (T value : array) {
            copied.add(value);
        }
        return copied;
    }

}
