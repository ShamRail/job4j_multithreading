package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format("subject = Notification {%s} to email {%s}", user.getName(), user.getEmail());
            String body = String.format("Add a new event to {%s}", user.getName());
            send(subject, body, user.getEmail());
        });
    }

    public void close() {
        this.pool.shutdown();
    }

    public void send(String subject, String body, String email) {

    }
}