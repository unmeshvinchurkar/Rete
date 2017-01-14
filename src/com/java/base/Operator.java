package com.java.base;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Operator {

	private String operator = null;

	private static Map<String, Operator> cMap = new HashMap<String, Operator>();

	public static final Operator GREATER_THAN = new Operator(">");
	public static final Operator LESS_THAN = new Operator("<");
	public static final Operator GREATER_THAN_EQUALS = new Operator(">=");
	public static final Operator lESS_THAN_EQUALS = new Operator("<=");
	public static final Operator EQUALS = new Operator("==");
	public static final Operator NOT_EQUALS = new Operator("!=");

	private Operator(String operator) {
		cMap.put(operator, this);
		this.operator = operator;
	}

	public static Operator getOperator(String operator) {
		return cMap.get(operator);
	}

	public static boolean compare(Object value1, String op, Object value2, Comparator comparator) {
		return compare(value1, cMap.get(op), value2, comparator);

	}

	public static boolean compare(Object value1, Operator op, Object value2, Comparator comparator) {

		if (value1 == null || value2 == null) {
			return false;
		} else if (value1.getClass() != value2.getClass()) {
			return false;
		}
		int result = comparator.compare(value1, value2);
		return _checkResult(result, op);

	}

	public static boolean compare(Object value1, String op, Object value2) {
		return compare(value1, cMap.get(op), value2);
	}

	public static boolean compare(Object value1, Operator op, Object value2) {

		if (value1 == null || value2 == null) {
			return false;
		} else if (value1.getClass() != value2.getClass()) {
			return false;
		}

		if (value1 instanceof Comparable) {
			int result = ((Comparable) value1).compareTo(value2);
			return _checkResult(result, op);
		} else if (EQUALS.equals(op)) {
			return value1.equals(value2);
		} else if (NOT_EQUALS.equals(op)) {
			return !value1.equals(value2);
		}

		/**
		 * 
		 * if (value1 instanceof Integer) { Integer obj1 = (Integer) value1;
		 * Integer obj2 = (Integer) value2; if (GREATER_THAN.equals(op)) {
		 * return obj1 > obj2; } else if (GREATER_THAN_EQUALS.equals(op)) {
		 * return obj1 >= obj2; } else if (LESS_THAN.equals(op)) { return obj1 <
		 * obj2; } else if (lESS_THAN_EQUALS.equals(op)) { return obj1 <= obj2;
		 * } } else if (value1 instanceof Float) { Float obj1 = (Float) value1;
		 * Float obj2 = (Float) value2; if (GREATER_THAN.equals(op)) { return
		 * obj1 > obj2; } else if (GREATER_THAN_EQUALS.equals(op)) { return obj1
		 * >= obj2; } else if (LESS_THAN.equals(op)) { return obj1 < obj2; }
		 * else if (lESS_THAN_EQUALS.equals(op)) { return obj1 <= obj2; } } else
		 * if (value1 instanceof Double) { Double obj1 = (Double) value1; Double
		 * obj2 = (Double) value2; if (GREATER_THAN.equals(op)) { return obj1 >
		 * obj2; } else if (GREATER_THAN_EQUALS.equals(op)) { return obj1 >=
		 * obj2; } else if (LESS_THAN.equals(op)) { return obj1 < obj2; } else
		 * if (lESS_THAN_EQUALS.equals(op)) { return obj1 <= obj2; } }
		 */

		return false;
	}

	private static boolean _checkResult(int result, Operator op) {

		if (EQUALS.equals(op) && result == 0) {
			return true;
		} else if (NOT_EQUALS.equals(op) && result != 0) {
			return true;
		} else if (GREATER_THAN.equals(op) && result > 0) {
			return true;
		} else if (GREATER_THAN_EQUALS.equals(op) && result >= 0) {
			return true;
		} else if (LESS_THAN.equals(op) && result < 0) {
			return true;
		} else if (lESS_THAN_EQUALS.equals(op) && result <= 0) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
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
		Operator other = (Operator) obj;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return operator;
	}

}
