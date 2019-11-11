package trigan.samples.matrix.numbers;

import org.junit.Assert;
import org.junit.Test;
import trigan.samples.matrix.Matrix;

import static trigan.samples.matrix.rangecheck.RangeCheckingMatrix.rangeChecking;

public class IntMatrixMultiplicationTest {
    @Test
    public void simpleCase() {
        Matrix<Integer> left = rangeChecking(intMatrixFromArray(new int[][]{
                {5, 1, 3},
                {9, 0, 6}
        }));

        Matrix<Integer> right = rangeChecking(intMatrixFromArray(new int[][]{
                {1, 7},
                {0, 4},
                {9, 8}
        }));

        Matrix<Integer> expectedProduct = rangeChecking(intMatrixFromArray(new int[][]{
                {32, 63},
                {63, 111}
        }));

        Matrix<Integer> actualProduct = rangeChecking(new NumberMatrix<Integer>(Integer.class, 2, 2));
        actualProduct.putProductOf(left, right, false);

        Assert.assertEquals(expectedProduct.toString(), actualProduct.toString());
    }

    private NumberMatrix<Integer> intMatrixFromArray(int[][] array) {
        NumberMatrix<Integer> matrix = new NumberMatrix<>(Integer.class, array.length, array[0].length);

        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[0].length; j++)
                matrix.set(i, j, array[i][j]);

        return matrix;
    }
}
