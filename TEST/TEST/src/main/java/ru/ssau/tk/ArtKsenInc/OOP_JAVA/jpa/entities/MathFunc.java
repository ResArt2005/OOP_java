package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.CompositeFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;

@Entity
@Getter
@Setter
@Table(name = "MathFunc")
public class MathFunc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String function; // Храните функцию как строку (например, JSON)

    // Метод для представления функции в виде класса MathFunction
    public MathFunction getFunction() {
        return parseFunction(function);
    }

    public void setFunction(MathFunction function) {
        this.function = toStringFunction(function);
    }

    public MathFunc() {
    }

    public MathFunc(String name, MathFunction function) {
        this.name = name;
        this.function = toStringFunction(function);
    }

    // Метод для представления мат функции в виде строки
    private String toStringFunction(MathFunction function) {
        if (function instanceof CompositeFunction composite) {
            return "CompositeFunction(" + toStringFunction(composite.getFirstFunction()) + "," + toStringFunction(composite.getSecondFunction()) + ")";
        } else {
            return function.getClass().getSimpleName();  // Сохраняем имя класса для простых функций
        }
    }

    // Рекурсивный метод для парсинга функции из строки
    private MathFunction parseFunction(String functionString) {
        if (functionString.startsWith("CompositeFunction")) {
            // Разбираем составную функцию CompositeFunction
            String innerFunctions = functionString.substring(functionString.indexOf('(') + 1, functionString.lastIndexOf(')'));
            String[] parts = splitCompositeArguments(innerFunctions);
            MathFunction firstFunction = parseFunction(parts[0]);
            MathFunction secondFunction = parseFunction(parts[1]);
            return new CompositeFunction(firstFunction, secondFunction);
        } else {
            // Попробуем создать объект класса функции через рефлексию
            try {
                // Нужно указать полный путь к классу
                String fullClassName = "ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions." + functionString.trim();
                Class<?> clazz = Class.forName(fullClassName);
                return (MathFunction) clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("Не удалось создать функцию: " + functionString, e);
            }
        }
    }


    // Метод для корректного разделения аргументов внутри CompositeFunction (учитывает вложенность)
    private String[] splitCompositeArguments(String innerFunctions) {
        int balance = 0;
        int splitIndex = -1;

        // Найдем запятую, которая разделяет первую и вторую функции
        for (int i = 0; i < innerFunctions.length(); i++) {
            char c = innerFunctions.charAt(i);
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
            } else if (c == ',' && balance == 0) {
                splitIndex = i;
                break;
            }
        }

        if (splitIndex == -1) {
            throw new IllegalArgumentException("Неверный формат составной функции: " + innerFunctions);
        }

        String firstPart = innerFunctions.substring(0, splitIndex).trim();
        String secondPart = innerFunctions.substring(splitIndex + 1).trim();
        return new String[]{firstPart, secondPart};
    }
}