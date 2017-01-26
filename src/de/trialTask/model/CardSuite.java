package de.trialTask.model;

public enum CardSuite {
	CLUB("club"), 
	DIAMOND("diamond"), 
	HEART("heart"), 
	SPADE("spade");
	
	private String name;
	
	public String getName() {
		return name;
	}

	private CardSuite(String suiteName){
		this.name = suiteName;
	}
	
	 @Override
	 public String toString(){
		 return this.name;
	 }
}
