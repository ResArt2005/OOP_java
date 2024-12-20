package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.ArrayTabulatedFunctionFactory;

@Controller
@RequestMapping("/{contextPath}")
public class TabulatedFunctionByArraysController {

    private final ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();

    @GetMapping("/createFunctionByArrays")
    public String showForm() {
        return "createFunctionByArrays";
    }

    @PostMapping("/createFunctionByArrays")
    @ResponseBody
    public String createTable(@RequestParam("pointsCount") int pointsCount, Model model) {
        model.addAttribute("pointsCount", pointsCount);
        return generateTableHtml(pointsCount); // Возвращаем HTML таблицы
    }

    @PostMapping("/submit-function")
    @ResponseBody
    public String submitFunction(@RequestParam("xValues") double[] xValues,
                                 @RequestParam("yValues") double[] yValues) {
        if (xValues.length != yValues.length || xValues.length == 0) {
            return "{\"status\":\"error\", \"message\":\"Количество значений X и Y должно совпадать и быть больше нуля.\"}";
        }

        TabulatedFunction function = factory.create(xValues, yValues);
        return "{\"status\":\"success\", \"message\":\"Табулированная функция успешно создана.\"}";
    }

    private String generateTableHtml(int pointsCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form id='submitFunctionForm'>");
        sb.append("<table>");
        sb.append("<thead><tr><th>x</th><th>y</th></tr></thead>");
        sb.append("<tbody>");
        for (int i = 0; i < pointsCount; i++) {
            sb.append("<tr>");
            sb.append("<td><input type='number' step='any' name='xValues' required/></td>");
            sb.append("<td><input type='number' step='any' name='yValues' required/></td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("<button type='submit' class='create-function-btn'>Создать функцию</button>");
        sb.append("</form>");
        return sb.toString();
    }
}
