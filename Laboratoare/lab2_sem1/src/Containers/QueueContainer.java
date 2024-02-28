package Containers;

import Tasks.Task;

public class QueueContainer extends AbstractContainer implements Container {
    public QueueContainer() {
        super();
    }

    @Override
    public void add(Task task) {
        if (this.size == this.tasks.length) {
            this.resize();
        }

        this.tasks[this.size++] = task;
    }

    @Override
    public Task remove() throws IndexOutOfBoundsException {
        if (this.isEmpty()) {
            throw new IndexOutOfBoundsException("Queue is empty!");
        }

        Task task = this.tasks[0];

        System.arraycopy(this.tasks, 1, this.tasks, 0, --this.size);

        return task;
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
