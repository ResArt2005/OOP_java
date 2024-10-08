package functions;

import java.util.Arrays;
//Вычисление сплайна S(x) по алгоритму де Бура.
public class DeFuraAlgorithmFunction implements MathFunction{
    private int segmentIndex;//Индекс сегмента, содержащего x
    private double[] nodeArray;//Массив узлов
    private double[] controlPoints;//Массив контрольных точек
    private int SplineDegree;//Степень сплайна
    public DeFuraAlgorithmFunction(int segmentIndex, double[] nodeArray, double[] controlPoints, int SplineDegree){
        this.segmentIndex = segmentIndex;
        this.nodeArray = Arrays.copyOf(nodeArray, nodeArray.length);
        this.controlPoints = Arrays.copyOf(controlPoints, controlPoints.length);
        this.SplineDegree = SplineDegree;
    }
    private double Algorithm(double x) {
        double[] d = new double[segmentIndex + 1];
        for (int i = 0; i <= segmentIndex; ++i) {
            d[i] = controlPoints[i + SplineDegree - segmentIndex];
        }
        for (int i = 1; i <= segmentIndex; ++i) {
            for (int j = segmentIndex; j >= i; --j) {
                double alpha = (x - nodeArray[j + SplineDegree - segmentIndex]) / (nodeArray[j + 1 + SplineDegree - i] - nodeArray[j + SplineDegree - segmentIndex]);
                d[j] = (1.0 - alpha) * d[j - 1] + alpha * d[j];
            }
        }
        return d[segmentIndex];
    }
    //Точка, в которой требуется вычислить сплайн.
    @Override
    public double apply(double x) {
        return Algorithm(x); // Возвращаем значение сплайна S(x)
    }
}
