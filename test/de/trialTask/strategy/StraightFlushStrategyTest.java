package de.trialTask.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class StraightFlushStrategyTest {

	private PokerHand handOne;
	private PokerHand handTwo;
	private StraightFlushStrategy strategy;

	@Before
	public void setUp() throws Exception {
		handOne = new PokerHand();
		handTwo = new PokerHand();
		strategy = new StraightFlushStrategy(new StraightStrategy(), new FlushStrategy());
	}
	
	@Test
	public void testStraightCardsHandWithHighestValueWins() {
		handOne.setCards(generateStraightDiamondCardsStartingWithValueTwo());
		handTwo.setCards(generateStraightHeartCardsStartingWithValueFive());
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(-1, result);
	}
	
	private PokerCard[] generateStraightDiamondCardsStartingWithValueTwo() {
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.FOUR);
		PokerCard three = new PokerCard(CardSuite.DIAMOND, CardValue.FIVE);
		PokerCard five = new PokerCard(CardSuite.DIAMOND, CardValue.SIX);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}
	
	private PokerCard[] generateStraightHeartCardsStartingWithValueFive() {
		PokerCard one = new PokerCard(CardSuite.HEART, CardValue.FIVE);
		PokerCard two = new PokerCard(CardSuite.HEART, CardValue.SIX);
		PokerCard four = new PokerCard(CardSuite.HEART, CardValue.SEVEN);
		PokerCard three = new PokerCard(CardSuite.HEART, CardValue.EIGHT);
		PokerCard five = new PokerCard(CardSuite.HEART, CardValue.NINE);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}

}
