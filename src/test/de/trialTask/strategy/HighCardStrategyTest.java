package test.de.trialTask;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.trialTask.CardSuite;
import de.trialTask.CardValue;
import de.trialTask.HighCardStrategy;
import de.trialTask.PokerCard;
import de.trialTask.PokerHand;

public class HighCardStrategyTest {

	PokerHand handOne;
	PokerHand handTwo;
	HighCardStrategy strategy;
	
	@Before
	public void setUp() throws Exception {
		handOne = new PokerHand();
		handTwo = new PokerHand();
		strategy = new HighCardStrategy();
	}

	@Test
	public void testHandOneWinsOverHandTwoDueToHighestCard(){
		handOne.setCards(generatePokerCardsFromOneToFour(
				new PokerCard(CardSuite.DIAMOND, CardValue.ACE)));
		handTwo.setCards(generatePokerCardsFromOneToFour(
				new PokerCard(CardSuite.DIAMOND, CardValue.JACK)));
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(result, 1);
	}
	
	@Test
	public void testHandOneWinsOverHandTwoDueToSecondHighestCard(){
		handOne.setCards(generatePokerCardsFromOneToThree(
				new PokerCard(CardSuite.DIAMOND, CardValue.KING),
				new PokerCard(CardSuite.DIAMOND, CardValue.ACE)));
		handTwo.setCards(generatePokerCardsFromOneToThree(
				new PokerCard(CardSuite.DIAMOND, CardValue.JACK),
			    new PokerCard(CardSuite.DIAMOND, CardValue.ACE)));
		
		
		int result = strategy.rank(handOne, handTwo);
		assertEquals(result, 1);
	}
	
	
	private PokerCard[] generatePokerCardsFromOneToFour(PokerCard lastCard){
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.ONE);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard three = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.FOUR);
		
		PokerCard[] cards = {one, two, three, four, lastCard};
		return cards;
	}
	
	private PokerCard[] generatePokerCardsFromOneToThree(PokerCard card, PokerCard lastCard){
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.ONE);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard three = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		
		PokerCard[] cards = {one, two, three, card, lastCard};
		return cards;
	}
}