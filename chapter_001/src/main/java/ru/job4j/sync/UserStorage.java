package ru.job4j.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) != user;
    }

    public synchronized boolean update(User user) {
        User old = users.get(user.getId());
        if (old == null) {
            return false;
        }
        old.setAmount(user.getAmount());
        return true;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    public synchronized User findById(int id) {
        return users.get(id);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User source = users.get(fromId);
        User target = users.get(toId);
        if (source == null || target == null || source.getAmount() < amount) {
            return false;
        }
        source.setAmount(source.getAmount() - amount);
        target.setAmount(target.getAmount() + amount);
        return true;
    }

}
