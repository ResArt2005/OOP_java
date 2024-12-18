package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MathFunctionInfo {
    String name();  // Локализованное имя функции
    int priority(); // Приоритет для отображения в списке
}
