package de.trialTask;

public class PokerCard implements Comparable {

	private CardSuite suite;
	public CardSuite getSuite() {
		return suite;
	}

	public CardValue getValue() {
		return value;
	}

	private CardValue value;

	PokerCard(CardSuite suite, CardValue value){
		this.suite = suite;
		this.value = value;
	}

	@Override
	public int compareTo(Object obj) {
		PokerCard otherCard = (PokerCard) obj;
		int thisCardValueAsNumber = this.value.getValueAsNumber();
		int otherCardValueAsNumber = otherCard.value.getValueAsNumber();
		return new Integer(thisCardValueAsNumber).compareTo(new Integer(otherCardValueAsNumber));
	}
}
