package de.trialTask;

public interface RankingStrategy {

	/**
	 * compares the two poker hands based on implemented strategy
	 * @param handOne
	 * 			contains 5 poker cards
	 * @param handTwo
	 * 			contains 5 poker cards
	 * @return
	 * 			1, if handOne is better than handTwo
	 * 			0, not specified
	 * 			2, if handTwo is better than handOne
	 */
	int rank(PokerHand handOne, PokerHand handTwo);
}
