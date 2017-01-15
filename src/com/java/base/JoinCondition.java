package com.java.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Applicable if two objects are used at left and right hand side of condition
 * 
 * @author UnmeshVinchurkar
 *
 */
public class JoinCondition implements Condition, Comparable {

	private Class bomClass1;
	private Class bomClass2;
	private String propertyName1;
	private String propertyName2;
	private Operator operator;
	private Comparator comparator;
	private static int id_counter = 0;
	private int id;
	private int ruleId;

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
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

	public JoinCondition(Class bomClass1, String propertyName1, Operator operator, Class bomClass2,
			String propertyName2) {
		this.bomClass1 = bomClass1;
		this.propertyName1 = propertyName1;
		this.operator = operator;
		this.bomClass2 = bomClass2;
		this.propertyName2 = propertyName2;
		this.id = (++id_counter);
	}

	public List<Class> getBoms() {
		List<Class> list = new ArrayList<>();
		list.add(bomClass1);
		list.add(bomClass2);
		;
		return list;
	}

	public JoinCondition(Class bomClass1, String propertyName1, String operator, Class bomClass2,
			String propertyName2) {
		this(bomClass1, propertyName1, Operator.getOperator(operator), bomClass2, propertyName2);

	}

	public boolean isJoin() {
		return true;
	}

	public boolean isTrueFor(Object obj1, Object obj2) {
		Class noparams[] = {};

		if (obj1.getClass().equals(bomClass2) && obj2.getClass().equals(bomClass1)) {
			Object tmp = obj2;
			obj2 = obj1;
			obj1 = tmp;
		}

		try {

			Method method1 = bomClass1.getDeclaredMethod(
					"get" + propertyName1.substring(0, 1).toUpperCase() + propertyName1.substring(1), noparams);
			Object value1 = method1.invoke(obj1, null);

			Method method2 = bomClass2.getDeclaredMethod(
					"get" + propertyName2.substring(0, 1).toUpperCase() + propertyName2.substring(1), noparams);
			Object value2 = method2.invoke(obj2, null);

			if (comparator == null) {
				return Operator.compare(value1, operator, value2);
			} else {
				return Operator.compare(value1, operator, value2, comparator);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bomClass1 == null) ? 0 : bomClass1.getName().hashCode());
		result = prime * result + ((bomClass2 == null) ? 0 : bomClass2.getName().hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((propertyName1 == null) ? 0 : propertyName1.hashCode());
		result = prime * result + ((propertyName2 == null) ? 0 : propertyName2.hashCode());
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
		JoinCondition other = (JoinCondition) obj;
		if (bomClass1 == null) {
			if (other.bomClass1 != null)
				return false;
		} else if (bomClass1 != other.bomClass1)
			return false;
		if (bomClass2 == null) {
			if (other.bomClass2 != null)
				return false;
		} else if (bomClass2 != other.bomClass2)
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (propertyName1 == null) {
			if (other.propertyName1 != null)
				return false;
		} else if (!propertyName1.equals(other.propertyName1))
			return false;
		if (propertyName2 == null) {
			if (other.propertyName2 != null)
				return false;
		} else if (!propertyName2.equals(other.propertyName2))
			return false;
		return true;
	}

	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}

	@Override
	public boolean isTrueFor(Object obj1) {
		throw new IllegalArgumentException("Method not defined");
	}

	@Override
	public int compareTo(Object o) {

		if (!(o instanceof JoinCondition)) {
			return -1;
		}
		JoinCondition dc = (JoinCondition) o;

		if (this.bomClass1.getName().compareTo(dc.bomClass1.getName()) != 0) {
			return this.bomClass1.getName().compareTo(dc.bomClass1.getName());
		}

		if (this.propertyName1.compareTo(dc.propertyName1) != 0) {
			return this.propertyName1.compareTo(dc.propertyName1);
		}

		return this.propertyName2.compareTo(dc.propertyName2);
	}

}
