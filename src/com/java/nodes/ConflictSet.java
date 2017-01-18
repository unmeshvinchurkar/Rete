package com.java.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
//import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Comparator;

public class ConflictSet {

	private static Set<Integer> ruleIds = new LinkedHashSet<>();
	private static Map<Integer, Set<Tuple>> ruleTupleMap = new LinkedHashMap<>();
	private static Map<Integer, Integer> ruleIdTime = new LinkedHashMap<Integer, Integer>();

	private static int time = 1;

	public static void clear() {
		ruleIds.clear();
		ruleTupleMap.clear();
	}

	public static int getTimeStamp(Integer ruleId) {
		return ruleIdTime.get(ruleId);
	}

	public static Collection<Integer> getActiveRuleIds() {

		List<Entry<Integer, Integer>> entries = new ArrayList<Entry<Integer, Integer>>();
		entries.addAll(ruleIdTime.entrySet());
		
		Collections.sort(entries, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
				return a.getValue().compareTo(b.getValue());
			}
		});

		List<Integer> rules = new ArrayList<Integer>();
		for (Entry<Integer, Integer> entry : entries) {

			if (ruleIds.contains(entry.getKey())) {
				rules.add(entry.getKey());
			}
		}

		return rules;
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

		if (ruleIdTime.get(ruleId) == null) {
			ruleIdTime.put(ruleId, time++);
		}

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
