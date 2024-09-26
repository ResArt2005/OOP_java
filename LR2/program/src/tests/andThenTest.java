package tests;


import functions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class andThenTest {
    @Test
    void Test1(){
        SquareFunction f = new SquareFunction();
        CubeFunction g = new CubeFunction();
        SqrFunction h = new SqrFunction();
        double result= f.andThen(g).andThen(h).apply(4);
        System.out.println(result);
        result = g.andThen(f).andThen(h).apply(9);
        System.out.println(result);
    }
    @Test
    void Test2(){
        ConstantFunction f = new ConstantFunction(42);
        ZeroFunction g = new ZeroFunction();
        UnitFunction h = new UnitFunction();
        double result= f.andThen(g).andThen(h).apply(4);
        System.out.println(result);
        result = g.andThen(f).andThen(h).apply(9);
        System.out.println(result);
    }
}