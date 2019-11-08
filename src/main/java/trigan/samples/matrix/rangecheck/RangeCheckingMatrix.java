package trigan.samples.matrix.rangecheck;

import trigan.samples.matrix.Matrix;
import trigan.samples.matrix.Vector;

public class RangeCheckingMatrix<T> implements Matrix<T> {
    private final Matrix<T> matrix;

    public RangeCheckingMatrix(Matrix<T> matrix) {
        this.matrix = matrix;
    }

    public static <V> RangeCheckingMatrix<V> rangeChecking(Matrix<V> matrix) {
        return new RangeCheckingMatrix<>(matrix);
    }

    @Override
    public int getHeight() {
        return matrix.getHeight();
    }

    @Override
    public int getWidth() {
        return matrix.getWidth();
    }

    @Override
    public T get(int i, int j) {
        checkRowIndex(i);
        checkColumnIndex(j);
        return matrix.get(i, j);
    }

    @Override
    public void set(int i, int j, T val) {
        checkRowIndex(i);
        checkColumnIndex(j);
        matrix.set(i, j, val);
    }

    @Override
    public Vector<T> getRow(int i) {
        checkRowIndex(i);
        return matrix.getRow(i);
    }

    @Override
    public Vector<T> getColumn(int j) {
        checkColumnIndex(j);
        return matrix.getColumn(j);
    }

    @Override
    public void putProductOf(Matrix<T> a, Matrix<T> b, boolean parallel) {
        if (a.getWidth() != b.getHeight()) {
            throw new IllegalArgumentException("matrices of that sizes could not by multiplied");
        } else if (a.getHeight() != getHeight() || b.getWidth() != getWidth()) {
            throw new IllegalArgumentException("the product of the given matrices could not be stored in this matrix");
        } else {
            matrix.putProductOf(a, b, parallel);
        }
    }

    private void checkRowIndex(int i) {
        if (i < 0) throw new IndexOutOfBoundsException(i + " < 0");
        else if (i >= getHeight()) throw new IndexOutOfBoundsException(i + " > " + getHeight());
    }

    private void checkColumnIndex(int j) {
        if (j < 0) throw new IndexOutOfBoundsException(j + " < 0");
        else if (j >= getWidth()) throw new IndexOutOfBoundsException(j + " > " + getWidth());
    }
}
