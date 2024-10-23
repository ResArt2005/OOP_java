package ru.ssau.tk.ArtKsenInc.OOP_JAVA.io;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.ArrayTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.SqrFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedDifferentialOperator;

import java.io.*;

public class ArrayTabulatedFunctionSerialization {
    public static void main(String[] args) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("output/serialized_array_functions.bin"));
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            ArrayTabulatedFunction originalFunction = new ArrayTabulatedFunction(new SqrFunction(), 0, 10, 5);
            TabulatedFunction firstDerivative = new TabulatedDifferentialOperator().derive(originalFunction);
            TabulatedFunction secondDerivative = new TabulatedDifferentialOperator().derive(firstDerivative);

            oos.writeObject(originalFunction);
            oos.writeObject(firstDerivative);
            oos.writeObject(secondDerivative);

            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("output/serialized_array_functions.bin"));
             ObjectInputStream ois = new ObjectInputStream(bis)) {

            TabulatedFunction deserializedOriginal = (TabulatedFunction) ois.readObject();
            TabulatedFunction deserializedFirstDerivative = (TabulatedFunction) ois.readObject();
            TabulatedFunction deserializedSecondDerivative = (TabulatedFunction) ois.readObject();

            System.out.println(deserializedOriginal);
            System.out.println(deserializedFirstDerivative);
            System.out.println(deserializedSecondDerivative);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
