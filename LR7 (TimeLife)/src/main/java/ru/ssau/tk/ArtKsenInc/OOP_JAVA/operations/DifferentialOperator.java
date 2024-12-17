package ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;

public interface DifferentialOperator<T extends MathFunction> {
    T derive(T function);
}