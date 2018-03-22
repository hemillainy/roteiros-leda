package adt.linkedList;

import java.util.Arrays;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return head.isNIL();
	}

	@Override
	public int size() {
		int saida = 0;
		if (!isEmpty()) {
			SingleLinkedListNode<T> aux = head;
			while (!aux.isNIL()) {
				aux = aux.next;
				saida++;
			}
		}
		return saida;
	}

	@Override
	public T search(T element) {
		T saida = null;
		if (!isEmpty()) {
			SingleLinkedListNode<T> aux = head;
			while (!aux.isNIL()) {
				if (aux.data.equals(element)) {
					saida = element;
				}
				aux = aux.next;
			}
		}
		return saida;
	}

	@Override
	public void insert(T element) {
		SingleLinkedListNode<T> newNode = new SingleLinkedListNode<>(element, new SingleLinkedListNode<>());
		if (isEmpty()) {
			head = newNode;
		} else {
			SingleLinkedListNode<T> auxNode = head;
			while (!auxNode.next.isNIL()) {
				auxNode = auxNode.next;
			}
			auxNode.next = newNode;
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			SingleLinkedListNode<T> auxNode = head;
			SingleLinkedListNode<T> previous = head;
			while (!auxNode.isNIL() && !auxNode.data.equals(element)) {
				previous = auxNode;
				auxNode = auxNode.next;
			}
			if (!auxNode.isNIL()) {
				previous.next = auxNode.next;
			}
		}
	}

	@Override
	public T[] toArray() {
		T[] saida = (T[]) new Object[size()];
		if (!isEmpty()) {
			SingleLinkedListNode<T> aux = head;
			int index = 0;
			while (!aux.isNIL()) {
				saida[index++] = aux.data;
				aux = aux.next;
			}
		}
		return saida;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
