import java.util.List;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;



/**
 * Your implementation of various sorting algorithms.
 *
 * @author Jadon Co
 * @version 1.0
 * @userid jco9
 * @GTID 903725118
 *
 * Collaborators: LIST ALL COLLABORATORS YOU
 * WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {


        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Either the array or comparator is null");
        }
        for (int i = 0; i < arr.length; i++) {
            T temp = arr[i];
            int j = i - 1;

            while (j >= 0 && comparator.compare(arr[j], temp) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }


    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("The array or comparator are null");
        }

        int startInd = 0;
        int endInd = arr.length - 1;

        while (endInd > startInd) {
            int swapped = startInd;
            for (int i = startInd; i < endInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = i;
                }
            }
            endInd = swapped;
            for (int i = endInd; i > startInd; i--) {
                if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                    T temp = arr[i - 1];
                    arr[i - 1] = arr[i];
                    arr[i] = temp;
                    swapped = i;
                }
            }
            startInd = swapped;
        }

    }


    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Either the array or comparator is null");
        }

        if (arr.length <= 1) {
            return;
        }
        int length = arr.length;
        int midIndex = (length / 2);
        T[] leftArray = ((T[]) new Object[midIndex]);
        for (int i = 0; i < midIndex; i++) {
            leftArray[i] = arr[i];
        }
        T[] rightArray = ((T[]) new Object[(length - midIndex)]);
        for (int i = midIndex; i < length; i++) {
            rightArray[i - midIndex] = arr[i];
        }

        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);

        int left = 0;
        int right = 0;
        int curr = 0;

        while (left < midIndex && right < (length - midIndex)) {
            if (comparator.compare(leftArray[left], rightArray[right]) < 0) {
                arr[curr] = leftArray[left];
                left++;
            } else {
                arr[curr] = rightArray[right];
                right++;
            }
            curr++;
        }

        while (left < midIndex) {
            arr[curr] = leftArray[left];
            curr++;
            left++;
        }
        while (right < (length - midIndex)) {
            arr[curr] = rightArray[right];
            curr++;
            right++;
        }






    }


    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {

        if (arr == null || comparator == null || rand == null) {
            throw new java.lang.IllegalArgumentException("Either the array, the comparator, or rand is null");
        }

        partition(arr, comparator, 0, arr.length - 1, rand);



    }

    /**
     *
     * @param arr the array to pass in to
     * @param comparator the comparator to check the values
     * @param i the left index
     * @param j the right index
     * @param rand the random class that allows you to find the random pivotindex
     * @param <T> the generic for the array values
     */
    private static <T> void partition(T[] arr, Comparator<T> comparator, int i, int j, Random rand) {

        if (i >= j - 1) {
            return;
        }


        int leftIndex = i + 1;
        int rightIndex = j;

        int pivotIndex = rand.nextInt(j - i) + i;


        T pivotValue = arr[pivotIndex];
        arr[pivotIndex] = arr[i];
        arr[i] = pivotValue;



        while (leftIndex < rightIndex) {
            while (leftIndex <= rightIndex && comparator.compare(arr[leftIndex], pivotValue) < 0) {
                leftIndex++;
            }
            while (leftIndex <= rightIndex && comparator.compare(arr[rightIndex], pivotValue) > 0) {
                rightIndex--;
            }
            if (leftIndex <= rightIndex) {
                T tmp = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = tmp;

                leftIndex++;
                rightIndex--;
            }
        }
        T switchValue = arr[rightIndex];
        arr[rightIndex] = arr[i];
        arr[i] = switchValue;

        partition(arr, comparator, i, rightIndex - 1, rand);
        partition(arr, comparator, rightIndex + 1, j, rand);

    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {

        if (arr == null) {
            throw new java.lang.IllegalArgumentException("The array is null");
        }
        if (arr.length <= 1) {
            return;
        }
        int max = arr[0];
        int min = arr[0];
        int length = arr.length;

        for (int i = 0; i < arr.length; i++)    {
            if (arr[i] < min) {
                min = arr[i];
            } else if (arr[i] > max)   {
                max = arr[i];
            }
        }

        LinkedList[] buckets =  new LinkedList[19];



        int times = 0;
        while (max != 0 || min != 0)   {
            max = max / 10;
            min = min / 10;
            times++;
        }
        int startingExponent = 1;
        for (int i = 0; i < times; i++)    {



            for (int j = 0; j < length; j++)    {

                //calculating number of times for buckets
                int num = (arr[j] / startingExponent) % 10;
                num += 9;

                //creates new linked list if empty
                if (buckets[num] == null)    {
                    buckets[num] = new LinkedList<Integer>();
                }
                //adds array value to the linked list
                buckets[num].add(arr[j]);
            }
            int idx = 0;
            for (int s = 0; s < 19; s++)    {
                //creates new linked list at location if it is null
                if (buckets[s] == null)    {
                    buckets[s] = new LinkedList<Integer>();
                }

                //while it isnt empty, take values out of linked list and put in the array
                while (!buckets[s].isEmpty())  {
                    arr[idx] = ((int) buckets[s].removeFirst());
                    idx++;
                }
            }
            //increases exponent accordingly
            startingExponent =  startingExponent * 10;
        }



    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data list is null");
        }
        PriorityQueue<Integer> queue = new PriorityQueue(data);
        int[] sorted = new int[data.size()];

        for (int i = 0; i < data.size(); i++) {
            sorted[i] = queue.remove();
        }

        return sorted;
    }
}
