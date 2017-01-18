package com.java.nodes;

import java.util.List;

import com.java.base.Condition;

public class BetaNode extends AbstractNode {

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

	public List<Class> getClasses() {
		return joinCondition.getBoms();
	}

	public boolean isTrueFor(Tuple tuple) {
		return joinCondition.isTrueFor(tuple.getObjects().get(0), tuple.getObjects().get(1));
	}

/*	@Override
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
	}*/
}
