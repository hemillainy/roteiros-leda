package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm.
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (array.length != 0 && array != null && leftIndex >= 0 && leftIndex < rightIndex
				&& rightIndex < array.length) {
			int gap = rightIndex - leftIndex;
			boolean trocou = true;
			while (gap > 1 || trocou) {
				if (gap > 1) {
					gap = (int) (gap / 1.25);
				}
				int i = leftIndex;
				trocou = false;
				while (i + gap < rightIndex + 1) {
					if (array[i].compareTo(array[i + gap]) > 0) {
						Util.swap(array, i, i + gap);
						trocou = true;
					}
					i += 1;
				}
			}
		}
	}
}
