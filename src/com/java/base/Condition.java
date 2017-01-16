package com.java.base;

import java.util.Comparator;
import java.util.List;

public interface Condition {

	public boolean isTrueFor(Object obj1, Object obj2);

	public boolean isTrueFor(Object obj1);

	public boolean isJoin();

	public void setComparator(Comparator comparator);

	List<Class> getBoms();

	public void setRuleId(int ruleId);

	public int getRuleId();

	public int getId();
	
	public Operator getOperator();

	public int hashCode();

	public boolean equals(Object obj);

}
