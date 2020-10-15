package io.company.brewcraft.migration;

import java.util.List;
import java.util.function.Supplier;

public interface TaskSet {

    <T> void submit(Supplier<T> supplier);

    List<Exception> getErrors();

    List<TaskResult<?>> getResults();
}
