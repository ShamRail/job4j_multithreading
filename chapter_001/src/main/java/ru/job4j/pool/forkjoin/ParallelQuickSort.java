package ru.job4j.pool.forkjoin;

import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort {

    public static <T> void sort(T[] array, Comparator<T> comparator) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(new QJob<>(array, 0, array.length - 1, comparator));
    }

    private static class QJob<T> extends RecursiveAction {

        private T[] array;
        private int l;
        private int r;
        private Comparator<T> comparator;

        public QJob(T[] array, int l, int r, Comparator<T> comparator) {
            this.array = array;
            this.l = l;
            this.r = r;
            this.comparator = comparator;
        }

        @Override
        protected void compute() {
            if (l < r) {
                int p = partition(array, l, r);
                RecursiveAction left = new QJob<>(array, l, p, comparator);
                RecursiveAction right = new QJob<>(array, p + 1, r, comparator);
                left.fork();
                right.fork();
                left.join();
                right.join();
            }
        }

        private int partition(T[] subArray, int l, int r) {
            int i = l;
            int j = r;
            T pivot = subArray[(l + r) / 2];
            while (i <= j) {
                while (comparator.compare(subArray[i], pivot) < 0) {
                    i++;
                }
                while (comparator.compare(subArray[j], pivot) > 0) {
                    j--;
                }
                if (i >= j) {
                    break;
                }
                T t = subArray[i];
                subArray[i] = subArray[j];
                subArray[j] = t;
            }
            return j;
        }

    }

}
