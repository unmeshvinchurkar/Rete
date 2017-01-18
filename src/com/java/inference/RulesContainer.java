package com.java.inference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.java.base.Action;
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
	private Map<Integer, Set<String>> rulePatternMap = new HashMap<Integer, Set<String>>();
	private Set<Object> objectMemory = new LinkedHashSet<>();
	private Set usedPatterns = new HashSet();

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

	public void removeObjects(Collection<Object> objs) {
		for (Object obj : objs) {
			root.removeObject(new Tuple(obj));
		}
	}

	public void removeObject(Object obj) {
		root.removeObject(new Tuple(obj));
	}

	public void run() {

		for (Object memObject : objectMemory) {
			root.sinkObject(new Tuple(memObject));

			Collection<Integer> aRuleIds = ConflictSet.getActiveRuleIds();

			if (aRuleIds != null && aRuleIds.size() > 0) {

				// Resolve conflict set

				// This map contains resolve rule ids at the end of next
				// for-loop

				Map<Integer, Set<Class>> ruleId_TargerCl = new HashMap<>();

				for (Integer currentRuleId : aRuleIds) {

					// ConflictSet.getTuplesByRuleId(ruleId);

					Set<Class> targetClasses = ruleMap.get(currentRuleId).getTargetClasses();
					Integer intersectedRuleId = doIntersect(targetClasses, ruleId_TargerCl);

					if (intersectedRuleId == null) {
						ruleId_TargerCl.put(currentRuleId, targetClasses);

					} else {
						// Rule with more conditions is given a priority
						if (ruleMap.get(intersectedRuleId).getConditions().size() < ruleMap.get(currentRuleId)
								.getConditions().size()) {
							ruleId_TargerCl.replace(currentRuleId, targetClasses);

						}

					}
				}
				// -------------------------------------------------------------------------------------

				// Use rules stored in ruleId_TargerCl
				// ///////////////////////////////////////////////////

				List<Object> modifiedObjects = new ArrayList();

				// If a rule is already fired with a given pattern
				// remove it from the list
				for (Integer ruleId : ruleId_TargerCl.keySet()) {
					
					Tuple tuple = (Tuple) ConflictSet.getTuplesByRuleId(ruleId).toArray()[0];
					if (isRuleAlreadyFired(tuple, ruleId)) {
						ruleId_TargerCl.remove(ruleId);
					}
				}
				
				

				// Fire Rules and collect modified objects
				for (Integer ruleId : ruleId_TargerCl.keySet()) {
					List<Action> actions = this.getRuleById(ruleId).getActions();

					for (Action a : actions) {
						List<Object> objects = a.getTask()
								.execute((Tuple) ConflictSet.getTuplesByRuleId(ruleId).toArray()[0]);
						modifiedObjects.addAll(objects);
					}
				}

				// Remove old objects
				objectMemory.removeAll(modifiedObjects);

				// Remove old objects from the networl=k
				this.removeObjects(modifiedObjects);

				// Add New Objects to working memory
				objectMemory.addAll(modifiedObjects);
			}
		}
	}

	private boolean isRuleAlreadyFired(Tuple tuple, Integer ruleId) {

		Rule rule = this.getRuleById(ruleId);

		List<Condition> conditions = rule.getConditions();
		String patternKey = "";

		for (Condition cond : conditions) {

			List<Class> boms = cond.getBoms();
			List<String> pNames = cond.getPropertyNames();

			for (int i = 0; i < boms.size(); i++) {
				Class cl = boms.get(i);
				String pName = pNames.get(i);

				List<Object> objs = tuple.getObjectsByClass(cl);
				Object obj = objs.get(0);

				try {
					Class noparams[] = {};
					Method method = cl.getDeclaredMethod(
							"get" + pName.substring(0, 1).toUpperCase() + pName.substring(1), noparams);
					Object value = method.invoke(obj, null);

					patternKey = patternKey + "_" + value.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (rulePatternMap.get(ruleId) == null) {
			rulePatternMap.put(ruleId, new HashSet<>());
		}

		if (rulePatternMap.get(ruleId).contains(patternKey)) {
			return true;
		} else {
			rulePatternMap.get(ruleId).add(patternKey);
			return false;
		}
	}

	private Integer doIntersect(Set<Class> targetClasses, Map<Integer, Set<Class>> ruleId_TargerCl) {
		Integer ruleId = null;
		Set<Integer> processedRIds = ruleId_TargerCl.keySet();

		for (Class cl : targetClasses) {
			for (Integer pRuleId : processedRIds) {

				if (ruleId_TargerCl.get(pRuleId).contains(cl)) {
					ruleId = pRuleId;
					break;
				}
			}
		}
		return ruleId;
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
