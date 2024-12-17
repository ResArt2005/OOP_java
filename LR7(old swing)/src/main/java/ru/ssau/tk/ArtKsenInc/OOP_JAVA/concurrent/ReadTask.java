package ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;

public class ReadTask implements Runnable {
    private final TabulatedFunction tabulatedFunction;
    public ReadTask(TabulatedFunction tabulatedFunction){
        this.tabulatedFunction = tabulatedFunction;
    }
    @Override
    public void run() {
        for (int i = 0; i < tabulatedFunction.getCount(); ++i) {
            synchronized (tabulatedFunction) {
                double x = tabulatedFunction.getX(i);
                double y = tabulatedFunction.getY(i);
                System.out.printf("After read: i = %d, x = %f, y = %f%n", i, x, y);
            }
        }
    }
}
