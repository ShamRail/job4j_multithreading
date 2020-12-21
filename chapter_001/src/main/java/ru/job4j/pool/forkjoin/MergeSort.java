package ru.job4j.pool.forkjoin;

import java.util.Comparator;

public class MergeSort {

    public static <T> T[] sort(T[] array, Comparator<T> comparator) {
        return sort(array, 0, array.length - 1, comparator);
    }

    private static <T> T[] sort(T[] array, int from, int to, Comparator<T> comparator) {
        // при следующем условии, массив из одного элемента
        // делить нечего, возвращаем элемент
        if (from == to) {
            return (T[]) (new Object[] {array[from]});
        }
        // попали сюда, значит в массиве более одного элемента
        // находим середину
        int mid = (from + to) / 2;
        // объединяем отсортированные части
        return merge(
                // сортируем левую часть
                sort(array, from, mid, comparator),
                // сортируем правую часть
                sort(array, mid + 1, to, comparator),
                comparator
        );
    }

    // Метод объединения двух отсортированных массивов
    public static <T> T[] merge(T[] left, T[] right, Comparator<T> comparator) {
        int li = 0;
        int ri = 0;
        int resI = 0;
        T[] result = (T[]) new Object[left.length + right.length];
        while (resI != result.length) {
            if (li == left.length) {
                result[resI++] = right[ri++];
            } else if (ri == right.length) {
                result[resI++] = left[li++];
            } else if (comparator.compare(left[li], right[ri]) < 0) {
                result[resI++] = left[li++];
            } else {
                result[resI++] = right[ri++];
            }
        }
        return result;
    }

}
