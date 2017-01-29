package de.trialTask.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class FourOfAKindStrategyTest {

	private PokerHand handOne;
	private PokerHand handTwo;
	private FourOfAKindStrategy strategy;

	@Before
	public void setUp() throws Exception {
		handOne = new PokerHand();
		handTwo = new PokerHand();
		strategy = new FourOfAKindStrategy();
	}
	
	@Test
	public void testHandWithHighestValueOfTheThreeSameCardsWins() {
		handOne.setCards(generatePokerCardsWithFourSameCardValues(CardValue.TEN, CardValue.TWO));
		handTwo.setCards(generatePokerCardsWithFourSameCardValues(CardValue.FIVE, CardValue.ACE));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(1, result);
	}
	
	private PokerCard[] generatePokerCardsWithFourSameCardValues(CardValue sameCardValue, CardValue lastCardValue) {
		PokerCard one = new PokerCard(CardSuite.DIAMOND, lastCardValue);
		PokerCard two = new PokerCard(CardSuite.SPADE, sameCardValue);
		PokerCard three = new PokerCard(CardSuite.HEART, sameCardValue);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, sameCardValue);
		PokerCard five = new PokerCard(CardSuite.CLUB, sameCardValue);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}

}
