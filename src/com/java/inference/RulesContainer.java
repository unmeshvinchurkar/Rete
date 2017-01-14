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
import com.java.nodes.ObjectTypeNode;
import com.java.nodes.RootNode;
import com.java.nodes.TerminalNode;
import com.java.nodes.Tuple;

public class RulesContainer {

	private List<Rule> rules = new ArrayList<Rule>();
	private RootNode root = null;
	private Map<Integer, Rule> ruleMap = new HashMap<>();

	public void addRule(Rule r) {
		rules.add(r);
	}

	public void addRules(List<Rule> r) {
		rules.addAll(r);
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	public void sinkObject(Object obj){		
		root.sinkObject(new Tuple(obj));
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

}
