package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations;

import org.reflections.Reflections;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.annotations.MathFunctionInfo;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.MathFunction;

import java.util.*;

public class MathFunctionScanner {
    public static Map<String, MathFunction> getAnnotatedFunctions() {
        Map<String, MathFunction> functionMap = new TreeMap<>();
        Reflections reflections = new Reflections("ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions");

        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(MathFunctionInfo.class);
        List<FunctionEntry> functionEntries = new ArrayList<>();

        for (Class<?> functionClass : annotatedClasses) {
            if (MathFunction.class.isAssignableFrom(functionClass)) {
                MathFunctionInfo annotation = functionClass.getAnnotation(MathFunctionInfo.class);
                try {
                    MathFunction functionInstance = (MathFunction) functionClass.getDeclaredConstructor().newInstance();
                    functionEntries.add(new FunctionEntry(annotation.name(), annotation.priority(), functionInstance));
                } catch (Exception e) {
                    e.printStackTrace(); // Логируем ошибку
                }
            }
        }

        // Сортировка по приоритету, а затем по алфавиту
        functionEntries.sort(Comparator.comparingInt(FunctionEntry::priority)
                .thenComparing(FunctionEntry::name));

        for (FunctionEntry entry : functionEntries) {
            functionMap.put(entry.name, entry.function);
        }

        return functionMap;
    }

    private static class FunctionEntry {
        String name;
        int priority;
        MathFunction function;

        public FunctionEntry(String name, int priority, MathFunction function) {
            this.name = name;
            this.priority = priority;
            this.function = function;
        }

        public String name() {
            return name;
        }

        public int priority() {
            return priority;
        }
    }
}
