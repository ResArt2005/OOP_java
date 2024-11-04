package ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.ConstantFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;

public class ReadWriteTaskExecutor {
    public static void main(String[] args) {
        LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(new ConstantFunction(-1), 1, 1000, 1000);
        Thread readStream = new Thread(new ReadTask(list));
        Thread writeStream = new Thread(new WriteTask(list, 0.5));
        readStream.start();
        writeStream.start();
    }
}
