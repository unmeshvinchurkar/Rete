package com.java.nodes;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tuple {

	private List<Object> list = new ArrayList<Object>();

	public Tuple() {
	}
	
	public boolean contains(Object obj){
		return list.contains(obj);
	}

	public Tuple(Object obj) {
		list.add(obj);
	}

	public Tuple(List<Object> objects) {
		list.addAll(objects);
	}

	public Map<Class, Object> getClassObjectsMap() {
		Map<Class, Object> map = new LinkedHashMap<>();
		for (Object o : list) {
			map.put(o.getClass(), o);
		}
		return map;
	}

	public List<Object> getObjectsByClass(Class c) {
		List<Object> objs = new ArrayList<>();

		for (Object o : list) {
			if (o.getClass().equals(c)) {
				objs.add(o);
			}
		}
		return objs;
	}

	public void addTuple(Tuple tuple) {
		for (Object obj : tuple.getObjects()) {
			if (!list.contains(obj)) {
				list.add(obj);
			}
		}
	}

	public Tuple mergeTuple(Tuple tuple) {
		Tuple t = new Tuple();
		t.addTuple(this);
		t.addTuple(tuple);
		return t;
	}

	public void addObject(Object obj) {
		if (!list.contains(obj)) {
			list.add(obj);
		}
	}

	public List<Object> getObjects() {
		return list;
	}

	public void setObjects(List<Object> objects) {
		list.addAll(objects);
	}

	@Override
	public String toString() {
		return "Tuple [list=" + list + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
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
		Tuple other = (Tuple) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		return true;
	}
	

}
