package com.java.nodes;

import java.util.List;

public class JoinNode extends AbstractNode {

	private Node childNode = null;

	private BetaMemory memory = new BetaMemory();

	@Override
	public Node getChildNode(Object key) {
		return childNode;
	}

	@Override
	public void addChildNode(Node node) {
		childNode = node;
	}

	@Override
	public void sinkObject(Tuple tuple) {
		sinkLeft(tuple);
	}

	@Override
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

	@Override
	public void sinkRight(Tuple tuple) {
		memory.addRightTuple(tuple);

		List<Tuple> leftList = memory.getLeftTupleMemory();

		for (Tuple t : leftList) {

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

	@Override
	public boolean contains(Node node) {
		return childNode == node;
	}

	@Override
	public boolean isTrueFor(Tuple tuple) {
		return true;
	}
}
