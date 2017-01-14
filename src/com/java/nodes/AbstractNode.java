package com.java.nodes;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode implements Node {

	private Node leftParent = null;
	private Node rightParent = null;

	protected BetaMemory memory = new BetaMemory();
	protected Node childNode = null;

	public void sinkObject(Tuple tuple) {
		sinkLeft(tuple);
	}

	@Override
	public boolean contains(Node node) {
		return childNode == node;
	}

	public Node getChildNode(Object key) {
		return childNode;
	}

	public void addChildNode(Node node) {
		childNode = node;
	}

	public void removeObject(Tuple tuple) {

		List<Tuple> tuples = memory.getLeftTupleMemory();
		List<Tuple> removeT = new ArrayList<>();

		for (Tuple oldTuple : tuples) {
			if (oldTuple.contains(tuple.getObjects().get(0))) {
				removeT.add(oldTuple);
			}
		}

		tuples.removeAll(removeT);
		removeT.clear();

		tuples = memory.getRightTupleMemory();

		for (Tuple oldTuple : tuples) {
			if (oldTuple.contains(tuple.getObjects().get(0))) {
				removeT.add(oldTuple);
			}
		}
		tuples.removeAll(removeT);
		removeT.clear();

		if (childNode != null) {
			childNode.removeObject(tuple);
		}
	}

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
