package ru.ssau.tk.ArtKsenInc.OOP_JAVA.io;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.ArrayTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.SqrFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;

public class TabulatedFunctionFileOutputStream {
    public static void main(String[] args){
        String arrayFunctionFilePath = "output/array function.bin";
        String linkedListFunctionFilePath = "output/linked list function.bin";
        try (
                BufferedOutputStream arrayFunctionWriter = new BufferedOutputStream(new FileOutputStream(arrayFunctionFilePath));
                BufferedOutputStream linkedListFunctionWriter = new BufferedOutputStream(new FileOutputStream(linkedListFunctionFilePath))
        ) {
            TabulatedFunction arrayFunction = new ArrayTabulatedFunction(new SqrFunction(), 0, 10, 5);
            TabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(new double[]{1,2,3}, new double[]{1, 4, 9});
            FunctionsIO.writeTabulatedFunction(arrayFunctionWriter, arrayFunction);
            FunctionsIO.writeTabulatedFunction(linkedListFunctionWriter, linkedListFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
