package test.de.trialTask;

import static org.junit.Assert.*;

import org.junit.Test;

import de.trialTask.CardSuite;
import de.trialTask.CardValue;
import de.trialTask.PokerCard;

public class PokerCardTest {
	
	@Test
	public void testPokerCardConstrucion() {
		PokerCard card = new PokerCard(CardSuite.DIAMOND, CardValue.FIVE);
		
		assertEquals(card.getSuite().getName(), CardSuite.DIAMOND.getName());
		
		assertEquals(card.getValue().getName(), CardValue.FIVE.getName());
		assertEquals(card.getValue().getNumber(), CardValue.FIVE.getNumber());
	}
	
	@Test
	public void testPokerCardAreEqualsAlthoughOfDifferentSuite() {
		PokerCard cardDiamond = new PokerCard(CardSuite.DIAMOND, CardValue.FIVE);
		PokerCard cardHeart = new PokerCard(CardSuite.HEART, CardValue.FIVE);
		
		assertEquals(cardDiamond, cardHeart);
	}
	
	@Test 
	public void testPokerCardAceHasHigherValueThanJack() {
		PokerCard cardAce = new PokerCard(CardSuite.SPADE, CardValue.ACE);
		PokerCard cardJack = new PokerCard(CardSuite.CLUB, CardValue.JACK);
		
		assertNotEquals(cardAce, cardJack);
		assertTrue(cardAce.compareTo(cardJack)>0);
	}

}
