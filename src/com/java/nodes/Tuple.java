package com.java.nodes;

import java.util.ArrayList;
import java.util.List;

public class Tuple {

	private List<Object> list = new ArrayList<Object>();

	public Tuple() {
	}

	public Tuple(Object obj) {
		list.add(obj);
	}

	public Tuple(List<Object> objects) {
		list.addAll(objects);
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
		list.addAll(tuple.getObjects());
	}

	public Tuple mergeTuple(Tuple tuple) {
		Tuple t = new Tuple();
		t.addTuple(this);
		t.addTuple(tuple);
		return t;
	}

	public void addObject(Object obj) {
		list.add(obj);
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

}
