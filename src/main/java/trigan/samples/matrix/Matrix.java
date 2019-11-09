package trigan.samples.matrix;

import java.util.List;
import java.util.stream.Collectors;
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
        List<Vector<T>> leftRows = IntStream.range(0, left.getHeight())
                .mapToObj(left::getRow).collect(Collectors.toList());

        List<Vector<T>> rightColumns = IntStream.range(0, right.getWidth())
                .mapToObj(right::getColumn).collect(Collectors.toList());

        IntStream cellIndices = IntStream.range(0, left.getHeight() * right.getWidth());

        /*
            I am aware of the Stream API limitation that forces all parallel computations to run on the common ForkJoinPool.
            And I know about the problems it could possibly create.
            But for the sake of simplicity and brevity in this test task I decided to ignore this fact.

            In production code one could use https://github.com/pivovarit/parallel-collectors, or other similar approaches.
            It won't affect the API much. All what we'll need is to pass an Executor instance.
         */
        (parallel ? cellIndices.parallel() : cellIndices.sequential())
                .forEach(cellIndex -> {
                    int i = cellIndex / left.getHeight();
                    int j = cellIndex % left.getHeight();
                    T val = leftRows.get(i).getScalarProduct(rightColumns.get(j));
                    set(i, j, val);
                });
    }
}
