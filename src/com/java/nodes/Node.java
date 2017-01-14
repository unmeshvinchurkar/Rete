package com.java.nodes;

public interface Node {

	public void addChildNode(Node node);

	public void sinkObject(Tuple tuple);

	public Node getChildNode(Object key);

	public boolean contains(Node node);

	public boolean isTrueFor(Tuple tuple);
}
