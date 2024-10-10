package tests;


import functions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class andThenTest {
    @Test
    void Test1(){
        DeBoorAlgorithmFunction f = new DeBoorAlgorithmFunction(new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, new double[]{0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100}, 1);
        IdentityFunction g = new IdentityFunction();
        SqrFunction h = new SqrFunction();
        double result= f.andThen(g).andThen(h).apply(4);
        Assertions.assertEquals(1, result);
        result = g.andThen(f).andThen(h).apply(9);
        Assertions.assertEquals(4, result);
    }
    @Test
    void Test2(){
        ConstantFunction f = new ConstantFunction(42);
        ZeroFunction g = new ZeroFunction();
        UnitFunction h = new UnitFunction();
        double result= f.andThen(g).andThen(h).apply(4);
        Assertions.assertEquals(42, result);
        result = g.andThen(f).andThen(h).apply(9);
        Assertions.assertEquals(0, result);
    }
}