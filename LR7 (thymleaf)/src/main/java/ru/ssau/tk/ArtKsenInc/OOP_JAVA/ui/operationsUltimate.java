package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent.TabulatedIntegrationExecutor;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.io.FunctionsIO;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedDifferentialOperator;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.operations.TabulatedFunctionOperationService;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Controller
@SessionAttributes("fabricType")
public class operationsUltimate {
    @PostMapping("/serialize")
    @ResponseBody
    public byte[] serialize(@RequestBody Map<String, Object> data, HttpSession session) {
        try {
            TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
            if (factory == null) {
                return null;
            }

            String fileName = (String) data.get("filePath");
            double[] xValues = ((List<Number>) data.get("xValues")).stream().mapToDouble(Number::doubleValue).toArray();
            double[] yValues = ((List<Number>) data.get("yValues")).stream().mapToDouble(Number::doubleValue).toArray();

            TabulatedFunction tabulatedFunction = factory.create(xValues, yValues);

            try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                 BufferedOutputStream stream = new BufferedOutputStream(byteStream)) {
                switch (getFileExtension(fileName)) {
                    case "json":
                        FunctionsIO.serializeJson(stream, tabulatedFunction);
                        break;
                    case "xml":
                        FunctionsIO.serializeXML(stream, tabulatedFunction);
                        break;
                    case "bin":
                    default:
                        FunctionsIO.serialize(stream, tabulatedFunction);
                        break;
                }
                return byteStream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("/deserialize")
    @ResponseBody
    public String deserialize(@RequestParam("file") MultipartFile file, HttpSession session) {
        try {
            TabulatedFunction tabulatedFunction;
            try (BufferedInputStream stream = new BufferedInputStream(file.getInputStream())) {
                tabulatedFunction = switch (Objects.requireNonNull(getFileExtension(file.getOriginalFilename()))) {
                    case "json" -> FunctionsIO.deserializeJson(stream);
                    case "xml" -> FunctionsIO.deserializeXML(stream);
                    default -> FunctionsIO.deserialize(stream);
                };
            }

            return createTable(tabulatedFunction);
        } catch (Exception e) {
            e.printStackTrace();
            return "<div id=\"modalContainer\">\n" +
                    "    <div class=\"art_state\" id=\"art_stateUniqueErrorId\">\n" +
                    "        <div class=\"art_state_content\">\n" +
                    "            <div class=\"art_error\">Ошибка</div>\n" +
                    "            <div class=\"art_state_h1\">Вы хотя бы знаете, что ИМЕННО в этом файле????</div>\n" +
                    "            <button class=\"art_state_button close\" data-modal-id=\"art_stateUniqueErrorId\">Ок</button>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>";
        }
    }
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }
    @PostMapping("/chooseElementaryOperationAndCalculate")
    @ResponseBody
    public String submitElemFunction(@RequestBody Map<String, Object> data, HttpSession session) {
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
    @PostMapping("/chooseDefOperationAndCalculate")
    @ResponseBody
    public String submitDefFunction(@RequestBody Map<String, double[]> data, HttpSession session) {
        double[] xValues = data.get("xValues");
        double[] yValues = data.get("yValues");
        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
        if (xValues == null || yValues == null) {
            return "error: Полученные данные пустые.";
        }
        try{
            TabulatedFunction function = factory.create(xValues, yValues);
            TabulatedDifferentialOperator service = new TabulatedDifferentialOperator(factory);
            return createTable(service.derive(function));

        }
        catch (IllegalArgumentException e){
            return "error: Полученные данные пустые.";
        }
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
    @PostMapping("/insert")
    @ResponseBody
    public String insert(@RequestBody Map<String, Object> data, HttpSession session) {
        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
        double[] xValues = ((List<Number>) data.get("xValues")).stream().mapToDouble(Number::doubleValue).toArray();
        double[] yValues = ((List<Number>) data.get("yValues")).stream().mapToDouble(Number::doubleValue).toArray();
        Object xValue = data.get("X");
        Object yValue = data.get("Y");
        if (xValue == null || yValue == null) {
            return "";
        }
        try {
            double X = Double.parseDouble(xValue.toString());
            double Y = Double.parseDouble(yValue.toString());
            TabulatedFunction function = factory.create(xValues, yValues);
            function.insert(X, Y);
            return createTableY(function);
        } catch (NumberFormatException e) {
            return "";
        }
    }
    @PostMapping("/remove")
    @ResponseBody
    public String remove(@RequestBody Map<String, Object> data, HttpSession session) {
        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
        double[] xValues = ((List<Number>) data.get("xValues")).stream().mapToDouble(Number::doubleValue).toArray();
        double[] yValues = ((List<Number>) data.get("yValues")).stream().mapToDouble(Number::doubleValue).toArray();
        Object index = data.get("index");
        if (index == null) {
            return "";
        }
        try {
            int INDEX = Integer.parseInt(index.toString());
            TabulatedFunction function = factory.create(xValues, yValues);
            function.remove(INDEX);
            return createTableY(function);
        } catch (NumberFormatException e) {
            return "";
        }
    }
    private String createTableY(TabulatedFunction function) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < function.getCount(); i++) {
            sb.append("<tr><td><input class=\"art_input_x\" type='number' step='any' name='xValues' value=\"")
                    .append(function.getX(i)).append("\" readonly></td>");
            sb.append("<td><input class=\"art_input_y\" type='number' step='any' name='yValues' value=\"")
                    .append(function.getY(i)).append("\" required></td></tr>");
        }
        return sb.toString();
    }
    @PostMapping("/integrate")
    @ResponseBody
    public String integrate(@RequestBody Map<String, Object> data, HttpSession session) {
        double[] xValues = ((List<Number>) data.get("xValues")).stream().mapToDouble(Number::doubleValue).toArray();
        double[] yValues = ((List<Number>) data.get("yValues")).stream().mapToDouble(Number::doubleValue).toArray();
        int countStreams = Integer.parseInt(data.get("countStreams").toString());
        TabulatedIntegrationExecutor integral = new TabulatedIntegrationExecutor(countStreams);
        TabulatedFunctionFactory factory = (TabulatedFunctionFactory) session.getAttribute("fabricType");
        if (xValues == null || yValues == null) {
            return "error: Полученные данные пустые.";
        }
        try{
            TabulatedFunction function = factory.create(xValues, yValues);
            return Objects.toString(integral.Integrate(function));

        }
        catch (IllegalArgumentException e){
            return "error: Полученные данные пустые.";
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}