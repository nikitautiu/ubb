package com.company;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class Utils {
    static<T> void printMatrix(ArrayList<ArrayList<T>> mat) {
        final int n = mat.size();
        final int m = mat.get(0).size();

        for(int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j)
                System.out.print(mat.get(i).get(j) + " ");
            System.out.print("\n");
        }

        System.out.print("\n");
    }

    /**
     * Returns a random n x m matrix of integers
     */
    static ArrayList<ArrayList<Integer>> randomIntMatrix(int n, int m) {
        ArrayList<ArrayList<Integer>>  matrix = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j< m; j++) {
                matrix.get(i).add((int) (Math.random()*10+1)); // geting numbres between 1 and 10
            }
        }

        return matrix;
    }

    static ArrayList<ArrayList<Complex>> randomComplexMatrix(int n, int m) {
        ArrayList<ArrayList<Complex>>  matrix = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j< m; j++) {
                matrix.get(i).add(new Complex((int) (Math.random()*10+1), (int) (Math.random()*10+1))); // geting numbres between 1 and 10
            }
        }

        return matrix;
    }


}
