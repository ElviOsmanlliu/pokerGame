package de.trialTask.strategy;

import java.util.Arrays;

import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class FlushStrategy implements IRankingStrategy {
	
	private HighCardStrategy highCardStrategy;
	
	FlushStrategy(){};
	
	FlushStrategy(HighCardStrategy highCardStrategy) {
		this.highCardStrategy = highCardStrategy;
	}

	@Override
	public int rank(PokerHand handOne, PokerHand handTwo) {
		int result = 0;
		
		PokerCard[] handOneCards = handOne.getCards().clone();
		PokerCard[] handTwoCards = handTwo.getCards().clone();
		
		if (existsFlush(handOneCards) && existsFlush(handTwoCards)) {
			Arrays.sort(handOneCards);
			Arrays.sort(handTwoCards);
			
			result = highCardStrategy.
					compareSortedCardsByHighestValue(handOneCards, handTwoCards, PokerHand.POKER_HAND_SIZE);
		}
		return result;
	}
	
	private boolean existsFlush(PokerCard[] cards) {
		if (cards[0].getSuite() == cards[1].getSuite()
				&& cards[1].getSuite() == cards[2].getSuite()
				&& cards[2].getSuite() == cards[3].getSuite()
				&& cards[3].getSuite() == cards[4].getSuite()) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Flush";
	}

}
