package com.java.test.flight.example;

public class Account {

	private String name;
	private String signedUpFor ="";
	private String status;
	private int awardMilesCurrentYear =0;
	private int awardMilesLastYear=0;
	private int flightId;

	public Account(String name) {
		super();
		this.name = name;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignedUpFor() {
		return signedUpFor;
	}

	public void setSignedUpFor(String signedUpFor) {
		this.signedUpFor = signedUpFor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAwardMilesCurrentYear() {
		return awardMilesCurrentYear;
	}

	public void setAwardMilesCurrentYear(int awardMilesCurrentYear) {
		this.awardMilesCurrentYear = awardMilesCurrentYear;
	}

	public int getAwardMilesLastYear() {
		return awardMilesLastYear;
	}

	public void setAwardMilesLastYear(int awardMilesLastYear) {
		this.awardMilesLastYear = awardMilesLastYear;
	}

	@Override
	public String toString() {
		return "Account [name=" + name + ", status=" + status + ", awardMilesCurrentYear=" + awardMilesCurrentYear
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Account other = (Account) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
