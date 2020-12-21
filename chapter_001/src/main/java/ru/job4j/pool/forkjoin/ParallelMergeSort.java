package ru.job4j.pool.forkjoin;

import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelMergeSort<T> extends RecursiveTask<T[]> {

    private final T[] array;
    private final int from;
    private final int to;
    private Comparator<T> comparator;

    public ParallelMergeSort(T[] array, int from, int to, Comparator<T> comparator) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.comparator = comparator;
    }

    @Override
    protected T[] compute() {
        if (from == to) {
            return (T[]) new Object[] {array[from]};
        }
        int mid = (from + to) / 2;
        // создаем задачи для сортировки частей
        ParallelMergeSort<T> leftSort = new ParallelMergeSort(array, from, mid, comparator);
        ParallelMergeSort<T> rightSort = new ParallelMergeSort(array, mid + 1, to, comparator);
        // производим деление.
        // оно будет происходить, пока в частях не останется по одному элементу
        leftSort.fork();
        rightSort.fork();
        // объединяем полученные результаты
        T[] left = leftSort.join();
        T[] right = rightSort.join();
        return MergeSort.merge(left, right, comparator);
    }

    public static <T> T[] sort(T[] array, Comparator<T> comparator) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return (T[]) forkJoinPool.invoke(
                new ParallelMergeSort(array, 0, array.length - 1, comparator)
        );
    }


}
