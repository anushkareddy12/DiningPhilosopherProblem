package  exampleproblems;

public class AltDiningPhilosopher {
    private static final Object lock = new Object();
    private static int currentPhilosopher = 1;

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];
        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(i + 1);
            threads[i] = new Thread(philosophers[i], "Philosopher " + (i + 1));
            threads[i].start();
        }

        for (int i = 0; i < 5; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Philosopher implements Runnable {
        private int id;

        Philosopher(int id) {
            this.id = id;
        }

        public void run() {
            synchronized (lock) {
                while (id != currentPhilosopher) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                think();
                leftFork();
                rightFork();
                eat();
                leftForkDown();
                rightForkDown();

                currentPhilosopher++;
                lock.notifyAll();
            }
        }

        public void think() {
            System.out.println(Thread.currentThread().getName() + " is Thinking");
        }

        public void eat() {
            System.out.println(Thread.currentThread().getName() + " is eating");
        }

        public void leftFork() {
            System.out.println(Thread.currentThread().getName() + " is picking up left fork");
        }

        public void rightFork() {
            System.out.println(Thread.currentThread().getName() + " is picking up right fork");
        }

        public void leftForkDown() {
            System.out.println(Thread.currentThread().getName() + " is putting down left fork");
        }

        public void rightForkDown() {
            System.out.println(Thread.currentThread().getName() + " is putting down right fork");
        }
    }
}
