package Containers;

import Tasks.Task;

public interface Container {
    Task remove() throws IndexOutOfBoundsException;

    void add(Task task);

    int size();

    boolean isEmpty();
}
