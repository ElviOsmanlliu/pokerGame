package de.trialTask.strategy;

import java.util.Arrays;

import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class FourOfAKindStrategy implements IRankingStrategy {

	@Override
	public int rank(PokerHand handOne, PokerHand handTwo) {
		int result = 0;
		
		PokerCard[] handOneCards = handOne.getCards().clone();
		PokerCard[] handTwoCards = handTwo.getCards().clone();
		
		if (existsFourSameValues(handOneCards) && existsFourSameValues(handTwoCards)) {
			result = compareHandCards(handOneCards, handTwoCards);
		}
		
		return result;
	}

	private boolean existsFourSameValues(PokerCard[] cards){
		Arrays.sort(cards);
		for (int i=0; i < cards.length-3; i++) {
			if (cards[i].compareTo(cards[i+1]) == 0
					&& cards[i+1].compareTo(cards[i+2]) == 0
					&& cards[i+2].compareTo(cards[i+3]) == 0) {
				return true;
			}
		}
		return false;
	}
	
	private int compareHandCards(PokerCard[] handOneCards, PokerCard[] handTwoCards){
		PokerCard[] handOneSameThree = getSameCards(handOneCards);
		PokerCard[] handTwoSameThree = getSameCards(handTwoCards);
		
		return handOneSameThree[0].compareTo(handTwoSameThree[0]);
	}
	
	private PokerCard[] getSameCards(PokerCard[] cards){
		PokerCard[] sameCards = new PokerCard[4];
		
		Arrays.sort(cards);
		for (int i=0; i < cards.length-3; i++) {
			if (cards[i].compareTo(cards[i+1]) == 0
					&& cards[i+1].compareTo(cards[i+2]) == 0
					&& cards[i+2].compareTo(cards[i+3]) == 0) {
				sameCards[0] = cards[i];
				sameCards[1] = cards[i+1];
				sameCards[2] = cards[i+2];
				sameCards[3] = cards[i+3];
				break;
			}
		}
		
		return sameCards;
	}
	
	@Override
	public String toString() {
		return "Four of a Kind";
	}
	
}
