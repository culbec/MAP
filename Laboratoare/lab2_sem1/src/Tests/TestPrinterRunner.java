package Tests;

import Runner.PrinterTaskRunner;
import Runner.StrategyTaskRunner;
import Strategies.Strategy;
import Tasks.MessageTask;

public class TestPrinterRunner extends TestRunner {
    public static void main(String[] args) {
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(Strategy.valueOf(args[0]));

        for (MessageTask messageTask : messageTasks) {
            strategyTaskRunner.addTask(messageTask);
        }

        System.out.println("Executing via StrategyTaskRunner...");
        strategyTaskRunner.executeAll();

        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(strategyTaskRunner);

        for (MessageTask messageTask : messageTasks) {
            printerTaskRunner.addTask(messageTask);
        }

        System.out.println("\nExecuting via PrinterTaskRunner...");
        printerTaskRunner.executeAll();
    }

}
