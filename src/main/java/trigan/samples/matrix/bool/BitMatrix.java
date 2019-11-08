package trigan.samples.matrix.bool;

import trigan.samples.matrix.Matrix;
import trigan.samples.matrix.Vector;

import java.util.BitSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of {@link Matrix<Boolean>} that uses BitSet to store the data.
 * In common case it should be the most optimal implementation both by speed and by memory.
 * <p>
 * But in case of huge sparse matrices the memory consumption becomes not optimal.
 * For them one should use optimization techniques like the one described at http://java-performance.info/bit-sets/
 */
public class BitMatrix implements Matrix<Boolean> {
    private final int height;
    private final int width;
    private final BitSet bits;

    public BitMatrix(int height, int width) {
        this.height = height;
        this.width = width;
        this.bits = new BitSet(height * width);
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
    public Boolean get(int i, int j) {
        return bits.get(bitIndex(i, j));
    }

    @Override
    public void set(int i, int j, Boolean val) {
        if (val) bits.set(bitIndex(i, j));
        else bits.clear(bitIndex(i, j));
    }

    @Override
    public Vector<Boolean> getRow(int i) {
        BitSet rowBits = bits.get(bitIndex(i, 0), bitIndex(i + 1, 0));
        return new BitVector(width, rowBits);
    }

    @Override
    public Vector<Boolean> getColumn(int j) {
        BitSet columnBits = new BitSet(height);

        IntStream.range(0, height)
                .filter(i -> bits.get(bitIndex(i, j)))
                .forEach(columnBits::set);

        return new BitVector(height, columnBits);
    }

    private int bitIndex(int i, int j) {
        return i * width + j;
    }

    @Override
    public String toString() {
        return IntStream.range(0, height)
                .mapToObj(i -> this.getRow(i).toString())
                .collect(Collectors.joining("\n"));
    }
}
