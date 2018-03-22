package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}

	private void insereQuandoVazio(T data, RecursiveDoubleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		this.data = data;
		this.next = next;
		this.previous = previous;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			if (isEmpty()) {
				insereQuandoVazio(element, new RecursiveDoubleLinkedListImpl<>(),
						new RecursiveDoubleLinkedListImpl<>());
			} else {
				RecursiveDoubleLinkedListImpl<T> aux = (RecursiveDoubleLinkedListImpl<T>) this.next;
				aux.insertAux(element, this);
			}
		}
	}

	private void insertAux(T element, RecursiveDoubleLinkedListImpl<T> previous) {
		if (isEmpty()) {
			insereQuandoVazio(element, new RecursiveDoubleLinkedListImpl<>(), previous);
		} else {
			RecursiveDoubleLinkedListImpl<T> aux = (RecursiveDoubleLinkedListImpl<T>) this.next;
			aux.insertAux(element, this);
		}

	}

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			RecursiveDoubleLinkedListImpl<T> aux = new RecursiveDoubleLinkedListImpl<>(this.data, this.next, this);
			this.data = element;
			this.next = aux;
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty() && element != null) {
			if (this.data.equals(element)) {
				this.data = this.next.data;
				this.next = next.next;
			} else {
				this.next.remove(element);
			}
		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			this.data = next.data;
			RecursiveDoubleLinkedListImpl<T> aux = (RecursiveDoubleLinkedListImpl<T>) this.next.next;
			aux.previous = this;
			this.next = aux;
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			if (this.next.isEmpty()) {
				this.previous.next = new RecursiveDoubleLinkedListImpl<>();
			} else {
				((RecursiveDoubleLinkedListImpl<T>) this.next).removeLast();
			}
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}
