package com.java.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConflictSet {

	private static Set<Integer> ruleIds = new HashSet<>();
	private static Map<Integer, Set<Tuple>> ruleTupleMap = new HashMap<>();

	public static void clear() {
		ruleIds.clear();
		ruleTupleMap.clear();
	}

	public static Collection<Integer> getActiveRuleIds() {
		return ruleIds;
	}

	public static Collection<Tuple> getTuplesByRuleId(Integer ruleId) {
		return ruleTupleMap.get(ruleId);
	}

	public static void print() {
		for (Integer ruleId : ruleTupleMap.keySet()) {
			System.out.println("RuleId: " + ruleId + " " + ruleTupleMap.get(ruleId));
			System.out.println("");
		}
	}

	public static boolean contains(Integer ruleId) {
		return ruleIds.contains(ruleId);
	}

	public static void addTuple(Integer ruleId, Tuple tuple) {
		ruleIds.add(ruleId);
		Set<Tuple> list = ruleTupleMap.get(ruleId);

		if (list == null) {
			ruleTupleMap.put(ruleId, new HashSet<>());
		}

		ruleTupleMap.get(ruleId).add(tuple);
	}

	public static void removeTuple(Integer ruleId, Tuple tuple) {

		if (ruleTupleMap.get(ruleId) != null) {
			ruleTupleMap.get(ruleId).remove(tuple);
		}

		if (ruleTupleMap.get(ruleId) == null || ruleTupleMap.get(ruleId).size() == 0) {
			ruleTupleMap.remove(ruleId);
			ruleIds.remove(ruleId);
		}
	}
}
