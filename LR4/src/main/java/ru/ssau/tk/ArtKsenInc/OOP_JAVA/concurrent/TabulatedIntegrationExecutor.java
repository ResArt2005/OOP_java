package ru.ssau.tk.ArtKsenInc.OOP_JAVA.concurrent;
import ru.ssau.tk.ArtKsenInc.OOP_JAVA.functions.TabulatedFunction;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class TabulatedIntegrationExecutor {
    private final int countOfStreams;
    private final ExecutorService ExService;
    public TabulatedIntegrationExecutor(int countOfStreams) {
        if(countOfStreams <= 0){
            throw new IllegalArgumentException();
        }
        this.countOfStreams = countOfStreams;
        this.ExService = Executors.newFixedThreadPool(countOfStreams);
    }
    public double Integrate(TabulatedFunction function) throws ExecutionException, InterruptedException {
        ArrayList<Future<Double>> allPiecesInOne = new ArrayList<>();
        double TheWholeSum = Math.abs((function.rightBound() - function.leftBound())) / countOfStreams;
        double partPrev, partNext;
        for (int i = 0; i < countOfStreams; ++i) {
            partPrev = function.leftBound() + i * TheWholeSum;
            partNext = partPrev + TheWholeSum;
            TabulatedIntegration IntegralPiece = new TabulatedIntegration(function, partPrev, partNext);
            allPiecesInOne.add(ExService.submit(IntegralPiece));
        }
        double result = 0;
        for (Future<Double> piece : allPiecesInOne) {
                result += piece.get();
        }
        return result;
    }
    public void shutdown() {
        ExService.shutdown();
    }
}
