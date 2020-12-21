package ru.job4j.pool.forkjoin;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;

public class ParallelMergeSortTest {

    @Test
    public void whenTestWith3Elements() {
        Integer[] array = {3, 2, 1};
        Object[] result = ParallelMergeSort.sort(array, Comparator.naturalOrder());
        Integer[] expected = {1, 2, 3};
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void whenTestWith5Elements() {
        Integer[] array = {5, 4, 3, 2, 1};
        Object[] result = ParallelMergeSort.sort(array, Comparator.naturalOrder());
        Integer[] expected = {1, 2, 3, 4, 5};
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void whenTestWith100Elements() {
        int seed = 123;
        Random random = new Random(seed);
        Integer[] array = IntStream.rangeClosed(1, 100)
                .map(i -> random.nextInt(100_000))
                .boxed()
                .toArray(value -> new Integer[100]);
        Integer[] expected = Arrays.stream(array)
                .sorted(Comparator.naturalOrder())
                .toArray(value -> new Integer[100]);
        Object[] result = ParallelMergeSort.sort(array, Comparator.naturalOrder());
        Assert.assertArrayEquals(expected, result);
    }

}