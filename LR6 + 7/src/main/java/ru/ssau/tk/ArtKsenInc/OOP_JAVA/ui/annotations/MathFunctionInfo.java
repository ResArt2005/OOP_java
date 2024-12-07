package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MathFunctionInfo {
    String name();  // Локализованное имя функции
    int priority(); // Приоритет для отображения в списке
}
