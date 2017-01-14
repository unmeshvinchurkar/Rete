/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.nodes;

import java.util.ArrayList;
import java.util.List;

public class BetaMemory {
	private List<Tuple> leftTupleMemory = new ArrayList<Tuple>();
	private List<Tuple> rightTupleMemory = new ArrayList<Tuple>();

	public BetaMemory() {
	}

	public void removeTuple(Tuple tuple) {
		leftTupleMemory.remove(tuple);
		rightTupleMemory.remove(tuple);
	}

	public void addLeftTuple(Tuple tuple) {
		leftTupleMemory.add(tuple);
	}

	public void addRightTuple(Tuple tuple) {
		rightTupleMemory.add(tuple);
	}

	public List<Tuple> getLeftTupleMemory() {
		return leftTupleMemory;
	}

	public List<Tuple> getRightTupleMemory() {
		return rightTupleMemory;
	}

}
