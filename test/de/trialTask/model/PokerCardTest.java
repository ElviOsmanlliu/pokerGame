package de.trialTask.model;

import static org.junit.Assert.*;

import org.junit.Test;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;

public class PokerCardTest {
	
	@Test
	public void testPokerCardConstrucion() {
		PokerCard card = new PokerCard(CardSuite.DIAMOND, CardValue.FIVE);
		
		assertEquals(CardSuite.DIAMOND.getName(), card.getSuite().getName());
		
		assertEquals(CardValue.FIVE.getName(), card.getValue().getName());
		assertEquals( CardValue.FIVE.getNumber(), card.getValue().getNumber());
	}
	
	@Test
	public void testPokerCardAreNotEqualsIfDifferentSuite() {
		PokerCard cardDiamond = new PokerCard(CardSuite.DIAMOND, CardValue.FIVE);
		PokerCard cardHeart = new PokerCard(CardSuite.HEART, CardValue.FIVE);
		
		assertNotEquals(cardDiamond, cardHeart);
	}
	
	@Test 
	public void testPokerCardAceHasHigherValueThanJack() {
		PokerCard cardAce = new PokerCard(CardSuite.SPADE, CardValue.ACE);
		PokerCard cardJack = new PokerCard(CardSuite.CLUB, CardValue.JACK);
		
		assertNotEquals(cardAce, cardJack);
		assertTrue(cardAce.compareTo(cardJack)>0);
	}

}
