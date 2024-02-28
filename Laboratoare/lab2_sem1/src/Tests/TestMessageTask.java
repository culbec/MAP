package Tests;

import Tasks.MessageTask;

import java.time.LocalDateTime;

public class TestMessageTask {
    public static void main(String[] args) {
        MessageTask messageTask1 = new MessageTask("1", "Feedback lab1",
                new MessageTask.Message("1", "Feedback lab1", "Ai obtinut 9.60", "Gigi", "Ana", LocalDateTime.now()));
        MessageTask messageTask2 = new MessageTask("2", "Feedback lab1",
                new MessageTask.Message("2", "Feedback lab1", "Ai obtinut 9.10", "Gigi", "Ana", LocalDateTime.now()));
        MessageTask messageTask3 = new MessageTask("3", "Feedback lab1",
                new MessageTask.Message("3", "Feedback lab1", "Ai obtinut 9.40", "Gigi", "Ana", LocalDateTime.now()));
        MessageTask messageTask4 = new MessageTask("4", "Feedback lab1",
                new MessageTask.Message("4", "Feedback lab1", "Ai obtinut 9.30", "Gigi", "Ana", LocalDateTime.now()));
        MessageTask messageTask5 = new MessageTask("5", "Feedback lab1",
                new MessageTask.Message("5", "Feedback lab1", "Ai obtinut 9.90", "Gigi", "Ana", LocalDateTime.now()));

        MessageTask[] messageTasks = new MessageTask[5];
        messageTasks[0] = messageTask1;
        messageTasks[1] = messageTask2;
        messageTasks[2] = messageTask3;
        messageTasks[3] = messageTask4;
        messageTasks[4] = messageTask5;

        for (MessageTask messageTask : messageTasks) {
            messageTask.execute();
        }

    }
}
