package trigan.samples.matrix;

import trigan.samples.matrix.bool.BitMatrix;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();

        int dimension = Integer.parseInt(args[0]);

        BitMatrix left = new BitMatrix(dimension, dimension);
        BitMatrix right = new BitMatrix(dimension, dimension);
        BitMatrix product = new BitMatrix(dimension, dimension);

        System.out.println("populating matrices with random values, please wait...");

        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                left.set(i, j, rand.nextBoolean());
                right.set(i, j, rand.nextBoolean());
            }

        System.out.println("performing sequential multiplication...");

        long start = System.currentTimeMillis();
        product.putProductOf(left, right, false);
        System.out.println("sequential - " + (System.currentTimeMillis() - start) + " ms");

        System.out.println("performing parallel multiplication...");

        start = System.currentTimeMillis();
        product.putProductOf(left, right, true);
        System.out.println("parallel - " + (System.currentTimeMillis() - start) + " ms");
    }
}
