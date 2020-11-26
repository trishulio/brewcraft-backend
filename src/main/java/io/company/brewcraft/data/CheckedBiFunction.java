package io.company.brewcraft.data;

public interface CheckedBiFunction<R, I1, I2, T extends Throwable> {
    R apply(I1 i1, I2 i2) throws T;
}
