package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.
	@Override
	public void insert(T element) {
		insert(this.root, element, new BSTNode<T>());
	}

	private void insert(BSTNode<T> node, T element, BSTNode<T> parent) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<>());
			node.setRight(new BSTNode<>());
			node.setParent(parent);
		} else {
			if (element.compareTo(node.getData()) < 0) {
				insert((BSTNode<T>) node.getLeft(), element, node);
			} else {
				insert((BSTNode<T>) node.getRight(), element, node);
			}
			rebalance(node);
		}
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		if (!node.isEmpty()) {
			if (node.isLeaf()) {
				node.setData(null);
				this.rebalanceUp((BSTNode<T>) node.getParent());
			} else if (childrens(this.root) == 1) {
				if (!node.equals(this.root)) {
					if (node.equals(this.root.getLeft())) {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setRight(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setRight(node.getRight());
							node.getLeft().setParent(node.getParent());
						}
					} else {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setLeft(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setLeft(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					}
				} else {
					if (!node.getLeft().isEmpty()) {
						root = (BSTNode<T>) node.getRight();
					} else {
						root = (BSTNode<T>) node.getLeft();
					}
					this.root.setParent(null);
				}
				this.rebalanceUp(node);
			} else {
				T dataSucessor = sucessor(node.getData()).getData();
				remove(dataSucessor);
				node.setData(dataSucessor);
			}
		}
	}

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		int balance = 0;
		if (!node.isEmpty()) {
			balance = height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
		}
		return balance;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		int balance = calculateBalance(node);
		if (Math.abs(balance) > 1) {
			BSTNode<T> aux;
			if (balance < 0) {
				if (calculateBalance((BSTNode<T>) node.getRight()) > 0) {
					node.setRight(Util.rightRotation((BSTNode<T>) node.getRight()));
				}
				aux = Util.leftRotation((BSTNode<T>) node);
			} else {
				if (calculateBalance((BSTNode<T>) node.getLeft()) < 0) {
					node.setLeft(Util.leftRotation((BSTNode<T>) node.getLeft()));
				}
				aux = Util.rightRotation(node);
			}
			if (this.root.equals(node)) {
				this.root = aux;
			} else {
				if (aux.getParent().getLeft().equals(node)) {
					aux.getParent().setLeft(aux);
				} else {
					aux.getParent().setRight(aux);
				}

			}
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		// BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (node != null) {
			rebalance(node);
			node = (BSTNode<T>) node.getParent();
		}
	}
}
