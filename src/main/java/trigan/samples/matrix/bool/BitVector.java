package trigan.samples.matrix.bool;

import trigan.samples.matrix.Vector;

import java.util.BitSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of {@link Vector<Boolean>} that uses BitSet to store the data.
 * In common case it should be the most optimal implementation both by speed and by memory.
 * <p>
 * But in case of huge sparse vectors the memory consumption becomes not optimal.
 * For them one should use optimization techniques like the one described at http://java-performance.info/bit-sets/
 */
public class BitVector implements Vector<Boolean> {
    private final int size;
    private final BitSet bits;

    public BitVector(int size) {
        this(size, new BitSet(size));
    }

    public BitVector(int size, BitSet bits) {
        this.size = size;
        this.bits = bits;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Boolean get(int i) {
        return bits.get(i);
    }

    @Override
    public void set(int i, Boolean val) {
        if (val) bits.set(i);
        else bits.clear(i);
    }

    @Override
    public Boolean getScalarProduct(Vector<Boolean> other) {
        //Copying is memory-consuming, but it guarantees immutability of the "other" on the next step.
        //If immutability is not required, then it could be optimized.
        BitSet bitCopyOfOther = bitCopyOf(other);
        //For fast multiplication with modulo 2 we can use bitwise AND.
        bitCopyOfOther.and(this.bits);
        //For fast addition with modulo 2 we can count the number of bits set to 1.
        //If it is even then the result is FALSE. If it is odd then the result is TRUE.
        return (bitCopyOfOther.cardinality() & 1) == 1;
    }

    public static BitSet bitCopyOf(Vector<Boolean> vector) {
        BitSet bitCopy = new BitSet(vector.getSize());

        if (vector instanceof BitVector) {
            bitCopy.or(((BitVector) vector).bits);
        } else {
            IntStream.range(0, vector.getSize())
                    .filter(vector::get)
                    .forEach(bitCopy::set);
        }

        return bitCopy;
    }

    @Override
    public String toString() {
        return IntStream.range(0, getSize())
                .mapToObj(i -> this.get(i) ? "1" : "0")
                .collect(Collectors.joining(" "));
    }
}
