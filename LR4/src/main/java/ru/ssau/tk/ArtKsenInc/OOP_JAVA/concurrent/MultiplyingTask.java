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
        // Логика выполнения задачи
        for (int i = 0; i < tabulatedFunction.getCount(); i++) {
            // Пример: умножение значения на 2
            double newValue = tabulatedFunction.getY(i) * 2;
            tabulatedFunction.setY(i, newValue);
        }
        completed = true; // Задача завершена
    }

    public boolean isCompleted() {
        return completed;
    }
}
