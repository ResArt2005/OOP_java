package ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.UnitFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

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
        ConcurrentLinkedQueue<MultiplyingTask> tasks = new ConcurrentLinkedQueue<>();

        // Создание задач и потоков
        for (int i = 0; i < 10; i++) {
            MultiplyingTask task = new MultiplyingTask(tabulatedFunction);
            tasks.add(task);
            Thread thread = new Thread(task);
            threads.add(thread);
        }

        // Запуск потоков
        for (Thread thread : threads) {
            thread.start();
        }

        // Проверка выполнения задач
        while (!tasks.isEmpty()) {
            tasks.removeIf(MultiplyingTask::isCompleted);
            // Можно добавить небольшую паузу для уменьшения загрузки процессора
            try {
                Thread.sleep(50); // Небольшая пауза для снижения нагрузки на CPU
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Вывод результата
        System.out.println("Результат табулированной функции:");
        for (int i = 0; i < tabulatedFunction.getCount(); i++) {
            System.out.printf("x: %.2f, y: %.2f%n", tabulatedFunction.getX(i), tabulatedFunction.getY(i));
        }
    }
}
