package Tests;

import Containers.QueueContainer;
import Containers.StackContainer;
import Tasks.MessageTask;
import Tasks.Task;

import java.time.LocalDateTime;

public class TestContainers {
    public static void main(String[] args) {
        // Testing the Containers.StackContainer
        StackContainer stackContainer = new StackContainer();

        Task task = new MessageTask("1", "tema", new MessageTask.Message("1", "aa", "bb", "aa", "bb", LocalDateTime.now()));

        System.out.println("STACK CONTAINER\n");

        // checking the size and isEmpty methods
        System.out.println("Is the stack empty? " + stackContainer.isEmpty());
        System.out.println("Stack size: " + stackContainer.size());

        // testing empty stack exception
        try {
            stackContainer.remove();
        } catch (IndexOutOfBoundsException iOOBE) {
            System.out.println(iOOBE.getMessage());
        }

        // checking the remove method
        stackContainer.add(task);
        Task removed = stackContainer.remove();
        System.out.println("Removed task: " + removed);

        // checking if the array resizes
        for (int i = 0; i < 11; i++) {
            stackContainer.add(task);
        }

        // checking the size and isEmpty methods
        System.out.println("Is the stack empty? " + stackContainer.isEmpty());
        System.out.println("Stack size: " + stackContainer.size());

        for (int i = 0; i < 11; i++) {
            stackContainer.remove().execute();
        }

        System.out.println("Stack size: " + stackContainer.size());

        // Testing the Containers.QueueContainer
        QueueContainer queueContainer = new QueueContainer();

        System.out.println("\nQUEUE CONTAINER\n");

        System.out.println("Is the queue empty? " + queueContainer.isEmpty());
        System.out.println("Queue size: " + queueContainer.size());

        try {
            queueContainer.remove();
        } catch (IndexOutOfBoundsException iOOBE) {
            System.out.println(iOOBE.getMessage());
        }

        queueContainer.add(task);
        System.out.println("Removed task: " + queueContainer.remove());

        for (int i = 0; i < 11; i++) {
            queueContainer.add(task);
        }

        System.out.println("Is the queue empty? " + queueContainer.isEmpty());
        System.out.println("Queue size: " + queueContainer.size());

        for (int i = 0; i < 11; i++) {
            queueContainer.remove().execute();
        }

        System.out.println("Queue size: " + queueContainer.size());
    }
}
