package de.trialTask.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class FullHouseStrategyTest {
	
	private PokerHand handOne;
	private PokerHand handTwo;
	private FullHouseStrategy strategy;

	@Before
	public void setUp() throws Exception {
		handOne = new PokerHand();
		handTwo = new PokerHand();
		strategy = new FullHouseStrategy(); 
	}
	
	@Test
	public void testHandWithFullHouseAndHighestValueWins() {
		handOne.setCards(generatePokerCardsWithThreeSameCardValues(CardValue.KING, CardValue.TWO));
		handTwo.setCards(generatePokerCardsWithThreeSameCardValues(CardValue.JACK, CardValue.ACE));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(1, result);
	}

	private PokerCard[] generatePokerCardsWithThreeSameCardValues(CardValue sameCardValue, CardValue pairValue) {
		PokerCard one = new PokerCard(CardSuite.DIAMOND, pairValue);
		PokerCard two = new PokerCard(CardSuite.SPADE, pairValue);
		PokerCard three = new PokerCard(CardSuite.HEART, sameCardValue);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, sameCardValue);
		PokerCard five = new PokerCard(CardSuite.CLUB, sameCardValue);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}
}
