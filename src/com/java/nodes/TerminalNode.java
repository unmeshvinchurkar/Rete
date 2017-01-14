package com.java.nodes;

public class TerminalNode implements Node {

	private Tuple tuple = null;
	private Integer ruleId = null;
	private boolean isActive = false;

	public TerminalNode(Integer ruleId) {
		this.ruleId = ruleId;
	}

	@Override
	public void addChildNode(Node node) {
	}

	@Override
	public Node getChildNode(Object key) {
		return null;
	}

	@Override
	public void sinkObject(Tuple tuple) {
		this.tuple = tuple;
		isActive = true;
		ConflictSet.addTuple(ruleId, tuple);
	}

	public Tuple getTuple() {
		return tuple;
	}

	public void setTuple(Tuple tuple) {
		this.tuple = tuple;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ruleId == null) ? 0 : ruleId.hashCode());
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
		TerminalNode other = (TerminalNode) obj;
		if (ruleId == null) {
			if (other.ruleId != null)
				return false;
		} else if (!ruleId.equals(other.ruleId))
			return false;
		return true;
	}

	@Override
	public boolean contains(Node node) {
		return false;
	}

	@Override
	public boolean isTrueFor(Tuple tuple) {
		return true;
	}

	@Override
	public void removeObject(Tuple obj) {		
	}

}
