package com.java.inference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.java.base.Condition;
import com.java.base.Rule;
import com.java.nodes.AlphaNode;
import com.java.nodes.BetaNode;
import com.java.nodes.ConflictSet;
import com.java.nodes.ObjectTypeNode;
import com.java.nodes.RootNode;
import com.java.nodes.TerminalNode;
import com.java.nodes.Tuple;

public class RulesContainer {

	private List<Rule> rules = new ArrayList<Rule>();
	private RootNode root = null;
	private Map<Integer, Rule> ruleMap = new HashMap<>();
	private Set objectMemory = new LinkedHashSet();

	public void addObject(Object o) {
		objectMemory.add(o);
	}

	public void addObjects(Collection<Object> objList) {
		objectMemory.addAll(objList);
	}

	public void clearObjectMemory() {
		objectMemory.clear();
	}

	public void addRule(Rule r) {
		rules.add(r);
		ruleMap.put(r.getId(), r);
	}

	public void addRules(List<Rule> rs) {
		for (Rule r : rs) {
			addRule(r);
		}
	}

	public Rule getRuleById(Integer ruleId) {
		return ruleMap.get(ruleId);
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public void sinkObject(Object obj) {
		root.sinkObject(new Tuple(obj));
	}

	public void removeObject(Object obj) {
		root.removeObject(new Tuple(obj));
	}

	public void run() {

		// ConflictSet.getActiveRuleIds();
		// ConflictSet.getTupleByRuleId(ruleId);
		
		
		
		
		
		
		
		
		

	}

	public void compile() {
		// Create Root node
		RootNode root = new RootNode();
		Map<Integer, Rule> ruleMap = new HashMap<>();

		for (Rule r : rules) {
			ruleMap.put(r.getId(), r);
			root.addRule(r);
		}

		this.root = root;
	}

	public void clearConflictSet() {
		ConflictSet.clear();
	}

	public Collection<Integer> getActiveRuleIds() {
		return ConflictSet.getActiveRuleIds();
	}

}
