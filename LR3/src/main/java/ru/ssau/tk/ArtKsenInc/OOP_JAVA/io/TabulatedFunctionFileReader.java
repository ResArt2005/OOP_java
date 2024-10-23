package ru.ssau.tk.ArtKsenInc.OOP_JAVA.io;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.ArrayTabulatedFunctionFactory;

import java.io.*;

public class TabulatedFunctionFileReader {
    public static void main(String[] args){
        String path = "input/function.txt";
        try (
                FileReader arrayFunctionReader = new FileReader(path);
                FileReader linkedListFunctionReader = new FileReader(path);
                BufferedReader bufferedArrayFunctionReader = new BufferedReader(arrayFunctionReader);
                BufferedReader bufferedLinkedListFunctionReader = new BufferedReader(linkedListFunctionReader);
        ) {
            TabulatedFunction arrayFunction = FunctionsIO.readTabulatedFunction(bufferedArrayFunctionReader, new ArrayTabulatedFunctionFactory());
            TabulatedFunction linkedListFunction = FunctionsIO.readTabulatedFunction(bufferedLinkedListFunctionReader, new ArrayTabulatedFunctionFactory());;
            System.out.println(arrayFunction);
            System.out.println(linkedListFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
