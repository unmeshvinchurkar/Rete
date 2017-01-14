package com.java.nodes;

import java.util.HashMap;
import java.util.Map;

import com.java.base.Condition;

public class AlphaNode implements Node {

	private Condition c = null;

	private Map<Node, Node> childNodes = new HashMap<Node, Node>();

	public AlphaNode(Condition c) {
		this.c = c;
	}

	public Node getChildNode(Object key) {
		return childNodes.get(key);
	}

	public void addChildNode(Node node) {

		if (childNodes.get(node) == null) {
			childNodes.put(node, node);
		}
	}

	public boolean contains(Node node) {
		return childNodes.get(node) != null;
	}

	public String getClassName() {
		return c.getBoms().get(0).getCanonicalName();
	}

	public boolean isTrueFor(Tuple tuple) {
		return c.isTrueFor(tuple.getObjects().get(0));
	}

	public void removeObject(Tuple tuple) {
		if (!isTrueFor(tuple)) {
			return;
		}
		for (Node node : childNodes.values()) {
			node.removeObject(tuple);
		}
	}

	public void sinkObject(Tuple tuple) {
		
		if (!isTrueFor(tuple)) {
			return;
		}

		for (Node node : childNodes.values()) {

			//if (node.isTrueFor(tuple)) {

				if (node instanceof AbstractNode) {
					AbstractNode an = (AbstractNode) node;

					if (an.isLeftParent(this)) {
						an.sinkLeft(tuple);
					} else {
						an.sinkRight(tuple);
					}
				} else {
					node.sinkObject(tuple);
				}
			//}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c == null) ? 0 : c.hashCode());
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
		AlphaNode other = (AlphaNode) obj;
		if (c == null) {
			if (other.c != null)
				return false;
		} else if (!c.equals(other.c))
			return false;
		return true;
	}

}
