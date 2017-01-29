package de.trialTask.strategy;

import java.util.Arrays;

import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class StraightFlushStrategy implements IRankingStrategy {
	
	private StraightStrategy straightStrategy;
	private FlushStrategy flushStrategy;

	StraightFlushStrategy(){}
	
	StraightFlushStrategy(StraightStrategy straightStrategy, FlushStrategy flushStrategy) {
		this.straightStrategy = straightStrategy;
		this.flushStrategy = flushStrategy;
	}
	
	@Override
	public int rank(PokerHand handOne, PokerHand handTwo) {
		int result = 0;
		
		PokerCard[] handOneCards = handOne.getCards().clone();
		PokerCard[] handTwoCards = handTwo.getCards().clone();
		
		Arrays.sort(handOneCards);
		Arrays.sort(handTwoCards);
		
		if (flushStrategy.existsFlush(handOneCards) && flushStrategy.existsFlush(handTwoCards)
				&& straightStrategy.existsStraight(handOneCards) && straightStrategy.existsStraight(handTwoCards)) {
			result = handOneCards[4].compareTo(handTwoCards[4]);
		}
		return result;
	}

	@Override
	public String toString() {
		return "Straight Flush";
	}
	
}
