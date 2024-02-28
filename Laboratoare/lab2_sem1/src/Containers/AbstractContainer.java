package Containers;

import Tasks.Task;

public class AbstractContainer {
    private static final int INIT_SIZE = 10;
    protected Task[] tasks;
    protected int size;

    public AbstractContainer() {
        this.tasks = new Task[INIT_SIZE];
        this.size = 0;
    }

    protected void resize() {
        Task[] newTasks = new Task[this.size * 2];
        System.arraycopy(this.tasks, 0, newTasks, 0, this.size);
        this.tasks = newTasks;
    }
}
