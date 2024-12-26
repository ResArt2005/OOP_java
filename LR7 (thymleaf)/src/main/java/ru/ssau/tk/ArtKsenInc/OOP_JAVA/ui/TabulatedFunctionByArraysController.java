package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.special_classes.dbTools;

import java.util.Map;

@Controller
//@RequestMapping("/{contextPath}")
@SessionAttributes("fabricType")
public class TabulatedFunctionByArraysController {
    TabulatedFunction function;
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
        return "redirect:/main";
    }

    @PostMapping("/submitFunctionByArr")
    @ResponseBody
    public String submitFunction(@RequestBody Map<String, double[]> data, HttpSession session) {
        double[] xValues = data.get("xValues");
        double[] yValues = data.get("yValues");
        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
        function = factory.create(xValues, yValues);
        dbTools.createTBFunction(xValues, yValues);
        return createTable(function);
    }

    private String generateTableHtml(int pointsCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form id='submitFunctionForm'>");
        sb.append("<button id='art_submitFunctionForm' data-modal-id='submitFunctionForm' type='button' class='art_byArr_create-function-btn'>Создать функцию</button>");
        sb.append("<table class='art_byArr_table'>");
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
        sb.append("</form>");
        return sb.toString();
    }
    public String createTable(TabulatedFunction function){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < function.getCount(); i++) {
            sb.append("<tr><td><input class=\"art_input_x\" type='number' step='any' name='xValues' value=\"").append(function.getX(i)).append("\" readonly></td>");
            sb.append("<td><input class=\"art_input_y\" type='number' step='any' name='yValues' value=\"").append(function.getY(i)).append("\" required></td></tr>");
        }
        return sb.toString();
    }
}