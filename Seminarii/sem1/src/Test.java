import java.time.LocalDateTime;
import java.util.Scanner;

public class Test {
    public static void doTestMessageTask() {
        Scanner scanner = new Scanner(System.in);
        MessageTask[] messageTasks = new MessageTask[5];
        LocalDateTime dateTime = LocalDateTime.now();

        for (int i = 0; i < 5; i++) {
            System.out.println("Dati descrierea: ");
            String desc = scanner.nextLine();
            System.out.println("Dati mesajul: ");
            String mes = scanner.nextLine();
            System.out.println("Dati expeditor: ");
            String from = scanner.nextLine();
            System.out.println("Dati destinatar: ");
            String to = scanner.nextLine();

            MessageTask newTask = new MessageTask(Integer.toString(i), desc, mes, from, to, dateTime);
            messageTasks[i] = newTask;
        }

        for (MessageTask messageTask : messageTasks) {
            System.out.println(messageTask);
        }
    }

    public static void doTestTestRunner() {
        MessageTask messageTask1 = new MessageTask("1", "Mesaj important 1", "Muie", "Tudor", "George", LocalDateTime.now());
        MessageTask messageTask2 = new MessageTask("2", "Mesaj important 2", "Muie", "Tudor", "George", LocalDateTime.now());
        MessageTask messageTask3 = new MessageTask("3", "Mesaj important 3", "Muie", "Tudor", "George", LocalDateTime.now());
        MessageTask messageTask4 = new MessageTask("4", "Mesaj important 4", "Muie", "Tudor", "George", LocalDateTime.now());
        MessageTask messageTask5 = new MessageTask("5", "Mesaj important 5", "Muie", "Tudor", "George", LocalDateTime.now());

        MessageTask[] messageTasks = new MessageTask[]{messageTask1, messageTask2, messageTask3, messageTask4, messageTask5};

        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(ContainerStrategy.LIFO);

        for(MessageTask messageTask: messageTasks) {
            strategyTaskRunner.addTask(messageTask);
        }

        System.out.println(strategyTaskRunner.hasTask());

        strategyTaskRunner.executeOneTask();

        strategyTaskRunner.executeAll();
    }
}
