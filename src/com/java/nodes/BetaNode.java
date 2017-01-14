package com.java.nodes;

import java.util.List;

import com.java.base.Condition;

public class BetaNode extends AbstractNode {

	private Node childNode = null;

	private BetaMemory memory = new BetaMemory();

	private Condition joinCondition = null;

	public BetaNode(Condition c) {
		this.joinCondition = c;
	}

	public int getRuleId() {
		return this.joinCondition.getRuleId();
	}

	public int getConditionId() {
		return this.joinCondition.getId();
	}

	public Node getChildNode(Object key) {
		return childNode;
	}

	public void addChildNode(Node node) {
		if (childNode == null) {
			childNode = node;
		}
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

	public List<Class> getClasses() {
		return joinCondition.getBoms();
	}

	public boolean isTrueFor(Tuple tuple) {
		return joinCondition.isTrueFor(tuple.getObjects().get(0), tuple.getObjects().get(1));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((joinCondition == null) ? 0 : joinCondition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BetaNode other = (BetaNode) obj;
		if (joinCondition == null) {
			if (other.joinCondition != null)
				return false;
		} else if (!joinCondition.equals(other.joinCondition))
			return false;
		return true;
	}

	@Override
	public boolean contains(Node node) {
		return childNode == node;
	}
}
