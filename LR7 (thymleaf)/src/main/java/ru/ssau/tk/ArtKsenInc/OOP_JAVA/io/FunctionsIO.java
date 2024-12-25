package ru.ssau.tk.ArtKsenInc.OOP_JAVA.io;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.ArrayTabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.Point;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.factory.TabulatedFunctionFactory;
import com.google.gson.Gson;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

final public class FunctionsIO {
    private FunctionsIO() {
        throw new UnsupportedOperationException();
    }

    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) {
        // Создаем PrintWriter, оборачивая переданный BufferedWriter
        PrintWriter printWriter = new PrintWriter(writer);
        // Получаем количество точек в функции
        int count = function.getCount();
        // Записываем количество точек в первой строке
        printWriter.println(count);
        // Перебираем точки функции и записываем каждую из них
        for (Point point : function) {  // Предполагается, что TabulatedFunction реализует Iterable<Point>
            double x = point.x; // Получаем x
            double y = point.y; // Получаем y
            printWriter.printf("%f %f%n", x, y); // Записываем в формате "x y"
        }
        // Пробрасываем данные из буфера
        printWriter.flush();
    }

    public static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(function.getCount());
        for (Point point : function) {
            dataOutputStream.writeDouble(point.x);
            dataOutputStream.writeDouble(point.y);
        }
        dataOutputStream.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        try {
            String firstLine = reader.readLine();
            int count = Integer.parseInt(firstLine);
            double[] xValues = new double[count];
            double[] yValues = new double[count];
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.forLanguageTag("ru"));
            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                String[] parts = line.split(" ");
                try {
                    xValues[i] = numberFormatter.parse(parts[0]).doubleValue();
                    yValues[i] = numberFormatter.parse(parts[1]).doubleValue();
                } catch (ParseException e) {
                    throw new IOException();
                }
            }
            return factory.create(xValues, yValues);
        } catch (IOException exception) {
            throw exception;
        } catch (NumberFormatException e) {
            throw new IOException();
        }
    }

    public static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        DataInputStream stream = new DataInputStream(inputStream);
        int length = stream.readInt();
        double[] xValues = new double[length];
        double[] yValues = new double[length];
        for (int i = 0; i < length; ++i) {
            xValues[i] = stream.readDouble();
            yValues[i] = stream.readDouble();
        }
        return factory.create(xValues, yValues);
    }

    public static void serialize(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
        objectOutputStream.writeObject(function);
        objectOutputStream.flush();
    }

    public static TabulatedFunction deserialize(BufferedInputStream stream) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(stream);
        return (TabulatedFunction) objectInputStream.readObject();
    }

    public static void serializeJson(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        Gson gson = new Gson();
        double[] xValues = new double[function.getCount()];
        double[] yValues = new double[function.getCount()];

        for (int i = 0; i < function.getCount(); i++) {
            xValues[i] = function.getX(i);
            yValues[i] = function.getY(i);
        }

        ArraysData data = new ArraysData(xValues, yValues);
        String json = gson.toJson(data);
        stream.write(json.getBytes());
        stream.flush();
    }

    public static TabulatedFunction deserializeJson(BufferedInputStream stream) throws IOException, ClassNotFoundException {
        Gson gson = new Gson();
        StringBuilder jsonBuilder = new StringBuilder();
        int ch;

        while ((ch = stream.read()) != -1) {
            jsonBuilder.append((char) ch);
        }

        ArraysData data = gson.fromJson(jsonBuilder.toString(), ArraysData.class);
        return new ArrayTabulatedFunction(data.getXValues(), data.getYValues());
    }

    private record ArraysData(double[] xValues, double[] yValues) {

        public double[] getXValues() {
            return xValues;
        }

        public double[] getYValues() {
            return yValues;
        }
    }

    public static void serializeXML(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        try {
            // Создание документа XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Корневой элемент <Arrays>
            Element rootElement = doc.createElement("Arrays");
            doc.appendChild(rootElement);

            // Элемент <xValues>
            Element xValuesElement = doc.createElement("xValues");
            rootElement.appendChild(xValuesElement);
            for (int i = 0; i < function.getCount(); i++) {
                Element value = doc.createElement("value");
                value.appendChild(doc.createTextNode(String.valueOf(function.getX(i))));
                xValuesElement.appendChild(value);
            }

            // Элемент <yValues>
            Element yValuesElement = doc.createElement("yValues");
            rootElement.appendChild(yValuesElement);
            for (int i = 0; i < function.getCount(); i++) {
                Element value = doc.createElement("value");
                value.appendChild(doc.createTextNode(String.valueOf(function.getY(i))));
                yValuesElement.appendChild(value);
            }

            // Преобразование DOM объекта в XML и запись в stream
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(stream);
            transformer.transform(source, result);

            // Закрытие потока
            stream.flush();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            throw new IOException("Error during XML serialization", e);
        }
    }

    public static TabulatedFunction deserializeXML(BufferedInputStream stream) throws IOException {
        try {
            // Создание парсера для чтения XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(stream);

            // Получение списков элементов <value> для xValues и yValues
            NodeList xValuesNodes = doc.getElementsByTagName("xValues").item(0).getChildNodes();
            NodeList yValuesNodes = doc.getElementsByTagName("yValues").item(0).getChildNodes();

            // Создание массивов для x и y
            int count = 0;
            for (int i = 0; i < xValuesNodes.getLength(); i++) {
                if (xValuesNodes.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    count++;
                }
            }

            double[] xValues = new double[count];
            double[] yValues = new double[count];

            int index = 0;
            for (int i = 0; i < xValuesNodes.getLength(); i++) {
                if (xValuesNodes.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    xValues[index] = Double.parseDouble(xValuesNodes.item(i).getTextContent());
                    yValues[index] = Double.parseDouble(yValuesNodes.item(i).getTextContent());
                    index++;
                }
            }

            // Воссоздание TabulatedFunction
            return new ArrayTabulatedFunction(xValues, yValues);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error during XML deserialization", e);
        }
    }
}
