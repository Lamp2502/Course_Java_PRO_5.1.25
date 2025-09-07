/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2023 VTB Group. All rights reserved.
 */

package ru.cource.inno.java_pro.hw3_threads_pool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Реализация пула потоков по условиям:
 *  - очередь задач: LinkedList
 *  - исполнители стартуют сразу в конструкторе
 *  - execute(Runnable): кладёт задачу в хвост и будит одного работника
 *  - shutdown(): запрещает приём новых задач, работники завершаются после опустошения очереди
 *  - awaitTermination(): ждёт завершения всех работников
 */
public final class ThreadPool {

    // Очередь задач по условию — LinkedList.
    private final LinkedList<Runnable> tasks = new LinkedList<>();

    // Блокировка и условие «очередь не пуста или пришёл shutdown».
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmptyOrStopping = lock.newCondition();

    // Рабочие потоки.
    private final List<Thread> workers = new ArrayList<>();

    // Флаг запрета на приём новых задач.
    private volatile boolean stopping = false;

    /**
     * Создаёт пул и сразу поднимает указанное число рабочих потоков.
     */
    public ThreadPool(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be > 0");
        }
        for (int i = 1; i <= capacity; i++) {
            Thread t = new Thread(new Worker(), "Morph-w-" + i);
            // Явно не daemon — процесс жив, пока живы воркеры.
            t.setDaemon(false);
            workers.add(t);
            t.start();
        }
    }

    /**
     * Отправляет задачу в очередь исполнения.
     * @throws IllegalStateException если пул уже закрыт (после shutdown()).
     */
    public void execute(Runnable job) {
        Objects.requireNonNull(job, "job");
        lock.lock();
        try {
            if (stopping) {
                throw new IllegalStateException("Pool is shutdown; rejecting new tasks");
            }
            tasks.addLast(job);
            // Будим одного — достаточно, чтобы кто-то подхватил свежую работу.
            notEmptyOrStopping.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Запрещает приём новых задач и инициирует мягкое завершение рабочих потоков,
     * когда очередь станет пустой.
     */
    public void shutdown() {
        lock.lock();
        try {
            if (!stopping) {
                stopping = true;
                // Разбудим всех ожидающих, чтобы они увидели флаг и завершились при пустой очереди.
                notEmptyOrStopping.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Блокируется, пока все рабочие потоки не завершатся.
     */
    public void awaitTermination() throws InterruptedException {
        for (Thread t : workers) {
            // join безопасен, если поток уже умер.
            t.join();
        }
    }

    /** Дополнительно: текущий размер очереди (диагностика). */
    public int queueSize() {
        lock.lock();
        try {
            return tasks.size();
        } finally {
            lock.unlock();
        }
    }

    /** Признак, что shutdown уже был вызван. */
    public boolean isShutdown() {
        return stopping;
    }

    /** Количество рабочих потоков. */
    public int capacity() {
        return workers.size();
    }

    /**
     * Логика работника: ждать задачи; если задач нет и стоим на shutdown — выйти.
     */
    private final class Worker implements Runnable {
        @Override
        public void run() {
            for (;;) {
                Runnable next;
                lock.lock();
                try {
                    // Ждём до тех пор, пока нет задач и (ещё) не пора умирать.
                    while (tasks.isEmpty() && !stopping) {
                        try {
                            notEmptyOrStopping.await();
                        } catch (InterruptedException ie) {
                            // Превращаем прерывание в повторную проверку условий цикла.
                            // Ничего не теряем: выйдем естественно на stopping и пустой очереди.
                        }
                    }
                    // Если задач нет и уже остановка — выходим.
                    if (tasks.isEmpty() && stopping) {
                        return;
                    }
                    // Забираем следующую задачу.
                    next = tasks.removeFirst();
                } finally {
                    lock.unlock();
                }

                // Выполняем за пределами критической секции.
                try {
                    next.run();
                } catch (Throwable t) {
                    // Не даём работнику умереть из-за исключения конкретной задачи.
                    System.err.println(Thread.currentThread().getName()
                        + " caught exception: " + t);
                    t.printStackTrace(System.err);
                }
            }
        }
    }

    // --- демо ---
    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(3);

        for (int i = 1; i <= 8; i++) {
            final int id = i;
            pool.execute(() -> {
                String tn = Thread.currentThread().getName();
                System.out.printf("[%s] job %d start%n", tn, id);
                try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                System.out.printf("[%s] job %d done%n", tn, id);
            });
        }

        pool.shutdown();
        pool.awaitTermination();
        System.out.println("queue size after shutdown = " + pool.queueSize());

        try {
            pool.execute(() -> System.out.println("should not be accepted"));
        } catch (IllegalStateException expected) {
            System.out.println("Rejected as expected: " + expected.getMessage());
        }
    }
}


