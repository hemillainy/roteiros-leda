package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
		this.initiateInternalTable(size);
	}

	private int getHash(T element, int probe) {
		int hash = ((HashFunctionOpenAddress<T>) this.getHashFunction()).hash(element, probe);
		return hash;
	}

	@Override
	public void insert(T element) {
		if (isFull()) {
			throw new HashtableOverflowException();
		}
		if (element != null) {
			int probe = 0;
			int index = getHash(element, probe);
			boolean hasElement = false;// variavel q sera usada para evitar q
										// seja adicionado dois elementos iguais
			while (table[index] != null && table[index] != deletedElement && !hasElement && probe < table.length) {
				index = getHash(element, ++probe);
				COLLISIONS++;
				if (table[index] != null && table[index].equals(element)) {
					hasElement = true;
				}
			}
			if (!hasElement) {
				if (table[index] == null || table[index] == deletedElement) {
					table[index] = element;
					elements++;
				}
			}
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty() && element != null) {
			int probe = 0;
			int index = indexOf(element);
			if (index != -1) {
				if (table[index] != null && table[index] != deletedElement && probe < table.length
						&& table[index].equals(element)) {
					table[index] = deletedElement;
					elements--;
				}
			}
		}
	}

	@Override
	public T search(T element) {
		T saida = null;
		if (!isEmpty() && element != null) {
			int probe = 0;
			int index = getHash(element, probe);
			while (table[index] != null && table[index] != deletedElement && probe < table.length && saida == null) {
				if (table[index].equals(element)) {
					saida = element;
				} else {
					index = getHash(element, ++probe);
				}
			}
		}
		return saida;
	}

	@Override
	public int indexOf(T element) {
		int index = -1;
		if (element != null) {
			int probe = 0;
			index = getHash(element, probe);
			while (table[index] != null && probe < table.length && !table[index].equals(element)) {
				index = getHash(element, ++probe);
			}
			if (table[index] == null || probe == table.length || !table[index].equals(element)) {
				index = -1;
			}
		}
		return index;
	}
}
