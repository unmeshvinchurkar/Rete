package com.java.nodes;

import java.util.List;

public class JoinNode extends AbstractNode {	

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
	public boolean contains(Node node) {
		return childNode == node;
	}

	@Override
	public boolean isTrueFor(Tuple tuple) {
		return true;
	}
}
