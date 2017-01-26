package de.trialTask.strategy;

import java.util.Arrays;

import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class HighCardStrategy implements IRankingStrategy {
	
	private PokerCard[] handOneCards;
	private PokerCard[] handTwoCards;

	@Override
	public int rank(PokerHand handOne, PokerHand handTwo) {
		int result = 0;
		
		handOneCards = handOne.getCards().clone();
		handTwoCards = handTwo.getCards().clone();
		
		Arrays.sort(handOneCards);
		Arrays.sort(handTwoCards);
		
		int i = PokerHand.POKER_HAND_SIZE-1;
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
