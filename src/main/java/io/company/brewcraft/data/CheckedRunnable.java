package io.company.brewcraft.data;

public interface CheckedRunnable<T extends Throwable> {
    void run() throws T;
}
