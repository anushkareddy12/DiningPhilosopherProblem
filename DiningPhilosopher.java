package exampleproblems;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosopher {

    public static void main(String[] args) throws InterruptedException {
        int numberOfPhilosophers = 5;
        Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
        Lock[] forks = new ReentrantLock[numberOfPhilosophers];

        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks[i] = new ReentrantLock();
        }

        for (int i = 0; i < numberOfPhilosophers; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % numberOfPhilosophers]);
            new Thread(philosophers[i], "Philosopher " + (i + 1)).start();
        }

        // Run the simulation for a fixed amount of time, e.g., 10 seconds
        Thread.sleep(100);

        // Stop all philosophers
        for (Philosopher philosopher : philosophers) {
            philosopher.stopPhilosopher();
        }
    }
}

class Philosopher implements Runnable {
    private final int id;
    private final Lock leftFork;
    private final Lock rightFork;
    private boolean running = true;

    public Philosopher(int id, Lock leftFork, Lock rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        try {
                think();
                pickUpForks();
                eat();
                putDownForks();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stopPhilosopher() {
        running = false;
    }

    private void think() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " is Thinking");
        Thread.sleep((int) (Math.random() * 100));
    }

    private void pickUpForks() {
        leftFork.lock();
        System.out.println(Thread.currentThread().getName() + " picked up left fork");
        rightFork.lock();
        System.out.println(Thread.currentThread().getName() + " picked up right fork");
    }

    private void eat() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " is Eating");
        Thread.sleep((int) (Math.random() * 100));
    }

    private void putDownForks() {
        leftFork.unlock();
        System.out.println(Thread.currentThread().getName() + " put down left fork");
        rightFork.unlock();
        System.out.println(Thread.currentThread().getName() + " put down right fork");
    }
}
