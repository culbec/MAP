public class StrategyTaskRunner implements TaskRunner {
    private Container container;

    StrategyTaskRunner(ContainerStrategy containerStrategy) {
        this.container = new TaskContainerFactory().createContainer(containerStrategy);
    }

    @Override
    public void executeOneTask() {
        this.container.remove().execute();
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
        return !container.isEmpty();
    }
}
