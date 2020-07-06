package ru.job4j.sync;

import java.util.*;

public class SimpleArray<T> implements Iterable<T> {

    private Object[] container = new Object[100];

    private int position;

    public void add(T model) {
        if (position == container.length) {
            container = Arrays.copyOf(container, container.length * 2);
        }
        container[position++] = model;
    }

    public void set(int index, T model) {
        Objects.checkIndex(index, position);
        container[index] = model;
    }

    public T get(int index) {
        Objects.checkIndex(index, position);
        return (T) container[index];
    }

    public T remove(int index) {
        Objects.checkIndex(index, position);
        T value = (T) container[index];
        System.arraycopy(container, index + 1, container, index, container.length - index - 1);
        container[container.length - 1] = null;
        position--;
        return value;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            private int index;

            @Override
            public boolean hasNext() {
                return index < position;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) container[index++];
            }
        };
    }
}

