package test.de.trialTask.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;
import de.trialTask.strategy.TwoPairsStrategy;

public class TwoPairsStrategyTest {

	private PokerHand handOne;
	private PokerHand handTwo;
	private TwoPairsStrategy strategy;

	@Before
	public void setUp() throws Exception {
		handOne = new PokerHand();
		handTwo = new PokerHand();
		strategy = new TwoPairsStrategy();
	}
	
	@Test
	public void testHandOneHasHighestPairValue() {
		handOne.setCards(generatePokerCardsWithTwoPairs(CardValue.ACE, CardValue.TWO, CardValue.THREE));
		handTwo.setCards(generatePokerCardsWithTwoPairs(CardValue.KING, CardValue.EIGHT, CardValue.NINE));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(1, result);
	}
	
	@Test
	public void testHandTwoHasHighestSecondPairValue() {
		handOne.setCards(generatePokerCardsWithTwoPairs(CardValue.ACE, CardValue.TWO, CardValue.THREE));
		handTwo.setCards(generatePokerCardsWithTwoPairs(CardValue.ACE, CardValue.EIGHT, CardValue.NINE));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(2, result);
	}
	
	@Test
	public void testHandTwoHasHighestLastCard() {
		handOne.setCards(generatePokerCardsWithTwoPairs(CardValue.ACE, CardValue.TWO, CardValue.THREE));
		handTwo.setCards(generatePokerCardsWithTwoPairs(CardValue.ACE, CardValue.TWO, CardValue.NINE));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(2, result);
	}
	
	@Test
	public void testResultUnknownDueToSameValueInPairsAndLastCard() {
		handOne.setCards(generatePokerCardsWithTwoPairs(CardValue.ACE, CardValue.TWO, CardValue.THREE));
		handTwo.setCards(generatePokerCardsWithTwoPairs(CardValue.ACE, CardValue.TWO, CardValue.THREE));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(0, result);
	}

	
	private PokerCard[] generatePokerCardsWithTwoPairs(CardValue firstPairValue, CardValue secondPairValue, CardValue lastCardValue) {
		PokerCard four = new PokerCard(CardSuite.CLUB, secondPairValue);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, firstPairValue);
		PokerCard three = new PokerCard(CardSuite.HEART, firstPairValue);
		PokerCard five = new PokerCard(CardSuite.SPADE, lastCardValue);
		PokerCard one = new PokerCard(CardSuite.DIAMOND, secondPairValue);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}
}
