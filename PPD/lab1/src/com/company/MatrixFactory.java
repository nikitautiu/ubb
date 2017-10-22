package com.company;

import java.util.ArrayList;

@FunctionalInterface
public interface MatrixFactory<T> {
    ArrayList<ArrayList<T>> randomMatrix(int n, int m);
}
