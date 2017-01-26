package test.de.trialTask.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;
import de.trialTask.strategy.HighCardStrategy;
import de.trialTask.strategy.PairStrategy;

public class PairStrategyTest {
	
	PokerHand handOne;
	PokerHand handTwo;
	PairStrategy strategy;

	@Before
	public void setUp() throws Exception {
		handOne = new PokerHand();
		handTwo = new PokerHand();
		strategy = new PairStrategy();
//		strategy.setHighCardStrategy(new HighCardStrategy());
	}
	
	@Test
	public void testTwoPokerHandsbyTheirPairs() {
		handOne.setCards(generatePokerCardsWithOnePair(CardValue.EIGHT));
		handTwo.setCards(generatePokerCardsWithOnePair(CardValue.QUEEN));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(result, 2);
	}
	
	@Test
	public void testTwoPokerHandsWithSamePairsByTheRestCards() {
		handOne.setCards(generatePokerCardsWithOnePair(CardValue.KING, CardValue.SEVEN));
		handTwo.setCards(generatePokerCardsWithOnePair(CardValue.KING, CardValue.FIVE));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(result, 1);
	}

	private PokerCard[] generatePokerCardsWithOnePair(CardValue pairCardValue) {
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.ONE);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, pairCardValue);
		PokerCard three = new PokerCard(CardSuite.HEART, pairCardValue);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard five = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}
	
	private PokerCard[] generatePokerCardsWithOnePair(CardValue pairCardValue, CardValue lastCardValue) {
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.ONE);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, pairCardValue);
		PokerCard three = new PokerCard(CardSuite.HEART, pairCardValue);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard five = new PokerCard(CardSuite.DIAMOND, lastCardValue);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}
	
}
