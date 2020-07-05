package ru.job4j.common;

import net.jcip.annotations.Immutable;

@Immutable
public class Node<T> {

    private final Node next;

    private final T value;

    public Node(Node next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }

}