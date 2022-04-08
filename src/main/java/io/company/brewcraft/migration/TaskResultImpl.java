package io.company.brewcraft.migration;

public class TaskResultImpl<T> implements TaskResult<T> {
    private T returnValue;

    public TaskResultImpl(T returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public T getReturnValue() {
        return returnValue;
    }
}
