package trigan.samples.matrix.bool;

import org.junit.Assert;
import org.junit.Test;

public class BitMatrixMultiplicationTest {
    @Test
    public void simpleCase() {
        BitMatrix left = bitMatrixFromArray(new int[][]{
                {1, 0},
                {1, 1},
        });

        BitMatrix right = bitMatrixFromArray(new int[][]{
                {0, 1},
                {1, 1},
        });

        BitMatrix expectedProduct = bitMatrixFromArray(new int[][]{
                {0, 1},
                {1, 0},
        });

        BitMatrix actualProduct = new BitMatrix(2, 2);
        actualProduct.putProductOf(left, right, false);

        Assert.assertEquals(expectedProduct.toString(), actualProduct.toString());
    }

    private BitMatrix bitMatrixFromArray(int[][] array) {
        BitMatrix matrix = new BitMatrix(array.length, array[0].length);

        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[0].length; j++)
                matrix.set(i, j, array[i][j] == 1);

        return matrix;
    }
}
