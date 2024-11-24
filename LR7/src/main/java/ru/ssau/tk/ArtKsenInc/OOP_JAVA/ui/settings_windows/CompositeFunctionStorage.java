package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.settings_windows;

import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.CompositeFunction;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CompositeFunctionStorage {
    private static final String FILE_PATH = "src/main/resources/saved_tabulated_functions/composite_functions.dat";

    public static void saveCompositeFunction(String name, CompositeFunction function) throws IOException, ClassNotFoundException {
        Map<String, CompositeFunction> functionsMap = loadCompositeFunctions();
        functionsMap.put(name, function);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(functionsMap);
        }
    }

    public static Map<String, CompositeFunction> loadCompositeFunctions() throws IOException, ClassNotFoundException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new HashMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<String, CompositeFunction>) ois.readObject();
        }
    }
}
