package ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;

public class MultiplyingTask implements Runnable {
    private final TabulatedFunction tabulatedFunction;
    private volatile boolean completed = false; // Поле для отслеживания состояния

    public MultiplyingTask(TabulatedFunction tabulatedFunction) {
        this.tabulatedFunction = tabulatedFunction;
    }

    @Override
    public void run() {
        for (int i = 0; i < tabulatedFunction.getCount(); ++i) {
            synchronized (tabulatedFunction) {
                double currentY = tabulatedFunction.getY(i);
                tabulatedFunction.setY(i, currentY * 2); // Увеличиваем значение y в 2 раза
            }
        }
        completed = true; // Устанавливаем флаг завершения
        System.out.printf("Поток %s закончил выполнение задачи.%n", Thread.currentThread().getName());
    }

    public boolean isCompleted() { // Метод для проверки завершенности
        return completed;
    }
}