package com.java.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectTypeNode implements Node {

	private String objectType = null;
	private Class objectClass = null;
	private Map<Node, Node> childNodes = new HashMap<Node, Node>();

	public ObjectTypeNode(Class objectClass) {
		this.objectClass = objectClass;
		objectType = objectClass.getCanonicalName();
	}

	public String getObjectType() {
		return objectType;
	}

	public Class getObjectClass() {
		return objectClass;
	}

	public void sinkObject(Tuple tuple) {

		if (!isTrueFor(tuple)) {
			return;
		}

		for (Node node : childNodes.values()) {
			if (node.isTrueFor(tuple)) {

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
			}
		}
	}

	public Node getChildNode(Object key) {
		return childNodes.get(key);
	}

	public void addChildNode(Node node) {
		childNodes.put(node, node);
	}

	public boolean contains(Node node) {
		return childNodes.get(node) != null;
	}

	public Collection<Node> getAlphaNodes() {
		return childNodes.values();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objectType == null) ? 0 : objectType.hashCode());
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
		ObjectTypeNode other = (ObjectTypeNode) obj;
		if (objectType == null) {
			if (other.objectType != null)
				return false;
		} else if (!objectType.equals(other.objectType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ObjectTypeNode [objectType=" + objectType + "]";
	}

	@Override
	public boolean isTrueFor(Tuple tuple) {
		return tuple.getObjects().get(0).getClass().getName().equals(objectType);
	}

}
