package com.java.test.flight.example;

public class Flight {

	private int id;
	private int miles;
	private String category;
	private String airLineType;
	
	

	public Flight(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMiles() {
		return miles;
	}

	public void setMiles(int miles) {
		this.miles = miles;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAirLineType() {
		return airLineType;
	}

	public void setAirLineType(String airLineType) {
		this.airLineType = airLineType;
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
		Flight other = (Flight) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Flight [id=" + id + ", miles=" + miles + ", category=" + category + ", airLineType=" + airLineType
				+ "]";
	}
	
	

}
