package com.java.base;

import java.util.ArrayList;
import java.util.List;

import com.java.nodes.Tuple;

public class Action {

	private static int id_counter = 0;

	private String fieldName = null;
	private Task task = null;
	private int id;
	private List<Class> targetClasses = new ArrayList<>();
	private int ruleId;

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public int getRuleId() {
		return this.ruleId;
	}

	public Action(Class targetClass, String fieldName, Task task) {
		this.fieldName = fieldName;
		this.task = task;
		this.targetClasses.add(targetClass);
		this.id = (++id_counter);
	}

	public Action(List<Class> targetClasses, String fieldName, Task task) {
		this.fieldName = fieldName;
		this.task = task;
		this.targetClasses = targetClasses;
		this.id = (++id_counter);
	}

	public Action() {
		this.id = (++id_counter);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Task getTask() {
		return task;
	}

	public List<Class> getTargetClasses() {
		return targetClasses;
	}

	public void execute(Tuple tuple) {

		task.execute(tuple);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Action other = (Action) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
