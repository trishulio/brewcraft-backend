package io.company.brewcraft.data;

public interface CheckedSupplier<R, I, T extends Throwable> {
    R get(I input) throws T;
}