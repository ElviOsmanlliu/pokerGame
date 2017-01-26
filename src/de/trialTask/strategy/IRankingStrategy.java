package de.trialTask.strategy;

import de.trialTask.model.PokerHand;

public interface IRankingStrategy {

	/**
	 * compares the two poker hands based on implemented strategy
	 * @param handOne
	 * 			contains 5 poker cards
	 * @param handTwo
	 * 			contains 5 poker cards
	 * @return
	 * 			1, if handOne is better than handTwo <br>
	 * 			0, not specified <br>
	 * 			2, if handTwo is better than handOne
	 */
	int rank(PokerHand handOne, PokerHand handTwo);
}
