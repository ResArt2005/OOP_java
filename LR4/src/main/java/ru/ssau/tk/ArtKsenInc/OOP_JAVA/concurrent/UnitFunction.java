package ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.Point;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;

import java.util.Iterator;

public class UnitFunction implements TabulatedFunction {
    @Override
    public double getY(double x) {
        return 1.0;
    }

    @Override
    public int getCount() {
        return 1000; // Например, 1000 точек
    }

    @Override
    public double getX(int index) {
        return index + 1; // Пример: x = 1, 2, ..., 1000
    }

    @Override
    public void setY(int index, double value) {
    }

    @Override
    public int indexOfX(double x) {
        return 0;
    }

    @Override
    public int indexOfY(double y) {
        return 0;
    }

    @Override
    public double leftBound() {
        return 0;
    }

    @Override
    public double rightBound() {
        return 0;
    }

    @Override
    public double getY(int index) {
        return 1.0; // Всегда возвращает 1
    }

    @Override
    public Iterator<Point> iterator() {
        return null;
    }

    @Override
    public double apply(double x) {
        return 0;
    }
}
