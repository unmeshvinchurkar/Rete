package com.java.nodes;

import java.util.List;

public abstract class AbstractNode implements Node {

	private Node leftParent = null;
	private Node rightParent = null;

	protected BetaMemory memory = new BetaMemory();
	protected Node childNode = null;

	public void sinkLeft(Tuple tuple) {

		memory.addLeftTuple(tuple);
		List<Tuple> rightList = memory.getRightTupleMemory();
		for (Tuple t : rightList) {

			Tuple merged = tuple.mergeTuple(t);

			if (isTrueFor(merged)) {

				if (childNode != null) {

					if (childNode instanceof AbstractNode) {
						AbstractNode cn = (AbstractNode) childNode;

						if (cn.isLeftParent(this)) {
							cn.sinkLeft(merged);
						} else {
							cn.sinkRight(merged);
						}
					} else {
						childNode.sinkObject(merged);
					}
				}
			}
		}
	}

	public void sinkRight(Tuple tuple) {

		memory.addRightTuple(tuple);
		List<Tuple> leftList = memory.getLeftTupleMemory();

		for (Tuple t : leftList) {

			Tuple merged = t.mergeTuple(tuple);

			if (isTrueFor(merged)) {
				if (childNode != null) {
					if (childNode instanceof AbstractNode) {
						AbstractNode cn = (AbstractNode) childNode;
						if (cn.isLeftParent(this)) {
							cn.sinkLeft(merged);
						} else {
							cn.sinkRight(merged);
						}
					} else {
						childNode.sinkObject(merged);
					}
				}
			}
		}
	}

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
