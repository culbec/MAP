package Sorters;

public class QuickSorter extends AbstractSorter {

    private int partition(int[] numbers, int left, int right) {
        int pivotElem = numbers[left];
        int position = left;

        for (int i = left + 1; i < right; i++) {
            if (numbers[i] < pivotElem) {
                int aux = numbers[i];
                numbers[i] = numbers[position];
                numbers[position] = aux;
                position++;
            }
        }

        return position;
    }

    private void quickSort(int[] numbers, int left, int right) {
        if (left < right) {
            int pivot = partition(numbers, left, right);

            quickSort(numbers, left, pivot);
            quickSort(numbers, pivot + 1, right);
        }
    }

    @Override
    public void sort(int[] numbers) {
        this.quickSort(numbers, 0, numbers.length);
    }
}
