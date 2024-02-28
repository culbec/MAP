package Tasks;

import Sorters.AbstractSorter;

import java.util.Arrays;

public class SortingTask extends Task {
    private int[] numbers;
    private AbstractSorter sorter;

    public SortingTask(String id, String descriere, int[] numbers, AbstractSorter sorter) {
        super(id, descriere);
        this.numbers = numbers;
        this.sorter = sorter;
    }

    @Override
    public void execute() {
        System.out.println("Unsorted array: " + Arrays.toString(this.numbers));
        this.sorter.sort(numbers);
        System.out.println("Sorted array: " + Arrays.toString(this.numbers));
    }
}
