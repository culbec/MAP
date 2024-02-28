package Tests;

import Runner.StrategyTaskRunner;
import Runner.TaskRunner;
import Strategies.Strategy;
import Tasks.MessageTask;

import java.time.LocalDateTime;

public class TestRunner {
    protected static final MessageTask[] messageTasks = {
            new MessageTask("1", "nota lab",
                    new MessageTask.Message("1", "Nota lab", "Ai primit nota 9.60", "Gigi", "Ana", LocalDateTime.now())),
            new MessageTask("2", "nota lab",
                    new MessageTask.Message("1", "Nota lab", "Ai primit nota 9.50", "Gigi", "Aura", LocalDateTime.now())),
            new MessageTask("3", "nota lab",
                    new MessageTask.Message("1", "Nota lab", "Ai primit nota 4.60", "Gigi", "Mirel", LocalDateTime.now()))
    };

    public static void main(String[] args) {
        Strategy strategy = Strategy.valueOf(args[0]);

        TaskRunner taskRunner = new StrategyTaskRunner(strategy);

        for (MessageTask messageTask : messageTasks) {
            taskRunner.addTask(messageTask);
        }

        System.out.println("Testing the LIFO of task runner...");
        System.out.println("\nExecuting one task...");
        taskRunner.executeOneTask();

        System.out.println("\nExecuting the remaining tasks...");
        taskRunner.executeAll();
    }
}
