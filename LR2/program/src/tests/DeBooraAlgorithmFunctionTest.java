package tests;

import functions.DeBoorAlgorithmFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
//
class DeBooraAlgorithmFunctionTest {

    @Test
    void apply_1() {
        DeBoorAlgorithmFunction obj_1 = new DeBoorAlgorithmFunction(new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, new double[]{0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100}, 1);
        Assertions.assertEquals(25, obj_1.apply(6), 0.0001);
        Assertions.assertEquals(64, obj_1.apply(9), 0.0001);
        Assertions.assertEquals(16, obj_1.apply(5), 0.0001);
        Assertions.assertEquals(49, obj_1.apply(8), 0.0001);
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                obj_1.apply(10);
            }
        });
    }
    @Test
    void apply_2() {
        DeBoorAlgorithmFunction obj_2 = new DeBoorAlgorithmFunction(new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, new double[]{0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100}, 2);
        Assertions.assertEquals(18.74, obj_2.apply(5.8), 0.0001);
        Assertions.assertEquals(8.9171, obj_2.apply(4.444), 0.0001);
        Assertions.assertEquals(26.2599, obj_2.apply(6.6), 0.0001);
        Assertions.assertEquals(0.9389, obj_2.apply(2.33), 0.0001);
        //
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                obj_2.apply(9);
            }
        });
    }
    /* Просто для себя
    @Test
    void test1() {
        DeBoorAlgorithmFunction obj = new DeBoorAlgorithmFunction(new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, new double[]{0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100}, 3);
        System.out.println(obj.apply(6));
        System.out.println(obj.apply(7));
        System.out.println(obj.apply(5));
        System.out.println(obj.apply(6.5));
    }
    @Test
    void test2() {
        DeBoorAlgorithmFunction obj = new DeBoorAlgorithmFunction(new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, new double[]{0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100}, 2);
        System.out.println(obj.apply(5.8));
        System.out.println(obj.apply(4.444));
        System.out.println(obj.apply(6.6));
        System.out.println(obj.apply(2.33));
    }
    */
}