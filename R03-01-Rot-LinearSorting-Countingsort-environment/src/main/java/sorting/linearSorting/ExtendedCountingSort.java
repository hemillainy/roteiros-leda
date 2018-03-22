package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex && array != null && leftIndex >= 0 && rightIndex < array.length && rightIndex > 0) {
			int maiorNum = array[leftIndex];
			int menorNum = array[leftIndex];

			for (int i = leftIndex; i <= rightIndex; i++) {
				if (array[i] > maiorNum) {
					maiorNum = array[i];
				}
				if (array[i] < menorNum) {
					menorNum = array[i];
				}
			}
			Integer[] arrayAux = new Integer[maiorNum - menorNum + 1];

			for (int i = 0; i < arrayAux.length; i++) {
				arrayAux[i] = 0;
			}

			for (int i = leftIndex; i <= rightIndex; i++) {
				arrayAux[array[i] - menorNum]++;
			}

			int i = leftIndex;
			int j = leftIndex;

			while (i < arrayAux.length) {
				while (arrayAux[i] != 0) {
					array[j] = i + menorNum;
					j++;
					arrayAux[i]--;
				}
				i++;
			}
		}

	}

}
