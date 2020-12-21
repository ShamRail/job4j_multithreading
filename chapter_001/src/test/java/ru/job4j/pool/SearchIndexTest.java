package ru.job4j.pool;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SearchIndexTest {

    @Test
    public void whenNotParallelAndIndexNotExists() {
        assertEquals(-1, SearchIndex.indexOf(new Integer[] {1, 2, 3}, 4));
    }

    @Test
    public void whenNotParallelAndIndexExists() {
        assertEquals(1, SearchIndex.indexOf(new Integer[] {1, 2, 3}, 2));
    }

    @Test
    public void whenParallelAndIndexNotExists() {
        assertEquals(-1,
                SearchIndex.indexOf(
                        new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, 20)
        );
    }

    @Test
    public void whenParallelAndIndexExists() {
        assertEquals(9,
                SearchIndex.indexOf(
                        new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, 10)
        );
    }

}