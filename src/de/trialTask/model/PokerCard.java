package de.trialTask.model;

public class PokerCard implements Comparable<PokerCard> {
	
	private CardSuite suite;
	public CardSuite getSuite() {
		return suite;
	}

	private CardValue value;
	public CardValue getValue() {
		return value;
	}

	private String pokerCardAsString;
	public String getPokerCardAsString() {
		return this.toString();
	}

	public PokerCard(CardSuite suite, CardValue value){
		this.suite = suite;
		this.value = value;
	}

	@Override
	public int compareTo(PokerCard otherCard) {
		int thisCardValueAsNumber = this.value.getNumber();
		int otherCardValueAsNumber = otherCard.value.getNumber();
		return new Integer(thisCardValueAsNumber).compareTo(new Integer(otherCardValueAsNumber));
	}
	
	/**
	 * Compares two poker card objects by their suite and value.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!this.getClass().equals(obj.getClass())){
			return false;
		}
		PokerCard otherCard = (PokerCard) obj;
		if (this.getSuite() != otherCard.getSuite() 
				|| this.getValue() != otherCard.getValue()) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString(){
		return suite.getName() + " " + value.getName();
	}
	
}
