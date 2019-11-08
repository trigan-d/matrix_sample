package trigan.samples.matrix;

public interface Vector<T> {
    int getSize();


    T get(int i);

    void set(int i, T val);


    T getScalarProduct(Vector<T> other);
}
