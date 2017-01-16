package com.java.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.java.nodes.Tuple;

public class Rule {

	private List<Condition> conditions = new ArrayList<Condition>();
	private List<Condition> joinConditions = new ArrayList<Condition>();
	private List<Condition> objectConditions = new ArrayList<Condition>();

	private List<Action> actions = new ArrayList<Action>();
	private boolean isFired = false;
	private static int id_counter = 0;
	private int id;
	private boolean containsJoin = false;
	private Set<Class> boms = new HashSet<>();

	public Rule() {
		this.id = (++id_counter);
	}

	public Rule(List<Condition> conditions, List<Action> actions) {
		this.conditions = conditions;
		this.actions = actions;

		for (Condition c : this.conditions) {
			if (c.isJoin()) {
				containsJoin = true;
				joinConditions.add(c);
				boms.add(c.getBoms().get(0));
				boms.add(c.getBoms().get(1));
			} else {
				objectConditions.add(c);
				boms.add(c.getBoms().get(0));
			}
		}

	}

	public boolean hasJoin() {
		return containsJoin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void fire(Tuple tuple) {
		for (Action action : actions) {
			action.execute(tuple);
		}
	}

	public void fire(Map<Integer, Tuple> action_tuple) {
		for (Action action : actions) {
			action.execute(action_tuple.get(action.getId()));
		}
	}

	public void reset() {
		isFired = false;
	}

	public boolean isFired() {
		return isFired;
	}

	public Set<Class> getBoms() {
		return boms;
	}

	public Set<Class> getTargetClasses() {
		Set<Class> list = new HashSet<>();

		for (Action act : actions) {
			list.addAll(act.getTargetClasses());
		}
		return list;
	}

	public void addCondition(Condition c) {
		this.conditions.add(c);
		c.setRuleId(this.id);

		if (c.isJoin()) {
			containsJoin = true;
			joinConditions.add(c);
			boms.add(c.getBoms().get(0));
			boms.add(c.getBoms().get(1));
		} else {
			objectConditions.add(c);
			boms.add(c.getBoms().get(0));
		}
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void addAction(Action a) {
		this.actions.add(a);
		a.setRuleId(this.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actions == null) ? 0 : actions.hashCode());
		result = prime * result + ((conditions == null) ? 0 : conditions.hashCode());
		return result;
	}

	public List<Condition> getJoinConditions() {
		return joinConditions;
	}

	public void setJoinConditions(List<Condition> joinConditions) {
		this.joinConditions = joinConditions;
	}

	public List<Condition> getObjectConditions() {
		return objectConditions;
	}

	public void setObjectConditions(List<Condition> objectConditions) {
		this.objectConditions = objectConditions;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		if (actions == null) {
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		if (conditions == null) {
			if (other.conditions != null)
				return false;
		} else if (!conditions.equals(other.conditions))
			return false;
		return true;
	}

}
