package com.java.nodes;

public abstract class AbstractNode implements Node {

	private Node leftParent = null;
	private Node rightParent = null;

	abstract public void sinkLeft(Tuple tuple);

	abstract public void sinkRight(Tuple tuple);

	public boolean isLeftParent(Node p) {
		if (leftParent == p) {
			return true;
		}
		return false;
	}

	public boolean isRightParent(Node p) {
		if (rightParent == p) {
			return true;
		}
		return false;
	}

	public Node getLeftParent() {
		return leftParent;
	}

	public void setLeftParent(Node leftParent) {
		this.leftParent = leftParent;
	}

	public Node getRightParent() {
		return rightParent;
	}

	public void setRightParent(Node rightParent) {
		this.rightParent = rightParent;
	}

}
