package com.java.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DefaultCondition implements Condition, Comparable {

	private Class bomClass;
	private String propertyName;
	private Object propertyValue;
	private Operator operator;
	private Comparator comparator;

	private String className = null;

	private static int id_counter = 0;
	private int id;
	private int ruleId;

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	
	public Operator getOperator() {
		return operator;
	}

	public int getRuleId() {
		return this.ruleId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DefaultCondition(Class bomClass, String propertyName, Operator operator, Object propertyValue) {
		this.bomClass = bomClass;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.operator = operator;
		this.id = (++id_counter);
		className = this.bomClass.getCanonicalName();
	}

	public DefaultCondition(Class bomClass, String propertyName, String operator, Object propertyValue) {
		this.bomClass = bomClass;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.operator = Operator.getOperator(operator);
		this.id = (++id_counter);
		className = this.bomClass.getCanonicalName();
	}

	public List<Class> getBoms() {
		List<Class> list = new ArrayList<>();
		list.add(bomClass);
		;
		return list;
	}

	public boolean isJoin() {
		return false;
	}

	public boolean isTrueFor(Object obj) {
		return isTrueFor(obj, null);
	}

	public boolean isTrueFor(Object obj, Object obj2) {

		Class noparams[] = {};

		try {

			Method method = bomClass.getDeclaredMethod(
					"get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), noparams);
			Object value = method.invoke(obj, null);

			if (comparator == null) {
				return Operator.compare(value, operator, propertyValue);
			} else {
				return Operator.compare(value, operator, propertyValue, comparator);
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return false;

	}

	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((propertyName == null) ? 0 : propertyName.hashCode());
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
		DefaultCondition other = (DefaultCondition) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (propertyName == null) {
			if (other.propertyName != null)
				return false;
		} else if (!propertyName.equals(other.propertyName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {

		if (!(o instanceof DefaultCondition)) {
			return 1;
		}
		DefaultCondition dc = (DefaultCondition) o;

		if (this.className.compareTo(dc.className) != 0) {
			return this.className.compareTo(dc.className);
		}

		return this.propertyName.compareTo(dc.propertyName);
	}

}
