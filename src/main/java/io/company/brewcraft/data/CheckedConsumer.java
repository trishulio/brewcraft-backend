package io.company.brewcraft.data;

public interface CheckedConsumer<I, T extends Throwable> {
    void run(I input) throws T;
}
