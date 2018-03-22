package adt.skipList.fromBST;

import adt.bst.BST;
import adt.bst.BSTNode;
import adt.skipList.SkipListImpl;

public class SkipListFromBSTImpl extends SkipListImpl<Integer> implements SkipListFromBST<Integer> {

	public SkipListFromBSTImpl(int maxHeight) {
		super(maxHeight);
	}

	public void importFromBST(BST<Integer> bst) {
		int alturaAtual = bst.height() + 1;
		int nivel = bst.height();
		boolean isRoot = true;
		BSTNode<Integer> left = null;
		BSTNode<Integer> right = null;

		if (this.size() == 0) {
			while (nivel >= 0) {
				// root
				if (isRoot) {
					this.insert(bst.getRoot().getData(), alturaAtual, alturaAtual);
					left = (BSTNode<Integer>) bst.getRoot().getLeft();
					right = (BSTNode<Integer>) bst.getRoot().getRight();
					isRoot = false;
					nivel--;
					alturaAtual--;
					// filhos
				} else {
					// left
					if (left != null && !left.isEmpty()) {
						this.insert(left.getData(), alturaAtual, alturaAtual);
						// left do left
						if (left.getLeft() != null && !left.getLeft().isEmpty()) {
							this.insert(left.getLeft().getData(), alturaAtual - 1, alturaAtual - 1);
						}
						// right do left
						if (left.getRight() != null && !left.getRight().isEmpty()) {
							this.insert(left.getRight().getData(), alturaAtual - 1, alturaAtual - 1);
						}
					}
					// right e filhos
					if (!right.isEmpty()) {
						insert(right.getData(), alturaAtual, alturaAtual);
						// left do right
						if (right.getLeft() != null && !right.getLeft().isEmpty()) {
							this.insert(right.getLeft().getData(), alturaAtual - 1, alturaAtual - 1);
						}
						// right do right
						if (right.getRight() != null && !right.getRight().isEmpty()) {
							this.insert(right.getRight().getData(), alturaAtual - 1, alturaAtual - 1);
						}
					}
					nivel--;
				}
			}
		}
	}
}
