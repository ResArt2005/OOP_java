package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.exceptions.ArrayIsNotSortedException;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

@Controller
@SessionAttributes("fabricType") // Изменено на fabricType
public class TabulatedFunctionByArraysController {

    private final TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory(); // Инициализация по умолчанию
    private TabulatedFunction tabulatedFunction;

    @GetMapping("/createFunctionPage")
    public String createFunctionPage(Model model) {
        // Отображение страницы для создания табулированной функции
        model.addAttribute("pointCountField", new String());
        return "createFunctionPage";
    }

    @GetMapping("/createTable")
    public String createTable(@ModelAttribute("pointCountField") String pointCountField, Model model) {
        int pointCount;
        try {
            pointCount = Integer.parseInt(pointCountField);
            if (pointCount < 2) {
                model.addAttribute("error", "Введите корректное количество точек!");
                return "createFunctionPage";
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Введите корректное количество точек!");
            return "createFunctionPage";
        }

        model.addAttribute("table", new Object[pointCount][2]); // Создание таблицы с 'pointCount' строками
        return "tablePage"; // Показать страницу таблицы
    }

    @GetMapping("/createTabulatedFunction")
    public String createTabulatedFunction(@ModelAttribute("table") Object[][] table, Model model) {
        double[] xValues = new double[table.length];
        double[] yValues = new double[table.length];

        try {
            for (int i = 0; i < table.length; i++) {
                xValues[i] = Double.parseDouble(table[i][0].toString());
                yValues[i] = Double.parseDouble(table[i][1].toString());
            }
            tabulatedFunction = factory.create(xValues, yValues);
            dbTools.createTBFunction(xValues, yValues);
            model.addAttribute("success", "Функция успешно создана!");
            return "successPage"; // Перенаправление на страницу успеха
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Введите корректные значения для всех точек!");
            return "createFunctionPage";
        } catch (ArrayIsNotSortedException e) {
            model.addAttribute("error", "Введите точки x в порядке возрастания!");
            return "createFunctionPage";
        }
    }

    @GetMapping("/viewTabulatedFunction")
    public String viewTabulatedFunction(Model model) {
        model.addAttribute("tabulatedFunction", tabulatedFunction);
        return "viewTabulatedFunctionPage"; // Отображение созданной функции
    }

}
