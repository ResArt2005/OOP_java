package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.ArrayTabulatedFunctionFactory;

import java.util.Map;

@Controller
@RequestMapping("/main")
public class TabulatedFunctionByArraysController {

    private final ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();

    @PostMapping("/createFunctionByArrays")
    @ResponseBody
    public String createTable(@RequestParam("art_byArr_pointsCount") int pointsCount) {
        if (pointsCount < 2) {
            return "<div class=\"art_error\">Количество строк таблицы должно быть не меньше двух</div>";
        }
        return generateTableHtml(pointsCount);
    }

    @GetMapping("/createFunctionByArrays")
    public String returnToMain() {
        return "main";
    }

    @PostMapping("/submitFunction")
    @ResponseBody
    public String submitFunction(@RequestBody Map<String, double[]> data) {
        double[] xValues = data.get("xValues");
        double[] yValues = data.get("yValues");
        for (int i = 0; i < xValues.length - 1; ++i) {
            if(xValues[i] >= xValues[i+1])
                return "<div class=\"art_state\" id=\"art_stateId\">\n" +
                    "    <div class=\"art_state_content\">\n" +
                    "        <div class=\"art_error\">Ошибка</div>\n" +
                    "        <div class=\"art_state_h1\">Значения X должны быть в порядке возрастания</div>\n" +
                    "        <button id='ok' class=\"art_state_button close\" data-modal-id=\"art_stateId\" >Ок</button>\n" +
                    "    </div>\n" +
                    "</div>";
        }
        TabulatedFunction function = factory.create(xValues, yValues);
        return "";
        /*return "<div class=\"art_state\" id=\"art_stateId\">\n" +
                    "    <div class=\"art_state_content\">\n" +
                    "        <div class=\"art_success\">Успех</div>\n" +
                    "        <div class=\"art_state_h1\">Табулированная функция успешно создана</div>\n" +
                    "        <button id='ok' class=\"art_state_button close\" data-modal-id=\"art_stateId\" >Ок</button>\n" +
                    "    </div>\n" +
                    "</div>";*/
    }

    private String generateTableHtml(int pointsCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form id='submitFunctionForm'>");
        sb.append("<table class='art_byArr_table'>");
        sb.append("<thead><tr><th>x</th><th>y</th></tr></thead>");
        sb.append("<tbody style='overflow:auto; max-height: 30vw;'>");
        for (int i = 0; i < pointsCount; i++) {
            sb.append("<tr>");
            sb.append("<td><input type='number' step='any' name='xValues' required/></td>");
            sb.append("<td><input type='number' step='any' name='yValues' required/></td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("<button id='art_submitFunctionForm' data-modal-id='submitFunctionForm' type='button' class='art_byArr_create-function-btn'>Создать функцию</button>");
        sb.append("</form>");
        return sb.toString();
    }
}