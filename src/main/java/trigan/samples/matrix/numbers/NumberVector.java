package trigan.samples.matrix.numbers;

import trigan.samples.matrix.Vector;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberVector<T extends Number> implements Vector<T> {
    private final Class<T> numberClass;
    private final T[] array;

    private final BinaryOperator<Number> multiplication;
    private final BinaryOperator<Number> addition;

    private static Map<Class<? extends Number>, BinaryOperator<Number>> multiplicationOperators = new HashMap<>();
    private static Map<Class<? extends Number>, BinaryOperator<Number>> additionsOperators = new HashMap<>();

    static {
        multiplicationOperators.put(Double.class, (a, b) -> a.doubleValue() * b.doubleValue());
        multiplicationOperators.put(Float.class, (a, b) -> a.floatValue() * b.floatValue());
        multiplicationOperators.put(Long.class, (a, b) -> a.longValue() * b.longValue());
        additionsOperators.put(Double.class, (a, b) -> a.doubleValue() + b.doubleValue());
        additionsOperators.put(Float.class, (a, b) -> a.floatValue() + b.floatValue());
        additionsOperators.put(Long.class, (a, b) -> a.longValue() + b.longValue());
    }

    public NumberVector(Class<T> numberClass, int size) {
        this.numberClass = numberClass;
        multiplication = multiplicationOperators.getOrDefault(numberClass, (a, b) -> a.intValue() * b.intValue());
        addition = multiplicationOperators.getOrDefault(numberClass, (a, b) -> a.intValue() + b.intValue());
        array = (T[]) Array.newInstance(numberClass, size);
    }

    @Override
    public int getSize() {
        return array.length;
    }

    @Override
    public T get(int i) {
        return array[i];
    }

    @Override
    public void set(int i, T val) {
        array[i] = val;
    }

    @Override
    public T getScalarProduct(Vector<T> other) {
        return (T)
                IntStream.range(0, array.length)
                        .mapToObj(i -> multiplication.apply(this.get(i), other.get(i)))
                        .reduce(0, addition);
    }

    @Override
    public String toString() {
        return IntStream.range(0, getSize())
                .mapToObj(i -> this.get(i).toString())
                .collect(Collectors.joining(" "));
    }
}
