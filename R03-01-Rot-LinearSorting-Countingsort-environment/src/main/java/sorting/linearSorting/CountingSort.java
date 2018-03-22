package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

   @Override
   public void sort(Integer[] array, int leftIndex, int rightIndex) {
      if (array != null && leftIndex < rightIndex && leftIndex >= 0 && rightIndex < array.length && array.length != 0) {

         int maior = 0;
         for (int i = leftIndex; i < rightIndex + 1; i++) {
            if (array[i] > maior) {
               maior = array[i];
            }
         }
         Integer[] arrayTemp = new Integer[maior + 1];
         for (int i = 0; i < arrayTemp.length; i++) {
            arrayTemp[i] = 0;
         }

         for (int i = 0; i < rightIndex + 1; i++) {
            arrayTemp[array[i]]++;
         }
         for (int i = 1; i < arrayTemp.length; i++) {
            arrayTemp[i] = arrayTemp[i] + arrayTemp[i - 1];
         }
         int[] arrayAux = new int[array.length];
         for (int i = rightIndex; i >= 0; i--) {
            arrayAux[arrayTemp[array[i]] - 1] = array[i];
            arrayTemp[array[i]]--;
         }
         for (int i = 0; i < array.length; i++) {
            array[i] = arrayAux[i];
         }
      }
   }

}
