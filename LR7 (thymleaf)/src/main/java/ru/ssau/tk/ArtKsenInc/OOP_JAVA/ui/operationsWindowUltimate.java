package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;

import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("fabricType")
public class operationsWindowUltimate {

    //private static final Logger logger = Logger.getLogger(operationsWindowUltimate.class.getName());

    @GetMapping("/chooseElementaryOperationAndCalculate")
    public String returnToMain() {
        return "redirect:/main";
    }

    @PostMapping("/chooseElementaryOperationAndCalculate")
    @ResponseBody
    public String submitFunction(@RequestBody Map<String, Object> data, HttpSession session) {
        String operationName = (String) data.get("operationName");
        List<Number> xValues_1_List = (List<Number>) data.get("xValues_1");
        List<Number> yValues_1_List = (List<Number>) data.get("yValues_1");
        List<Number> xValues_2_List = (List<Number>) data.get("xValues_2");
        List<Number> yValues_2_List = (List<Number>) data.get("yValues_2");

        if (xValues_1_List == null || yValues_1_List == null || xValues_2_List == null || yValues_2_List == null) {
            return "error: Полученные данные пустые.";
        }

        double[] xValues_1 = xValues_1_List.stream().mapToDouble(Number::doubleValue).toArray();
        double[] yValues_1 = yValues_1_List.stream().mapToDouble(Number::doubleValue).toArray();
        double[] xValues_2 = xValues_2_List.stream().mapToDouble(Number::doubleValue).toArray();
        double[] yValues_2 = yValues_2_List.stream().mapToDouble(Number::doubleValue).toArray();

        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
        if (factory == null) {
            //logger.severe("Factory not found in session.");
            return "error: Factory not found in session.";
        }

        TabulatedFunction function_1 = factory.create(xValues_1, yValues_1);
        TabulatedFunction function_2 = factory.create(xValues_2, yValues_2);
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        return switch (operationName) {
            case "sum" -> createTable(service.sum(function_1, function_2));
            case "subtract" -> createTable(service.subtract(function_1, function_2));
            case "multiplication" -> createTable(service.multiplication(function_1, function_2));
            case "division" -> createTable(service.division(function_1, function_2));
            default -> "error: Invalid operation name.";
        };
    }

    public String createTable(TabulatedFunction function) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < function.getCount(); i++) {
            sb.append("<tr><td><input class=\"art_input_x\" type='number' step='any' name='xValues' value=\"")
                    .append(function.getX(i)).append("\" readonly></td>");
            sb.append("<td><input class=\"art_input_y\" type='number' step='any' name='yValues' value=\"")
                    .append(function.getY(i)).append("\" readonly></td></tr>");
        }
        return sb.toString();
    }
}