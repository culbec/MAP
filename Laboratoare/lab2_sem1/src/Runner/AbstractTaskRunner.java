package Runner;

import Tasks.Task;

public class AbstractTaskRunner implements TaskRunner {
    private final TaskRunner taskRunner;

    public AbstractTaskRunner(TaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }

    @Override
    public void executeOneTask() {
        if(this.hasTask()) {
            this.taskRunner.executeOneTask();
        }
    }

    @Override
    public void executeAll() {
        while (this.hasTask()) {
            this.executeOneTask();
        }
    }

    @Override
    public void addTask(Task task) {
        this.taskRunner.addTask(task);
    }

    @Override
    public boolean hasTask() {
        return this.taskRunner.hasTask();
    }
}
