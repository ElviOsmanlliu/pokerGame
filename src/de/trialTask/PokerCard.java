package de.trialTask;

public class PokerCard implements Comparable<PokerCard> {

	private CardSuite suite;
	public CardSuite getSuite() {
		return suite;
	}

	public CardValue getValue() {
		return value;
	}

	private CardValue value;

	public PokerCard(CardSuite suite, CardValue value){
		this.suite = suite;
		this.value = value;
	}

	@Override
	public int compareTo(PokerCard otherCard) {
		int thisCardValueAsNumber = this.value.getValueAsNumber();
		int otherCardValueAsNumber = otherCard.value.getValueAsNumber();
		return new Integer(thisCardValueAsNumber).compareTo(new Integer(otherCardValueAsNumber));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!this.getClass().equals(obj.getClass())){
			return false;
		}
		PokerCard otherCard = (PokerCard) obj;
		if (this.compareTo(otherCard) != 0) {
			return false;
		}
		return true;
	}
	
}
