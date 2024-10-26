package ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;

public class WriteTask implements Runnable{
    private final TabulatedFunction tabulatedFunction;
    private final double value;
    public WriteTask(TabulatedFunction tabulatedFunction, double value){
        this.tabulatedFunction = tabulatedFunction;
        this.value = value;
    }
    @Override
    public void run() {
        for (int i = 0; i < tabulatedFunction.getCount(); ++i) {
            tabulatedFunction.setY(i, value);
            System.out.printf("Writing for index %d complete", i);
        }
    }
}
