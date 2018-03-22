package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The implementation of the algorithm must be in-place!
 */
public class GnomeSort<T extends Comparable<T>> extends AbstractSorting<T> {
   public void sort(T[] array, int leftIndex, int rightIndex) {
      if (array != null && leftIndex >= 0 && leftIndex < rightIndex && rightIndex < array.length) {
         int pivot = leftIndex + 1;
         while (pivot <= rightIndex) {
            if (array[pivot].compareTo(array[pivot - 1]) >= 0) {
               pivot++;
            } else {
               Util.swap(array, pivot, pivot - 1);
               if (pivot > 1) {
                  pivot--;
               } else {
                  pivot++;
               }
            }
         }
      }
   }
}
