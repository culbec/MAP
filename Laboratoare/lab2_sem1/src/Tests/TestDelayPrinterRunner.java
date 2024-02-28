package Tests;

import Runner.DelayTaskRunner;
import Runner.PrinterTaskRunner;
import Runner.StrategyTaskRunner;
import Runner.TaskRunner;
import Strategies.Strategy;
import Tasks.MessageTask;

public class TestDelayPrinterRunner extends TestRunner {
    public static void main(String[] args) {
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(Strategy.valueOf(args[0]));
        for (MessageTask messageTask : messageTasks) {
            strategyTaskRunner.addTask(messageTask);
        }
        System.out.println("Executing tasks via StrategyTaskRunner...");
        strategyTaskRunner.executeAll();

        TaskRunner printerDelayer = new PrinterTaskRunner(new DelayTaskRunner(strategyTaskRunner));
        for (MessageTask messageTask : messageTasks) {
            printerDelayer.addTask(messageTask);
        }

        System.out.println("\nExecuting via PrinterDelayer...");
        printerDelayer.executeAll();
    }
}
