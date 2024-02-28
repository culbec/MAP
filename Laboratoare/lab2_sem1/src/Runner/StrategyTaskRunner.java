package Runner;

import Containers.Container;
import Factories.TaskContainerFactory;
import Strategies.Strategy;
import Tasks.Task;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;

    public StrategyTaskRunner(Strategy strategy) {
        this.container = TaskContainerFactory.getInstance().createContainer(strategy);
    }

    @Override
    public void executeOneTask() {
        if (this.hasTask()) {
            this.container.remove().execute();
        }
    }

    @Override
    public void executeAll() {
        while (this.hasTask()) {
            this.container.remove().execute();
        }
    }

    @Override
    public void addTask(Task t) {
        this.container.add(t);
    }

    @Override
    public boolean hasTask() {
        return !this.container.isEmpty();
    }

}
