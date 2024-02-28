package Containers;

import Tasks.Task;

public class StackContainer extends AbstractContainer implements Container {
    public StackContainer() {
        super();
    }

    @Override
    public Task remove() throws IndexOutOfBoundsException {
        if (this.isEmpty()) {
            throw new IndexOutOfBoundsException("Stack is empty.");
        }

        return this.tasks[--this.size];
    }

    @Override
    public void add(Task task) {
        if (this.size == this.tasks.length) {
            this.resize();
        }

        this.tasks[this.size++] = task;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
}
