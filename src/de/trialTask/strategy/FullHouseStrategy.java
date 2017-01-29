package de.trialTask.strategy;

import java.util.Arrays;

import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class FullHouseStrategy implements IRankingStrategy {

	@Override
	public int rank(PokerHand handOne, PokerHand handTwo) {
		int result = 0;
		
		PokerCard[] handOneCards = handOne.getCards().clone();
		PokerCard[] handTwoCards = handTwo.getCards().clone();
		
		if (existsFullHouse(handOneCards) && existsFullHouse(handTwoCards)) {
			result = compareHandCards(handOneCards, handTwoCards);
		}
		
		return result;
	}

	private boolean existsFullHouse(PokerCard[] cards) {
		CardValue threeSameValue = null;
		CardValue pairValue = null;
		// exists three same value
		Arrays.sort(cards);
		for (int i=0; i < cards.length-2; i++) {
			if (cards[i].compareTo(cards[i+1]) == 0
					&& cards[i].compareTo(cards[i+2]) == 0) {
				threeSameValue = cards[i].getValue();
				break;
			}
		}
		for (int i=0; i < cards.length-1; i++) {
			if (cards[i].compareTo(cards[i+1]) == 0
					&& threeSameValue != cards[i].getValue()) {
				pairValue = cards[i].getValue();
				break;
			}
		}
		if (threeSameValue != null && pairValue != null) {
			return true;
		}
		return false;
	}
	
	private int compareHandCards(PokerCard[] handOneCards, PokerCard[] handTwoCards){
		PokerCard[] handOneSameThree = getSameCards(handOneCards);
		PokerCard[] handTwoSameThree = getSameCards(handTwoCards);
		
		return handOneSameThree[0].compareTo(handTwoSameThree[0]);
	}
	
	private PokerCard[] getSameCards(PokerCard[] cards){
		PokerCard[] sameCards = new PokerCard[3];
		
		Arrays.sort(cards);
		for (int i=0; i < cards.length-2; i++) {
			if (cards[i].compareTo(cards[i+1]) == 0
					&& cards[i].compareTo(cards[i+2]) == 0) {
				sameCards[0] = cards[i];
				sameCards[1] = cards[i+1];
				sameCards[2] = cards[i+2];
				break;
			}
		}
		
		return sameCards;
	}
	
	@Override
	public String toString() {
		return "Full House";
	}
}
