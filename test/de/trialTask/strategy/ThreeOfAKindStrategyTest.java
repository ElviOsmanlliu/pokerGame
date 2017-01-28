package de.trialTask.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;
import de.trialTask.strategy.ThreeOfAKindStrategy;

public class ThreeOfAKindStrategyTest {
	
	private PokerHand handOne;
	private PokerHand handTwo;
	private ThreeOfAKindStrategy strategy;

	@Before
	public void setUp() throws Exception {
		handOne = new PokerHand();
		handTwo = new PokerHand();
		strategy = new ThreeOfAKindStrategy();
	}
	
	@Test
	public void testHandWithHighestValueOfTheThreeSameCardsWins() {
		handOne.setCards(generatePokerCardsWithThreeSameCardValues(CardValue.QUEEN));
		handTwo.setCards(generatePokerCardsWithThreeSameCardValues(CardValue.FIVE));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(1, result);
	}
	
	private PokerCard[] generatePokerCardsWithThreeSameCardValues(CardValue sameCardValue) {
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, sameCardValue);
		PokerCard three = new PokerCard(CardSuite.HEART, sameCardValue);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		PokerCard five = new PokerCard(CardSuite.CLUB, sameCardValue);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}
}
