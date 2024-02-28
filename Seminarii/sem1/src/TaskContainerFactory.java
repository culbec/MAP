public class TaskContainerFactory implements  Factory {

    // TODO: SINGLETON

    @Override
    public Container createContainer(ContainerStrategy containerStrategy) {
        return switch (containerStrategy) {
            case FIFO -> new StackContainer();
            case LIFO -> new StackContainer(); // TODO: replace with QueueContainer when available
            default -> null;
        };
    }
}
