package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("fabricType")
public class RedactorTBFuncController {
    @PostMapping("/Graph")
    @ResponseBody
    public Map<String, double[]> Graph(@RequestBody Map<String, double[]> data, HttpSession session) {
        Map<String, double[]> graphData = new HashMap<>();
        graphData.put("xValues", data.get("xValues"));
        graphData.put("yValues", data.get("yValues"));

        return graphData;
    }

    @PostMapping("/apply")
    @ResponseBody
    public String apply(@RequestBody Map<String, Object> data, HttpSession session) {
        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
        double[] xValues = ((List<Number>) data.get("xValues")).stream().mapToDouble(Number::doubleValue).toArray();
        double[] yValues = ((List<Number>) data.get("yValues")).stream().mapToDouble(Number::doubleValue).toArray();
        Object xValue = data.get("X"); // Получаем значение из data
        if (xValue == null) {
            return "Введите значение по X!";
        }
        try {
            double X = Double.parseDouble(xValue.toString()); // Пробуем преобразовать значение в double
            TabulatedFunction function = factory.create(xValues, yValues);
            double Y = function.apply(X);
            return "Значение функции в точке " + X + " равно " + Y;
            // Здесь можно продолжать работать с X
        } catch (NumberFormatException e) {
            return "Введите значение по X!";
        }
    }

    public String graphBuilder(TabulatedFunction function) {
        StringBuilder sb = new StringBuilder();
        //function.getX(index),function.getY(index)
        //Построение графика...
        return sb.toString();
    }
}
