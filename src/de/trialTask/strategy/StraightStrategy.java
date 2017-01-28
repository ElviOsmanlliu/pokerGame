package de.trialTask.strategy;

import java.util.Arrays;

import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class StraightStrategy implements IRankingStrategy {

	@Override
	public int rank(PokerHand handOne, PokerHand handTwo) {
		int result = 0;
		
		PokerCard[] handOneCards = handOne.getCards().clone();
		PokerCard[] handTwoCards = handTwo.getCards().clone();
		
		Arrays.sort(handOneCards);
		Arrays.sort(handTwoCards);
		
		if (existsStraight(handOneCards) && existsStraight(handTwoCards)) {
			result = handOneCards[4].compareTo(handTwoCards[4]);
		}
		
		return result;
	}
	
	private boolean existsStraight(PokerCard[] cards){
		Arrays.sort(cards);
		if (cards[4].getValue().getNumber() == cards[3].getValue().getNumber() + 1 
			&& cards[3].getValue().getNumber() == cards[2].getValue().getNumber() + 1
			&& cards[2].getValue().getNumber() == cards[1].getValue().getNumber() + 1
			&& cards[1].getValue().getNumber() == cards[0].getValue().getNumber() + 1) {
			
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Straight";
	}
}
