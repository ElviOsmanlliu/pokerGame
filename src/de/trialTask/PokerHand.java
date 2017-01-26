package de.trialTask;

public class PokerHand {
	
	public static final int POKER_HAND_SIZE = 5;

	private PokerCard[] cards = new PokerCard[POKER_HAND_SIZE];
	
	public PokerCard[] getCards() {
		return cards;
	}

	public void setCards(PokerCard[] cards) throws IllegalStateException {
		if(cards.length != POKER_HAND_SIZE) {
			throw new IllegalStateException(); 
		} else {
			this.cards = cards;
		}
	}
	
}
