package com.java.patterns;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ActorMessagePassingDemo {
    private ActorMessagePassingDemo() {
    }

    public static void main(String[] args) throws InterruptedException {
        CounterActor actor = new CounterActor(8);
        actor.start();
        try {
            actor.send(new Add(3));
            actor.send(new Add(4));
            actor.send(new Add(-1));
        } finally {
            actor.close();
        }
    }

    private interface Message {
    }

    private static final class Add implements Message {
        private final int amount;

        private Add(int amount) {
            this.amount = amount;
        }
    }

    private enum Stop implements Message {
        INSTANCE
    }

    private static final class CounterActor implements AutoCloseable {
        private final BlockingQueue<Message> mailbox;
        private final Thread worker;
        private boolean accepting = true;
        private boolean stopEnqueued;
        private int value;

        private CounterActor(int capacity) {
            mailbox = new ArrayBlockingQueue<Message>(capacity);
            worker = new Thread(this::processMessages, "counter-actor");
        }

        private void start() {
            worker.start();
        }

        private synchronized void send(Message message) throws InterruptedException {
            if (!accepting) {
                throw new IllegalStateException("Actor is stopping");
            }
            mailbox.put(message);
        }

        private void processMessages() {
            try {
                while (true) {
                    Message message = mailbox.take();
                    if (message == Stop.INSTANCE) {
                        return;
                    }
                    value += ((Add) message).amount;
                    System.out.println("Actor-owned counter: " + value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public void close() {
            boolean interrupted = false;
            synchronized (this) {
                if (!accepting) {
                    while (worker.isAlive()) {
                        try {
                            worker.join();
                        } catch (InterruptedException e) {
                            interrupted = true;
                        }
                    }
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                    }
                    return;
                }
                accepting = false;
                while (!stopEnqueued) {
                    try {
                        mailbox.put(Stop.INSTANCE);
                        stopEnqueued = true;
                    } catch (InterruptedException e) {
                        interrupted = true;
                    }
                }
            }
            while (worker.isAlive()) {
                try {
                    worker.join();
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
