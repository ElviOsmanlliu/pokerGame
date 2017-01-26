package de.trialTask;

public enum CardSuite {
	CLUB("club"), 
	DIAMOND("diamond"), 
	HEART("heart"), 
	SPADE("spade");
	
	private String suiteName;
	
	private CardSuite(String suiteName){
		this.suiteName = suiteName;
	}
	
	 @Override
	 public String toString(){
		 return this.suiteName;
	 }
}
