package adt.bst;

import java.util.ArrayList;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BSTNode<T> node) {
		int height = -1;
		if (!node.isEmpty()) {
			int left = height((BSTNode<T>) node.getLeft());
			int right = height((BSTNode<T>) node.getRight());
			height = Math.max(left, right) + 1;
		}
		return height;
	}

	@Override
	public BSTNode<T> search(T element) {
		return search(this.root, element);
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		BSTNode<T> exit = null;
		if (element != null) {
			if (node.isEmpty() || node.getData().compareTo(element) == 0) {
				exit = node;
			} else if (node.getData().compareTo(element) > 0) {
				exit = search((BSTNode<T>) node.getLeft(), element);
			} else {
				exit = search((BSTNode<T>) node.getRight(), element);
			}
		}
		return exit;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			insert(element, this.root, new BSTNode<>());
		}
	}

	private void insert(T element, BSTNode<T> node, BSTNode<T> parent) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<>());
			node.setRight(new BSTNode<>());
			node.setParent(parent);
		} else if (node.getData().compareTo(element) > 0) {
			insert(element, (BSTNode<T>) node.getLeft(), node);
		} else {
			insert(element, (BSTNode<T>) node.getRight(), node);
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return maximum(this.root);
	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		BSTNode<T> exit = null;
		BSTNode<T> aux = node;
		while (!aux.isEmpty()) {
			exit = aux;
			aux = (BSTNode<T>) aux.getRight();
		}
		return exit;
	}

	@Override
	public BSTNode<T> minimum() {
		return minimun(this.root);
	}

	private BSTNode<T> minimun(BSTNode<T> node) {
		BSTNode<T> exit = null;
		BSTNode<T> aux = node;
		while (!aux.isEmpty()) {
			exit = aux;
			aux = (BSTNode<T>) aux.getLeft();
		}
		return exit;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> exit = null;
		if (element != null) {
			BSTNode<T> node = search(element);
			if (!node.isEmpty()) {
				if (!node.getRight().isEmpty()) {
					exit = minimun((BSTNode<T>) node.getRight());
				} else {
					exit = (BSTNode<T>) node.getParent();
					while (!exit.isEmpty() && node.equals(exit.getRight())) {
						node = exit;
						exit = (BSTNode<T>) exit.getParent();
					}
				}
			}
		}
		return exit;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> exit = null;
		if (element != null) {
			BSTNode<T> node = search(element);
			if (!node.isEmpty() && !node.equals(this.minimum())) {
				if (!node.getLeft().isEmpty()) {
					exit = maximum((BSTNode<T>) node.getLeft());
				} else {
					exit = (BSTNode<T>) node.getParent();
					while (!exit.isEmpty() && node.equals(exit.getLeft())) {
						node = exit;
						exit = (BSTNode<T>) exit.getParent();
					}
				}
			}
		}
		return exit;
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		if (node != null && !node.isEmpty()) {
			if (node.isLeaf()) {
				node.setData(null);
				node.setLeft(null);
				node.setRight(null);
				node.setParent(null);
			} else if (childrens(this.root) == 1) {
				if (!node.equals(this.root)) {
					if (node.equals(this.root.getLeft())) {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setLeft(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setLeft(node.getRight());
							node.getRight().setParent(node.getParent());
							;
						}
					} else {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setRight(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setRight(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					}
				} else {
					T data = null;
					if (!node.getLeft().isEmpty()) {
						data = maximum((BSTNode<T>) node.getLeft()).getData();
					} else {
						data = minimun((BSTNode<T>) node.getRight()).getData();
					}
					remove(data);
					this.root.setData(data);
				}
			} else {
				BSTNode<T> sucessor = sucessor(node.getData());
				T data = sucessor.getData();
				remove(data);
				node.setData(data);
			}
		}
	}

	private int childrens(BSTNode<T> node) {
		int exit = 0;
		if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
			exit = 2;
		} else if ((!node.getLeft().isEmpty() && node.getRight().isEmpty())
				|| (node.getLeft().isEmpty() && !node.getRight().isEmpty())) {
			exit = 1;
		}
		return exit;
	}

	@Override
	public T[] preOrder() {
		ArrayList<Comparable<T>> array = new ArrayList<>();
		preOrder(array, this.root);
		return (T[]) array.toArray(new Comparable[size()]);
	}

	private void preOrder(ArrayList<Comparable<T>> array, BSTNode<T> node) {
		if (!node.isEmpty()) {
			array.add(node.getData());
			preOrder(array, (BSTNode<T>) node.getLeft());
			preOrder(array, (BSTNode<T>) node.getRight());
		}
	}

	@Override
	public T[] order() {
		ArrayList<Comparable> array = new ArrayList<Comparable>();
		order(array, this.root);
		return (T[]) array.toArray(new Comparable[size()]);
	}

	private void order(ArrayList<Comparable> array, BSTNode<T> node) {
		if (!node.isEmpty()) {
			order(array, (BSTNode<T>) node.getLeft());
			array.add(node.getData());
			order(array, (BSTNode<T>) node.getRight());
		}
	}

	@Override
	public T[] postOrder() {
		ArrayList<Comparable<T>> array = new ArrayList<>();
		postOrder(array, this.root);
		return (T[]) array.toArray(new Comparable[size()]);
	}

	private void postOrder(ArrayList<Comparable<T>> array, BSTNode<T> node) {
		if (!node.isEmpty()) {
			postOrder(array, (BSTNode<T>) node.getLeft());
			postOrder(array, (BSTNode<T>) node.getRight());
			array.add(node.getData());
		}
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
