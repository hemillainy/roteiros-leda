package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;

	@Override
	public void insert(T element) {
		if (element != null) {
			DoubleLinkedListNode<T> newLast = new DoubleLinkedListNode<>(element, new DoubleLinkedListNode<>(),
					new DoubleLinkedListNode<>());
			if (isEmpty()) {
				head = newLast;
				last = newLast;
			} else {
				newLast.previous = last;
				last.next = newLast;
				last = newLast;
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			DoubleLinkedListNode<T> newHead = new DoubleLinkedListNode<>(element, new DoubleLinkedListNode<>(),
					new DoubleLinkedListNode<>());
			newHead.next = head;
			newHead.previous = new DoubleLinkedListNode<>();
			((DoubleLinkedListNode<T>) head).previous = newHead;
			if (head.isNIL()) {
				last = newHead;
			}
			head = newHead;
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			if (this.head.getData().equals(element)) {
				this.head = this.head.next;
			} else {
				SingleLinkedListNode<T> aux = head;
				while (!aux.isNIL() && !aux.data.equals(element)) {
					aux = (DoubleLinkedListNode<T>) aux.next;
				}
				if (!aux.isNIL()) {
					((DoubleLinkedListNode<T>) aux).previous.next = aux.next;
					((DoubleLinkedListNode<T>) aux.next).previous = ((DoubleLinkedListNode<T>) aux).previous;
				}
			}
		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			setHead(head.next);
			if (isEmpty()) {
				last = (DoubleLinkedListNode<T>) head;
			}
			((DoubleLinkedListNode<T>) head).previous = new DoubleLinkedListNode<>();
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			last = last.previous;
			last.next = new DoubleLinkedListNode<>();
		}
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}
}