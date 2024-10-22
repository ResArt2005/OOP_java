package ru.ssau.tk.ArtKsenInc.OOP_JAVA.io;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.Point;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import java.io.BufferedWriter;
import java.io.PrintWriter;

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
}
