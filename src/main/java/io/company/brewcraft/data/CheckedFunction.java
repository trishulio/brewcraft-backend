package io.company.brewcraft.data;

public interface CheckedFunction<R, I, T extends Throwable> {
    R apply(I input) throws T;
}
