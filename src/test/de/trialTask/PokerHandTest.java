package test.de.trialTask;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.trialTask.CardSuite;
import de.trialTask.CardValue;
import de.trialTask.PokerCard;
import de.trialTask.PokerHand;

public class PokerHandTest {
	
	PokerHand pokerHand;
	
	@Before
	public void setUp(){
		pokerHand = new PokerHand();
	}

	@Test
	public void testPokerHandHasCorrectNumberOfCards(){
		pokerHand.setCards(generatePokerCardsFromOneToFive());
		
		assertEquals(PokerHand.POKER_HAND_SIZE, pokerHand.getCards().length);
	}
	
	@Test (expected = IllegalStateException.class) 
	public void testPokerHandWithMoreThanFiveCardsThrowsIllegalStateException() {
		pokerHand.setCards(generatePokerCardsFromOneToSix());
	}
	
	
	private PokerCard[] generatePokerCardsFromOneToFive(){
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.ONE);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard three = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.FOUR);
		PokerCard five = new PokerCard(CardSuite.DIAMOND, CardValue.FIVE);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}
	
	private PokerCard[] generatePokerCardsFromOneToSix(){
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.ONE);
		PokerCard two = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard three = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.FOUR);
		PokerCard five = new PokerCard(CardSuite.DIAMOND, CardValue.FIVE);
		PokerCard six = new PokerCard(CardSuite.DIAMOND, CardValue.SIX);
		
		PokerCard[] cards = {one, two, three, four, five, six};
		return cards;
	}

}
