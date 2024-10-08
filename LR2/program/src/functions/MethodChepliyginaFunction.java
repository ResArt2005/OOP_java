package functions;

public class MethodChepliyginaFunction implements MathFunction{
    private final double x0; // Начальное значение x
    private final double y0; // Начальное значение y
    private final double h;   // Шаг
    private final int n;      // Количество шагов
    public MethodChepliyginaFunction(double x0, double y0, double h, int n) {
        this.x0 = x0;
        this.y0 = y0;
        this.h = h;
        this.n = n;
    }
    @Override
    public double apply(double x) {
        // Считаем индекс текущего x
        int index = (int)((x - x0) / h);
        if (index < 0 || index >= n) {
            throw new IllegalArgumentException("x вне допустимого диапазона");
        }
        double y = y0; // Инициализация y
        for (int i = 0; i < index; i++) {
            // Вычисление приближения методом двухсторонних
            // Например, если y' = f(x, y), вы можете заменить f на вашу функцию
            double f1 = f(x0 + i * h, y); // f(x, y) в текущей точке
            double y_next = y + h * f1; // Прямое приближение (на следующий шаг)

            double f2 = f(x0 + (i + 1) * h, y_next);
            y = y + (h / 2) * (f1 + f2); // Обновление y с использованием усреднения
        }
        return y; // Возвращаем приближенное значение
    }
    // Пример определения вашей функции f(x, y)
    private double f(double x, double y) {
        return x + y; // Замените это свою функцию
    }
}
