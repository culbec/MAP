package Factories;

import Containers.Container;
import Containers.QueueContainer;
import Containers.StackContainer;
import Strategies.Strategy;

public class TaskContainerFactory implements Factory {
    private static final TaskContainerFactory instance = new TaskContainerFactory();

    private TaskContainerFactory() {
    }

    ;

    public static TaskContainerFactory getInstance() {
        return instance;
    }

    @Override
    public Container createContainer(Strategy strategy) {
        return switch (strategy) {
            case LIFO -> new StackContainer();
            case FIFO -> new QueueContainer();
        };
    }
}
