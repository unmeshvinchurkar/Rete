package com.java.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.java.base.Condition;
import com.java.base.Rule;

public class RootNode {

	private Map<String, ObjectTypeNode> oNodes = new HashMap<>();

	public void addRule(Rule rule) {

		Set<Class> conditionClasses = rule.getBoms();

		Map<Class, Node> typeParentMap = new HashMap<>();

		// Create object type node
		for (Class clazz : conditionClasses) {
			Node objectNode = oNodes.get(clazz.getName());
			if (objectNode == null) {
				objectNode = new ObjectTypeNode(clazz);
				oNodes.put(clazz.getName(), (ObjectTypeNode) objectNode);
			}
			typeParentMap.put(clazz, objectNode);
		}

		List<Condition> oConditions = rule.getObjectConditions();

		// Create alpha nodes
		for (Condition c : oConditions) {

			Class cl = c.getBoms().get(0);
			Node parent = typeParentMap.get(cl);

			Node alpha = new AlphaNode(c);

			if (!parent.contains(alpha)) {
				parent.addChildNode(alpha);
			} else {
				alpha = parent.getChildNode(alpha);
			}
			parent = alpha;
			typeParentMap.put(cl, parent);
		}

		List<Condition> joinConditions = rule.getJoinConditions();

		// Contains the remaining hanging nodes
		List<Class> usedClasses = new ArrayList<>();
		List<Node> remainingNodes = new ArrayList<>();

		// Create beta nodes from explicit join conditions
		for (Condition c : joinConditions) {
			BetaNode bNode = new BetaNode(c);

			// Remove the parent nodes as they are getting replaced by Beta node
			Node left = typeParentMap.remove(bNode.getClasses().get(0));
			Node right = typeParentMap.remove(bNode.getClasses().get(1));

			usedClasses.add(bNode.getClasses().get(0));
			usedClasses.add(bNode.getClasses().get(1));

			if (left != null && right != null) {
				bNode.setLeftParent(left);
				bNode.setRightParent(right);
				left.addChildNode(bNode);
				right.addChildNode(bNode);
			}
			remainingNodes.add(bNode);
		}

		for (Class uc : usedClasses) {
			typeParentMap.remove(uc);
		}
		remainingNodes.addAll(typeParentMap.values());

		// Create Join nodes ////////////////////////////////
		while (remainingNodes.size() > 1) {
			Node left = remainingNodes.remove(0);
			Node right = remainingNodes.remove(1);

			JoinNode jNode = new JoinNode();
			jNode.setLeftParent(left);
			jNode.setRightParent(right);

			left.addChildNode(jNode);
			right.addChildNode(jNode);

			remainingNodes.add(jNode);
		}

		// Add terminal nodes //////////////////////////////
		if (remainingNodes.size() == 1) {
			Node hNode = remainingNodes.get(0);
			TerminalNode tn = new TerminalNode(rule.getId());
			hNode.addChildNode(tn);
		}
	}

	public void addObjectTypeNode(ObjectTypeNode node) {
		oNodes.put(node.getObjectType(), node);
	}

	public Map<String, ObjectTypeNode> getObjectTypeNodes() {
		return oNodes;
	}

	public void sinkObject(Tuple tuple) {
		for (ObjectTypeNode node : oNodes.values()) {
			node.sinkObject(tuple);
		}
	}

}
