package ru.job4j.switcher;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class MasterSlaveBarrier {

    @GuardedBy("monitor")
    private boolean isMaster = true;

    private final Object monitor = this;

    public void tryMaster() {
        synchronized (monitor) {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    if (isMaster) {
                        doneMaster();
                        isMaster = false;
                        notify();
                    } else {
                        notify();
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void trySlave() {
        synchronized (monitor) {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    if (isMaster) {
                        notify();
                        wait();
                    } else {
                        doneSlave();
                        isMaster = true;
                        notify();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void doneMaster() {
        System.out.println("A");
    }

    public void doneSlave() {
        System.out.println("B");
    }

    public static void main(String[] args) throws InterruptedException {
        MasterSlaveBarrier barrier = new MasterSlaveBarrier();
        Thread master = new Thread(barrier::tryMaster);
        Thread slave = new Thread(barrier::trySlave);
        master.start();
        slave.start();
        master.join();
        slave.join();
    }

}
