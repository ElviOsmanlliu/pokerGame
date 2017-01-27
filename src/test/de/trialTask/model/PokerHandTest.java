package test.de.trialTask.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class PokerHandTest {
	
	PokerHand pokerHand;
	
	@Before
	public void setUp(){
		pokerHand = new PokerHand();
	}

	@Test
	public void testPokerHandHasCorrectNumberOfCards(){
		pokerHand.setCards(generatePokerCardsFromTwoToSix());
		
		assertEquals(PokerHand.POKER_HAND_SIZE, pokerHand.getCards().length);
	}
	
	@Test (expected = IllegalStateException.class) 
	public void testPokerHandWithMoreThanFiveCardsThrowsIllegalStateException() {
		pokerHand.setCards(generatePokerCardsFromTwoToSeven());
	}
	
	
	private PokerCard[] generatePokerCardsFromTwoToSix(){
		PokerCard two = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard three = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.FOUR);
		PokerCard five = new PokerCard(CardSuite.DIAMOND, CardValue.FIVE);
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.SIX);
		
		PokerCard[] cards = {one, two, three, four, five};
		return cards;
	}
	
	private PokerCard[] generatePokerCardsFromTwoToSeven(){
		PokerCard two = new PokerCard(CardSuite.DIAMOND, CardValue.TWO);
		PokerCard three = new PokerCard(CardSuite.DIAMOND, CardValue.THREE);
		PokerCard four = new PokerCard(CardSuite.DIAMOND, CardValue.FOUR);
		PokerCard five = new PokerCard(CardSuite.DIAMOND, CardValue.FIVE);
		PokerCard six = new PokerCard(CardSuite.DIAMOND, CardValue.SIX);
		PokerCard one = new PokerCard(CardSuite.DIAMOND, CardValue.SEVEN);
		
		PokerCard[] cards = {one, two, three, four, five, six};
		return cards;
	}

}
