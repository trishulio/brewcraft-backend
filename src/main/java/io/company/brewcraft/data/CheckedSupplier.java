package io.company.brewcraft.data;

public interface CheckedSupplier<R, I, T extends Throwable> {
    R run(I input) throws T;
}