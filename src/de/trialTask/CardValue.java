package de.trialTask;

public enum CardValue {
	ONE("one", 1), 
	TWO("two", 2), 
	THREE("three", 3), 
	FOUR("four", 4), 
	FIVE("five", 5), 
	SIX("six", 6), 
	SEVEN("seven", 7), 
	EIGHT("eight", 8), 
	NINE("nine", 9), 
	TEN("ten", 10), 
	JACK("jack", 11), 
	QUEEN("quenn", 12), 
	KING("king", 13), 
	ACE("ace", 14);
	
	private String valueAsString;
	public String getValueAsString() {
		return valueAsString;
	}

	public int getValueAsNumber() {
		return valueAsNumber;
	}

	private int valueAsNumber;
	
	private CardValue(String valueAsString, int valueAsNumber) {
		this.valueAsString = valueAsString;
		this.valueAsNumber = valueAsNumber;
	}
	
	@Override
	public String toString(){
		return this.valueAsString;
	}
	
}
