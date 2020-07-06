package ru.job4j.sync;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.IsNull.nullValue;

public class UserStorageTest {

    @Test
    public void whenAdd() {
        UserStorage storage = new UserStorage();
        User in = new User(1, 100);
        storage.add(in);
        User out = storage.findById(in.getId());
        Assert.assertThat(out, Is.is(in));
    }

    @Test
    public void whenDelete() {
        UserStorage storage = new UserStorage();
        User in = new User(1, 100);
        storage.add(in);
        storage.delete(in);
        User out = storage.findById(in.getId());
        Assert.assertThat(out, Is.is(nullValue()));
    }

    @Test
    public void whenUpdate() {
        UserStorage storage = new UserStorage();
        User in = new User(1, 100);
        storage.add(in);
        User updated = new User(1, 200);
        storage.update(updated);
        User out = storage.findById(in.getId());
        Assert.assertThat(out, Is.is(updated));
    }

    @Test
    public void whenTransfer() {
        UserStorage storage = new UserStorage();
        User from = new User(1, 100);
        User to = new User(2, 100);
        storage.add(from);
        storage.add(to);
        storage.transfer(from.getId(), to.getId(), 50);
        Assert.assertEquals(50, from.getAmount());
        Assert.assertEquals(150, to.getAmount());
    }

}