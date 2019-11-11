package trigan.samples.matrix.rangecheck;

import trigan.samples.matrix.Vector;

public class RangeCheckingVector<T> implements Vector<T> {
    private final Vector<T> vector;

    public RangeCheckingVector(Vector<T> vector) {
        this.vector = vector;
    }

    public static <V> RangeCheckingVector<V> rangeChecking(Vector<V> vector) {
        return new RangeCheckingVector<>(vector);
    }

    @Override
    public int getSize() {
        return vector.getSize();
    }

    @Override
    public T get(int i) {
        checkIndex(i);
        return vector.get(i);
    }

    @Override
    public void set(int i, T val) {
        checkIndex(i);
        vector.set(i, val);
    }

    @Override
    public T getScalarProduct(Vector<T> other) {
        if (getSize() != other.getSize()) throw new IllegalArgumentException("vector sizes do not match");
        else return vector.getScalarProduct(other);
    }

    @Override
    public String toString() {
        return vector.toString();
    }

    private void checkIndex(int i) {
        if (i < 0) throw new IndexOutOfBoundsException(i + " < 0");
        else if (i >= getSize()) throw new IndexOutOfBoundsException(i + " > " + getSize());
    }
}
