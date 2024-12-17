package ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;

public class MultiplyingTask implements Runnable {
    private final TabulatedFunction tabulatedFunction;
    private volatile boolean completed = false; // Статус выполнения задачи

    public MultiplyingTask(TabulatedFunction tabulatedFunction) {
        this.tabulatedFunction = tabulatedFunction;
    }

    @Override
    public void run() {
        synchronized (tabulatedFunction) {
            for (int i = 0; i < tabulatedFunction.getCount(); i++) {
                double newValue = tabulatedFunction.getY(i) * 2;
                tabulatedFunction.setY(i, newValue);
            }
            completed = true; // Устанавливаем статус выполнения
            System.out.println(Thread.currentThread().getName() + " completed task");
        }
    }

    public boolean isCompleted() {
        return completed;
    }
}
