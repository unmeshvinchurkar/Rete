package com.java.test.base;

import java.util.ArrayList;
import java.util.List;

import com.java.base.Action;
import com.java.base.Condition;
import com.java.base.DefaultCondition;
import com.java.base.JoinCondition;
import com.java.base.Operator;
import com.java.base.Rule;
import com.java.base.Task;
import com.java.bom.Student;
import com.java.bom.Subject;
import com.java.inference.RulesContainer;
import com.java.nodes.ConflictSet;

public class TestRules {

	public static void main(String args[]) {

		Condition c1 = new DefaultCondition(Student.class, "age", Operator.GREATER_THAN, 30);

		Condition c2 = new DefaultCondition(Student.class, "age", Operator.LESS_THAN, 100);

		Condition c3 = new JoinCondition(Student.class, "name", Operator.EQUALS, Subject.class, "studentName");

		Task t1 = new Task() {

			public List execute(Object obj[]) {
				List targetObjs = new ArrayList<>();

				Student s = (Student) obj[0];
				s.setPoints(s.getPoints() + 50);
				targetObjs.add(s);
				return targetObjs;
			}
		};
		Action a1 = new Action("points", t1);

		Rule r1 = new Rule();
		r1.addCondition(c1);
		r1.addCondition(c3);
		r1.addAction(a1);

		Rule r2 = new Rule();
		r2.addCondition(c2);
		r2.addAction(a1);

//		Rule r3 = new Rule();
//		r3.addCondition(c3);
//		r3.addAction(a1);

		/// Create objects /////////////////////////////////////
		Student stud = new Student();
		stud.setAge(34);
		stud.setHeight(5);
		stud.setWeight(75);
		stud.setName("Ram");

		Subject maths = new Subject("Maths", "Ram", 100);

		ConflictSet.clear();

		RulesContainer container = new RulesContainer();
		container.addRule(r1);
		container.addRule(r2);
		//container.addRule(r3);
		container.compile();
		container.sinkObject(stud);
		container.sinkObject(maths);

		ConflictSet.print();
		if (c2.isTrueFor(stud)) {
			r1.fire(new Object[] { stud });
		}

		System.out.println("canonical name: " + stud.getClass().getCanonicalName());
		System.out.println("canonical name: " + stud.getClass().getName());

		System.out.println("Object class: " + a1.getTargetObjectClasses());

		System.out.println("Students points: " + stud.getPoints());

	}
}
