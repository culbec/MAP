package Sorters;

public class MergeSorter extends AbstractSorter {

    private void mergeSort(int[] numbers, int left, int right) {
        if (left < right - 1) {
            int middle = (left + right) / 2;
            mergeSort(numbers, left, middle);
            mergeSort(numbers, middle, right);
            merge(numbers, left, middle, right);
        }
    }
    
    private void merge(int[] numbers, int left, int middle, int right) {
        int leftSize = middle - left;
        int rightSize = right - middle;

        int[] leftSide = new int[leftSize];
        int[] rightSide = new int[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftSide[i] = numbers[left + i];
        }

        for (int i = 0; i < rightSize; i++) {
            rightSide[i] = numbers[middle + i];
        }

        int leftIndex = 0, rightIndex = 0, placePos = left;

        while (leftIndex < leftSize && rightIndex < rightSize) {
            if (leftSide[leftIndex] < rightSide[rightIndex]) {
                numbers[placePos++] = leftSide[leftIndex++];
            } else {
                numbers[placePos++] = rightSide[rightIndex++];
            }
        }

        while (leftIndex < leftSize) {
            numbers[placePos++] = leftSide[leftIndex++];
        }

        while (rightIndex < rightSize) {
            numbers[placePos++] = rightSide[rightIndex++];
        }

    }

    @Override
    public void sort(int[] numbers) {
        this.mergeSort(numbers, 0, numbers.length);
    }
}
