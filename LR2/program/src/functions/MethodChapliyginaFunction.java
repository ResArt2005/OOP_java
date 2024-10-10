package functions;
import java.util.Arrays;
public class MethodChapliyginaFunction implements MathFunction {
    StandardFunction equation;
    double x0;  // начальная точка
    double[] xValues;  // массив упорядоченных значений x
    double[] yValues;  // массив соответствующих значений y для каждого x

    public MethodChapliyginaFunction(StandardFunction equation, double x0, double[] xValues, double[] yValues) {
        this.equation = equation;
        this.x0 = x0;
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
    }

    @Override
    public double apply(double x) {
        // Предположим, что нужно решить уравнение в точке x
        int n = findNearestXIndex(x);  // Найдём ближайший индекс к x

        double h = xValues[n] - x0;  // Шаг между x и x0
        double yPrev = yValues[n];  // Предыдущее значение y

        // Вычисляем производную и интеграл
        double derivative = equation.derivative(x0);
        double integral = equation.integral(x0, xValues[n]);

        // Метод Чаплыгина предполагает вычисление нового y через производную и интеграл
        return chaplyginStep(yPrev, derivative, integral, h);
    }

    // Метод нахождения ближайшего индекса для значения x
    private int findNearestXIndex(double x) {
        int nearestIndex = 0;
        double minDifference = Math.abs(x - xValues[0]);
        for (int i = 1; i < xValues.length; ++i) {
            double difference = Math.abs(x - xValues[i]);
            if (difference < minDifference) {
                minDifference = difference;
                nearestIndex = i;
            }
        }
        return nearestIndex;
    }

    // Алгоритм одного шага Чаплыгина
    private double chaplyginStep(double yPrev, double derivative, double integral, double h) {
        // y_n+1 = y_n + h * derivative + (h^2 / 2) * integral
        return yPrev + h * derivative + (Math.pow(h, 2) / 2) * integral;
    }
}
