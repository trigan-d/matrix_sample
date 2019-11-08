package trigan.samples.matrix;

import java.util.stream.IntStream;

public interface Matrix<T> {
    int getHeight();

    int getWidth();


    T get(int i, int j);

    void set(int i, int j, T val);


    Vector<T> getRow(int i);

    Vector<T> getColumn(int j);


    /**
     * Multiplies two matrices and stores the product in "this" matrix.
     * This API is just the very basic form. But it could be easily extended.
     * For example, to calculate each scalar product of the vectors on different nodes in a cluster.
     *
     * @param left:     left-side matrix
     * @param right:    right-side matrix
     * @param parallel: set to TRUE to perform parallel computation
     */
    default void putProductOf(Matrix<T> left, Matrix<T> right, boolean parallel) {
        IntStream cellIndices = IntStream.range(0, left.getHeight() * right.getWidth());

        /*
            I am aware of the Stream API limitation that forces all parallel computations to run on the common ForkJoinPool.
            And I know about the problems it creates.
            But for the sake of simplicity and brevity in this test task I decided to ignore this fact.

            In production code I'd obviously use https://github.com/pivovarit/parallel-collectors
            It won't affect the API much. All what we need is to pass an Executor instance to this method instead of the "parallel flag".
            And for the sequential multiplication we could pass a default Executor that works over the current thread.
            Like this one: https://stackoverflow.com/a/6583868/1456045
         */
        (parallel ? cellIndices.parallel() : cellIndices.sequential())
                .forEach(cellIndex -> {
                    int i = cellIndex / left.getHeight();
                    int j = cellIndex % left.getHeight();
                    T val = left.getRow(i).getScalarProduct(right.getColumn(j));
                    set(i, j, val);
                });
    }
}
