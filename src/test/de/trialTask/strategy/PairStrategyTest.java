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
	
	private PokerHand handOne;
	private PokerHand handTwo;
	private PairStrategy strategy;

	@Before
	public void setUp() throws Exception {
		handOne = new PokerHand();
		handTwo = new PokerHand();
		strategy = new PairStrategy();
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
		handOne.setCards(generatePokerCardsWithOnePairAndLastCard(CardValue.KING, CardValue.SEVEN));
		handTwo.setCards(generatePokerCardsWithOnePairAndLastCard(CardValue.KING, CardValue.FIVE));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(result, 1);
	}
	
	@Test 
	public void testTwoPokerHandsWithThreeSameCards() {
		handOne.setCards(generatePokerCardsWithThreeSameCards(CardValue.KING));
		handTwo.setCards(generatePokerCardsWithOnePair(CardValue.ACE));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(result, 2);
	}

	private PokerCard[] generatePokerCardsWithOnePair(CardValue pairCardValue) {
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, pairCardValue);
		PokerCard three = new PokerCard(CardSuite.HEART, pairCardValue);
		PokerCard five = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.FOUR);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}
	
	private PokerCard[] generatePokerCardsWithOnePairAndLastCard(CardValue pairCardValue, CardValue lastCardValue) {
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.TEN);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, pairCardValue);
		PokerCard three = new PokerCard(CardSuite.HEART, pairCardValue);
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.NINE);
		PokerCard five = new PokerCard(CardSuite.DIAMOND, lastCardValue);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}
	
	private PokerCard[] generatePokerCardsWithThreeSameCards(CardValue sameCardValue) {
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, sameCardValue);
		PokerCard three = new PokerCard(CardSuite.HEART, sameCardValue);
		PokerCard five = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		PokerCard one = new PokerCard(CardSuite.CLUB, sameCardValue);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}
	
}
