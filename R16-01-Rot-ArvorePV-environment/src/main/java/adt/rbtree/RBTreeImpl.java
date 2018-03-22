package adt.rbtree;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T>implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return this.blackHeight((RBNode<T>) this.root);
	}

	private int blackHeight(RBNode<T> root) {
		int height = 0;
		RBNode<T> aux = root;
		while (!aux.getRight().isEmpty()) {
			if (aux.getColour().equals(Colour.BLACK)) {
				height = blackHeight((RBNode<T>) aux.getRight()) + 1;
			}
		}
		return height;
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		return verifyChildrenOfRedNodes((RBNode<T>) this.root);
	}

	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		boolean areRed = true;
		if (!node.isEmpty()) {
			if (node.getColour().equals(Colour.RED)) {
				Colour leftColour = ((RBNode<T>) node.getLeft()).getColour();
				Colour rightColour = ((RBNode<T>) node.getRight()).getColour();
				if (leftColour.equals(Colour.BLACK) || (rightColour.equals(Colour.BLACK))) {
					areRed = false;
				}
			}
			areRed = verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
					&& verifyChildrenOfRedNodes((RBNode<T>) node.getLeft());
		}
		return areRed;
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		int leftHeight = verifyBlackHeight((RBNode<T>) this.root.getLeft(), 0);
		int rightHeight = verifyBlackHeight((RBNode<T>) this.root.getLeft(), 0);
		if (leftHeight == rightHeight) {
			return true;
		}
		return false;
	}

	private int verifyBlackHeight(RBNode<T> node, int lenght) {
		if (node != null && !node.isEmpty()) {
			if (node.getColour().equals(Colour.BLACK)) {
				lenght++;
			}
			return Math.max(this.verifyBlackHeight((RBNode<T>) node.getLeft(), lenght),
					this.verifyBlackHeight((RBNode<T>) node.getRight(), lenght));
		}
		return lenght + 1;
	}

	@Override
	public void insert(T value) {
		RBNode<T> node = this.insert(value, (RBNode<T>) this.root, new RBNode<>());
		this.fixUpCase1(node);
	}

	private RBNode<T> insert(T element, RBNode<T> node, RBNode<T> parent) {
		RBNode<T> newNode = null;
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new RBNode<>());
			node.setRight(new RBNode<>());
			node.setParent(parent);
			node.setColour(Colour.RED);
			newNode = node;
		} else if (node.getData().compareTo(element) > 0) {
			newNode = insert(element, (RBNode<T>) node.getLeft(), node);
		} else {
			newNode = insert(element, (RBNode<T>) node.getRight(), node);
		}
		return newNode;
	}

	@Override
	public RBNode<T>[] rbPreOrder() {
		List<RBNode<T>> arrayAux = new ArrayList<>();
		this.rbPreOrder(arrayAux, (RBNode<T>) this.root);
		RBNode<T>[] rbArray = new RBNode[arrayAux.size()];
		return arrayAux.toArray(rbArray);
	}

	private void rbPreOrder(List<RBNode<T>> array, RBNode<T> node) {
		if (!node.isEmpty()) {
			array.add(node);
			this.rbPreOrder(array, (RBNode<T>) node.getLeft());
			this.rbPreOrder(array, (RBNode<T>) node.getRight());
		}
	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (node.equals(this.root)) {
			node.setColour(Colour.BLACK);
		} else {
			fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		if (!((RBNode<T>) node.getParent()).getColour().equals(Colour.BLACK)) {
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> uncle = (RBNode<T>) null;
		RBNode<T> grand = (RBNode<T>) parent.getParent();
		if (grand.getLeft().equals(parent)) {
			uncle = (RBNode<T>) grand.getRight();
		} else {
			uncle = (RBNode<T>) grand.getLeft();
		}
		if (uncle.getColour().equals(Colour.RED)) {
			parent.setColour(Colour.BLACK);
			uncle.setColour(Colour.BLACK);
			grand.setColour(Colour.RED);
			this.fixUpCase1(grand);
		} else {
			fixUpCase4(node);
		}
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> rightChild = (RBNode<T>) parent.getRight();
		RBNode<T> leftChild = (RBNode<T>) parent.getLeft();
		if (node.equals(rightChild) && parent.equals(leftChild)) {
			Util.leftRotation(parent);
			next = (RBNode<T>) node.getLeft();
		} else if (node.equals(leftChild) && parent.equals(rightChild)) {
			Util.rightRotation(parent);
			next = (RBNode<T>) node.getRight();
		}
		fixUpCase5(next);
	}

	protected void fixUpCase5(RBNode<T> node) {
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grand = (RBNode<T>) parent.getParent();
		parent.setColour(Colour.BLACK);
		grand.setColour(Colour.RED);
		if (node.equals(parent.getLeft())) {
			Util.rightRotation(grand);
		} else {
			Util.leftRotation(grand);
		}
	}
}
