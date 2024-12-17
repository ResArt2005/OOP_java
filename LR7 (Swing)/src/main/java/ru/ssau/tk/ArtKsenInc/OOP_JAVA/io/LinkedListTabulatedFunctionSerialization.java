package ru.ssau.tk.ArtKsenInc.OOP_JAVA.io;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.AInDegreeXFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedDifferentialOperator;

import java.io.*;

public class LinkedListTabulatedFunctionSerialization {

    public static void main(String[] args) {
        String path = "output/serialized linked list functions.bin";
        LinkedListTabulatedFunction originalFunction = new LinkedListTabulatedFunction(new AInDegreeXFunction(1, 2), 0, 10, 5);
        TabulatedFunction firstDerivative = new TabulatedDifferentialOperator().derive(originalFunction);
        TabulatedFunction secondDerivative = new TabulatedDifferentialOperator().derive(firstDerivative);
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
            FunctionsIO.serialize(bufferedOutputStream, originalFunction);
            FunctionsIO.serialize(bufferedOutputStream, firstDerivative);
            FunctionsIO.serialize(bufferedOutputStream, secondDerivative);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileInputStream fileInputStream = new FileInputStream(path);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
            TabulatedFunction deserializedOriginalFunction = FunctionsIO.deserialize(bufferedInputStream);
            TabulatedFunction deserializedFirstDerivative = FunctionsIO.deserialize(bufferedInputStream);
            TabulatedFunction deserializedSecondDerivative = FunctionsIO.deserialize(bufferedInputStream);
            System.out.println(deserializedOriginalFunction.toString());
            System.out.println(deserializedFirstDerivative.toString());
            System.out.println(deserializedSecondDerivative.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

