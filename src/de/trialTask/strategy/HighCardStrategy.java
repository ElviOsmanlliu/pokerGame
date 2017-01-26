package de.trialTask.strategy;

import java.util.Arrays;

import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class HighCardStrategy implements IRankingStrategy {
	
	private PokerCard[] handOneCards;
	private PokerCard[] handTwoCards;

	@Override
	public int rank(PokerHand handOne, PokerHand handTwo) {
		handOneCards = handOne.getCards().clone();
		handTwoCards = handTwo.getCards().clone();
		
		Arrays.sort(handOneCards);
		Arrays.sort(handTwoCards);
		
		return compareSortedCardsByHighestValue(handOneCards, handTwoCards, PokerHand.POKER_HAND_SIZE);
	}
	
	/**
	 * compares to set of cards by the highest values in decreasing order
	 * @param handOneCards
	 * @param handTwoCards
	 * @param handSize
	 * 			must be greater 0 and less equals 5
	 * @return
	 * 			1, if handOne is better than handTwo <br>
	 * 			0, all cards are equal <br>
	 * 			2, if handTwo is better than handOne
	 */
	protected int compareSortedCardsByHighestValue(PokerCard[] handOneCards, PokerCard[] handTwoCards, int handSize) {
		int result = 0;
		int i = handSize-1;
		int handOneHighestValue;
		int handTwoHighestValue;
		
		do {
			handOneHighestValue = handOneCards[i].getValue().getNumber();
			handTwoHighestValue = handTwoCards[i].getValue().getNumber();
			
			if (handOneHighestValue > handTwoHighestValue) {
				result = 1;
			} else if (handOneHighestValue < handTwoHighestValue) {
				result = 2;
			}
			i--;
		} while (i>=0 && handOneHighestValue == handTwoHighestValue);
		
		return result;
	}

}
