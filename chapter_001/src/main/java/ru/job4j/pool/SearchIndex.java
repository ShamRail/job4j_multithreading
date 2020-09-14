package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchIndex {

    public static <T> int indexOf(T[] array, T key) {
        return indexOf(array, key, 0, array.length - 1);
    }

    private static <T> int indexOf(T[] array, T key, int from, int to) {
        if (to - from + 1 <= 10) {
            return linearSearch(array, key, from, to);
        }
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new SearchTask<>(from, to, array, key));
    }

    private static <T> int linearSearch(T[] array, T key, int from, int to) {
        int index = -1;
        for (int i = from; i <= to; i++) {
            if (Objects.equals(key, array[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    private static class SearchTask<T> extends RecursiveTask<Integer> {

        private final int start;
        private final int end;
        private final T[] array;
        private final T key;

        public SearchTask(int start, int end, T[] array, T key) {
            this.start = start;
            this.end = end;
            this.array = array;
            this.key = key;
        }

        @Override
        protected Integer compute() {
            if (end - start  + 1 <= 10) {
                return linearSearch(array, key, start, end);
            }
            int mid = (start + end) / 2;
            SearchTask<T> left = new SearchTask<>(start, mid, array, key);
            SearchTask<T> right = new SearchTask<>(mid + 1, end, array, key);
            left.fork();
            right.fork();
            return Math.max(left.join(), right.join());
        }
    }


}
