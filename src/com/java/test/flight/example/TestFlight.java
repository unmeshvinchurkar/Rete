package com.java.test.flight.example;

import java.util.ArrayList;
import java.util.List;

import com.java.base.Action;
import com.java.base.Condition;
import com.java.base.DefaultCondition;
import com.java.base.JoinCondition;
import com.java.base.Operator;
import com.java.base.Rule;
import com.java.base.Task;
import com.java.inference.RulesContainer;
import com.java.nodes.Tuple;

public class TestFlight {

	public static void main(String[] args) {

		Condition join = new JoinCondition(Account.class, "flightId", Operator.EQUALS, Flight.class, "id");

		// if award miles for last year or current year > 25,000 then status =
		// Silver
		Condition c1 = new DefaultCondition(Account.class, "awardMilesLastYear", Operator.GREATER_THAN, 25000);
		Condition c2 = new DefaultCondition(Account.class, "awardMilesCurrentYear", Operator.GREATER_THAN, 25000);

		Task taskSilver = new Task() {
			public List execute(Tuple tuple) {
				List targetObjs = new ArrayList<>();
				Account a = null;
				List<Object> objList = tuple.getObjectsByClass(Account.class);
				a = (Account) objList.get(0);
				a.setStatus("Silver");
				targetObjs.add(a);
				System.out.println("Changed status to Silver");
				return targetObjs;
			}
		};

		Action actionSilver = new Action(Account.class, "status", taskSilver);

		Rule r1 = new Rule();
		r1.addCondition(c1);
		r1.addAction(actionSilver);

		Rule r2 = new Rule();
		r2.addCondition(c2);
		r2.addAction(actionSilver);

		// if award miles for last year or current year > 100,000 then status =
		// Gold
		Condition c3 = new DefaultCondition(Account.class, "awardMilesLastYear", Operator.GREATER_THAN, 100000);
		Condition c4 = new DefaultCondition(Account.class, "awardMilesCurrentYear", Operator.GREATER_THAN_EQUALS, 100000);

		Task taskGold = new Task() {
			public List execute(Tuple tuple) {
				List targetObjs = new ArrayList<>();
				Account a = null;
				List<Object> objList = tuple.getObjectsByClass(Account.class);
				a = (Account) objList.get(0);
				a.setStatus("Gold");
				targetObjs.add(a);

				System.out.println("Changed status to Gold");

				return targetObjs;
			}
		};

		Action actionGold = new Action(Account.class, "status", taskGold);
		Rule r3 = new Rule();
		r3.addCondition(c3);
		r3.addAction(actionGold);

		Rule r4 = new Rule();
		r4.addCondition(c4);
		r4.addAction(actionGold);

		// If flight is less than 500 miles then award 500 miles
		Condition c5 = new DefaultCondition(Flight.class, "miles", Operator.LESS_THAN, 500);

		Task award500m = new Task() {
			public List execute(Tuple tuple) {
				List targetObjs = new ArrayList<>();
				Account a = null;
				List<Object> objList = tuple.getObjectsByClass(Account.class);
				a = (Account) objList.get(0);
				a.setAwardMilesCurrentYear(a.getAwardMilesCurrentYear() + 500);

				System.out.println("Awarded 500 miles");
				targetObjs.add(a);
				return targetObjs;
			}
		};

		Action Action500 = new Action(Account.class, "awardMilesCurrentYear", award500m);
		Rule r5 = new Rule();
		r5.addCondition(c5);
		r5.addCondition(join);
		r5.addAction(Action500);

		// If flight is 500 miles or more then award flight miles

		Condition c6 = new DefaultCondition(Flight.class, "miles", Operator.GREATER_THAN_EQUALS, 500);

		Task awardFlightm = new Task() {
			public List execute(Tuple tuple) {
				List targetObjs = new ArrayList<>();
				Account a = null;
				List<Object> objList = tuple.getObjectsByClass(Account.class);
				a = (Account) objList.get(0);

				Flight fl = (Flight) tuple.getObjectsByClass(Flight.class).get(0);

				a.setAwardMilesCurrentYear(a.getAwardMilesCurrentYear() + fl.getMiles());
				targetObjs.add(a);
				System.out.println("Awarded Flight miles(as miles >500)");

				return targetObjs;
			}
		};

		Action ActionFlightm = new Action(Account.class, "awardMilesCurrentYear", awardFlightm);
		Rule r6 = new Rule();
		r6.addCondition(c6);
		r6.addCondition(join);
		r6.addAction(ActionFlightm);

		/// if category is business or first then award 50% bonus miles
		Condition c7 = new DefaultCondition(Flight.class, "category", Operator.EQUALS, "business");

		Task bonus50Miles = new Task() {
			public List execute(Tuple tuple) {
				List targetObjs = new ArrayList<>();
				Account a = null;
				List<Object> objList = tuple.getObjectsByClass(Account.class);
				a = (Account) objList.get(0);

				Flight fl = (Flight) tuple.getObjectsByClass(Flight.class).get(0);

				a.setAwardMilesCurrentYear(a.getAwardMilesCurrentYear() + fl.getMiles() + fl.getMiles() / 2);

				System.out.println("Awarded 50% bonus miles");

				targetObjs.add(a);
				return targetObjs;
			}
		};

		Action actionCat = new Action(Account.class, "awardMilesCurrentYear", bonus50Miles);
		Rule r7 = new Rule();
		r7.addCondition(c7);
		r7.addCondition(join);
		r7.addAction(actionCat);

		// if status is Gold and airline is not partner then award 100% bonus
		// miles
		Condition c9 = new DefaultCondition(Account.class, "status", Operator.EQUALS, "Gold");
		Condition c10 = new DefaultCondition(Flight.class, "airLineType", Operator.EQUALS, "notPartner");

		Task bonus100Miles = new Task() {
			public List execute(Tuple tuple) {
				List targetObjs = new ArrayList<>();
				Account a = null;
				List<Object> objList = tuple.getObjectsByClass(Account.class);
				a = (Account) objList.get(0);

				Flight fl = (Flight) tuple.getObjectsByClass(Flight.class).get(0);

				a.setAwardMilesCurrentYear(a.getAwardMilesCurrentYear() + fl.getMiles() + fl.getMiles());

				System.out.println("Awarded 100% bonus miles");
				targetObjs.add(a);
				return targetObjs;
			}
		};

		Action bonus100 = new Action(Account.class, "awardMilesCurrentYear", bonus100Miles);
		Rule r8 = new Rule();
		r8.addCondition(c9);
		r8.addCondition(c10);
		r8.addCondition(join);
		r8.addAction(bonus100);

		// if status is Silver and airline is not partner then award 20% bonus
		// miles
		Condition c11 = new DefaultCondition(Account.class, "status", Operator.EQUALS, "Silver");
		Condition c12 = new DefaultCondition(Flight.class, "airLineType", Operator.EQUALS, "notPartner");

		Task bonus20Miles = new Task() {
			public List execute(Tuple tuple) {
				List targetObjs = new ArrayList<>();
				Account a = null;
				List<Object> objList = tuple.getObjectsByClass(Account.class);
				a = (Account) objList.get(0);

				Flight fl = (Flight) tuple.getObjectsByClass(Flight.class).get(0);

				a.setAwardMilesCurrentYear((int) (a.getAwardMilesCurrentYear() + fl.getMiles() + 0.20 * fl.getMiles()));
				System.out.println("Awarded 20% bonus miles");
				targetObjs.add(a);
				return targetObjs;
			}
		};

		Action bonus20 = new Action(Account.class, "awardMilesCurrentYear", bonus20Miles);
		Rule r9 = new Rule();
		r9.addCondition(c11);
		r9.addCondition(c12);
		r9.addCondition(join);
		r9.addAction(bonus20);

		RulesContainer container = new RulesContainer();
		
		

		container.addRule(r5);
		container.addRule(r6);
		container.addRule(r7);
		
		container.addRule(r1);
		container.addRule(r2);
		container.addRule(r3);
		container.addRule(r4);
		
		container.addRule(r8);
		container.addRule(r9);
		container.compile();

		Account acc = new Account("Unmesh");
		acc.setFlightId(1);
		acc.setAwardMilesCurrentYear(98000);

		Flight fl = new Flight(1);
		fl.setAirLineType("notPartner");
		fl.setCategory("business");
		fl.setMiles(2419);

		container.addObject(fl);
		container.addObject(acc);

		container.run();

		System.out.println("Account:  " + acc);

		System.out.println("Flight:  " + fl);

	}

}
