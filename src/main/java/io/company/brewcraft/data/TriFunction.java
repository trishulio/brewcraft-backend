package io.company.brewcraft.data;

public interface TriFunction<R, X, Y, Z> {
    R apply(X x, Y y, Z z);
}
