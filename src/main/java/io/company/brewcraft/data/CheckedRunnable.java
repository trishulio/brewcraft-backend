package io.company.brewcraft.data;

public interface CheckedRunnable<I, T extends Throwable> {
    void run(I input) throws T;
}
