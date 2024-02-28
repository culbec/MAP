import java.util.ArrayList;
import java.util.List;

public class StackContainer implements Container {
    private List<Task> tasks = new ArrayList<Task>();

    @Override
    public Task remove() {
        if(this.tasks.isEmpty()) {
            return null;
        }
        return this.tasks.remove(this.tasks.size() - 1);
    }

    @Override
    public void add(Task task) {
        this.tasks.add(task);
    }

    @Override
    public int size() {
        return this.tasks.size();
    }

    @Override
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }
}
