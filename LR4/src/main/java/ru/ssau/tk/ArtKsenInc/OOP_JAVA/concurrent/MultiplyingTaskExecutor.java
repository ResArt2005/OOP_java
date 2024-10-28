package ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;

import java.util.ArrayList;
import java.util.List;

public class MultiplyingTaskExecutor {
    public static void main(String[] args) {
        // Создание массивов x и y для табулированной функции
        int count = 1000; // Количество точек
        double[] xValues = new double[count];
        double[] yValues = new double[count];

        for (int i = 0; i < count; i++) {
            xValues[i] = i + 1; // Пример: x = 1, 2, ..., 1000
            yValues[i] = 1.0; // Значения y всегда равны 1
        }

        // Создание табулированной функции
        LinkedListTabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(xValues, yValues);

        // Список потоков
        List<Thread> threads = new ArrayList<>();

        // Создание задач и потоков
        for (int i = 0; i < 10; i++) {
            MultiplyingTask task = new MultiplyingTask(tabulatedFunction);
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start(); // Запускаем поток сразу после его создания
        }

        // Ожидание завершения всех потоков
        for (Thread thread : threads) {
            try {
                thread.join(); // Дождаться завершения потока
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Восстановить статус прерывания
            }
        }

        // Вывод результата
        System.out.println("Результат табулированной функции:");
        for (int i = 0; i < tabulatedFunction.getCount(); i++) {
            System.out.printf("x: %.2f, y: %.2f%n", tabulatedFunction.getX(i), tabulatedFunction.getY(i));
        }
    }
}
