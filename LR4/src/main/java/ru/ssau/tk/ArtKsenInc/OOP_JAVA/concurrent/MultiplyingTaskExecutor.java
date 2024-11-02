package ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.UnitFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class MultiplyingTaskExecutor {
    public static void main(String[] args) {
        LinkedListTabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(new UnitFunction(), 1, 1000, 1000);
        List<MultiplyingTask> tasks = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MultiplyingTask task = new MultiplyingTask(tabulatedFunction);
            Thread thread = new Thread(task);
            tasks.add(task);
            threads.add(thread);
            thread.start();
        }
        while (!tasks.isEmpty()) {
            Iterator<MultiplyingTask> iterator = tasks.iterator();
            while (iterator.hasNext()) {
                MultiplyingTask task = iterator.next();
                if (task.isCompleted()) {
                    iterator.remove();
                }
            }
        }
        System.out.println(tabulatedFunction);
    }
}
