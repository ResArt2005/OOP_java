package tests;

import functions.DeFuraAlgorithmFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeFuraAlgorithmFunctionTest {

    @Test
    void apply() {
        DeFuraAlgorithmFunction obj = new DeFuraAlgorithmFunction(1, new double[]{8, 5, 7, 8,9,4,7,5,12,55,77}, new double[]{0, 5, 10, 20, 10, 7, 5, 78, 9}, 2);
        Assertions.assertEquals(-15, obj.apply(3));
        Assertions.assertEquals(15, obj.apply(9));
        Assertions.assertEquals(60, obj.apply(18));
        Assertions.assertEquals(465, obj.apply(99));
    }
}