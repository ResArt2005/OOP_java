package ru.ssau.tk.ArtKsenInc.OOP_JAVA.io;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.Point;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;


import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

final public class FunctionsIO {
    private FunctionsIO(){
        throw new UnsupportedOperationException();
    }
    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function){
         // Создаем PrintWriter, оборачивая переданный BufferedWriter
        PrintWriter printWriter = new PrintWriter(writer);
        // Получаем количество точек в функции
        int count = function.getCount();
        // Записываем количество точек в первой строке
        printWriter.println(count);
        // Перебираем точки функции и записываем каждую из них
        for (Point point : function) {  // Предполагается, что TabulatedFunction реализует Iterable<Point>
            double x = point.x; // Получаем x
            double y = point.y; // Получаем y
            printWriter.printf("%f %f%n", x, y); // Записываем в формате "x y"
        }
        // Пробрасываем данные из буфера
        printWriter.flush();
    }
    public static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(function.getCount());
        for(Point point: function){
            dataOutputStream.writeDouble(point.x);
            dataOutputStream.writeDouble(point.y);
        }
        dataOutputStream.flush();
    }
    public static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        try {
            String firstLine = reader.readLine();
            int count = Integer.parseInt(firstLine);
            double[] xValues = new double[count];
            double[] yValues = new double[count];
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.forLanguageTag("ru"));
            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                String[] parts = line.split(" ");
                try {
                    xValues[i] = numberFormatter.parse(parts[0]).doubleValue();
                    yValues[i] = numberFormatter.parse(parts[1]).doubleValue();
                } catch (ParseException e) {
                    throw new IOException();
                }
            }
            return factory.create(xValues, yValues);
        } catch (IOException exception) {
            throw exception;
        } catch (NumberFormatException e) {
            throw new IOException();
        }
    }
    public static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        DataInputStream stream = new DataInputStream(inputStream);
        int length = stream.readInt();
        double[] xValues = new double[length];
        double[] yValues = new double[length];
        for (int i = 0; i < length; ++i) {
            xValues[i] = stream.readDouble();
            yValues[i] = stream.readDouble();
        }
        return factory.create(xValues, yValues);
    }
    public static void serialize(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
        objectOutputStream.writeObject(function);
        objectOutputStream.flush();
    }
}
