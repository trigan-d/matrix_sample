package trigan.samples.matrix.numbers;

import trigan.samples.matrix.Matrix;
import trigan.samples.matrix.Vector;

import java.lang.reflect.Array;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberMatrix<T extends Number> implements Matrix<T> {
    private final Class<T> numberClass;
    private final int height;
    private final int width;
    private final NumberVector<T>[] rows;

    public NumberMatrix(Class<T> numberClass, int height, int width) {
        this.numberClass = numberClass;
        this.height = height;
        this.width = width;
        this.rows = (NumberVector<T>[]) Array.newInstance(NumberVector.class, height);
        IntStream.range(0, height).forEach(i -> rows[i] = new NumberVector<>(numberClass, width));
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public T get(int i, int j) {
        return rows[i].get(j);
    }

    @Override
    public void set(int i, int j, T val) {
        rows[i].set(j, val);
    }

    @Override
    public Vector<T> getRow(int i) {
        return rows[i];
    }

    @Override
    public Vector<T> getColumn(int j) {
        NumberVector<T> result = new NumberVector<>(numberClass, height);
        IntStream.range(0, height).forEach(i -> result.set(i, get(i, j)));
        return result;
    }

    @Override
    public String toString() {
        return IntStream.range(0, height)
                .mapToObj(i -> this.getRow(i).toString())
                .collect(Collectors.joining("\n"));
    }
}
