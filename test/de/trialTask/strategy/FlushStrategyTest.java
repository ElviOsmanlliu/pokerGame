package de.trialTask.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class FlushStrategyTest {

	private PokerHand handOne;
	private PokerHand handTwo;
	private FlushStrategy strategy;
	
	@Before
	public void setUp() throws Exception {
		handOne = new PokerHand();
		handTwo = new PokerHand();
		strategy = new FlushStrategy(new HighCardStrategy());
	}
	
	@Test
	public void testHandsWithSameSuiteComparedWithHighCardStrategy() {
		handOne.setCards(generatePokerCardsFromSameDiamondSuite(CardValue.ACE));
		handTwo.setCards(generatePokerCardsFromSameHeartSuite(CardValue.EIGHT));
		
		int result = strategy.rank(handOne, handTwo);
		
		assertEquals(1, result);
	}
	
	private PokerCard[] generatePokerCardsFromSameDiamondSuite(CardValue lastCardValue){
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		PokerCard three = new PokerCard(CardSuite.DIAMOND, CardValue.FOUR);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.FIVE);
		PokerCard lastCard = new PokerCard(CardSuite.DIAMOND, lastCardValue);
		
		PokerCard[] cards = {one, two, three, four, lastCard};
		return cards;
	}
	
	private PokerCard[] generatePokerCardsFromSameHeartSuite(CardValue lastCardValue){
		PokerCard one = new PokerCard(CardSuite.HEART, CardValue.TWO);
		PokerCard two = new PokerCard(CardSuite.HEART, CardValue.THREE);
		PokerCard three = new PokerCard(CardSuite.HEART, CardValue.FOUR);
		PokerCard four = new PokerCard(CardSuite.HEART, CardValue.FIVE);
		PokerCard lastCard = new PokerCard(CardSuite.HEART, lastCardValue);
		
		PokerCard[] cards = {one, two, three, four, lastCard};
		return cards;
	}

}
