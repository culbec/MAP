import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final List<String> list = Arrays.asList("asf", "bcd", "asd", "bed", "bbb");

    private static void oneStream() {
        // stream execution steps -> each element runs on every step on the stream until it reaches a step when it stops
        list.stream().filter(item -> {
            System.out.println("filtering -> " + item);
            return item.startsWith("b");
        }).map(item -> {
            System.out.println("map -> " + item);
            return item.toUpperCase();
        }).forEach(item -> System.out.println("forEach -> " + item));
    }

    private static void parallelStream() {
        // stream that will run on multiple threads
        list.parallelStream().filter(item -> {
            System.out.println(Thread.currentThread().getName() + " filtering -> " + item);
            return item.startsWith("b");
        }).map(item -> {
            System.out.println(Thread.currentThread().getName() + " map -> " + item);
            return item.toUpperCase();
        }).forEachOrdered(item -> {
            // keeps the order from the list
            System.out.println(Thread.currentThread().getName() + " forEachOrdered -> " + item);
        });
    }

    private static void specificThreadNumber() throws ExecutionException, InterruptedException {
        // specifying a number of threads to be used
        try {
            new ForkJoinPool(2).submit(Main::parallelStream).get();
        } catch (ExecutionException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void streamReduce() {
        System.out.println(list.stream().filter(item -> item.startsWith("b")).reduce("", (a, b) -> a + b));
    }

    private static void streamReduceWithoutIdentity() {
        Optional<String> resultGood = list.stream().filter(item -> item.startsWith("b")).reduce((a, b) -> a + b);
        System.out.println(resultGood.isPresent() + " " + resultGood.get());
        Optional<String> resultEmpty = list.stream().filter(item -> item.startsWith("e")).reduce((a, b) -> a + b);
        System.out.println(resultEmpty.isPresent());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Running one stream...");
        Main.oneStream();

        System.out.println("\nRunning parallel stream...");
        Main.parallelStream();

        System.out.println("\nSpecific number of threads...");
        Main.specificThreadNumber();

        System.out.println("\nStream reduce...");
        Main.streamReduce();

        System.out.println("\nReduce without accumulator...");
        Main.streamReduceWithoutIdentity();
    }
}
