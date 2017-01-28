package test.de.trialTask.strategy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;
import de.trialTask.strategy.HighCardStrategy;
import de.trialTask.strategy.IRankingStrategy;
import de.trialTask.strategy.PairStrategy;
import de.trialTask.strategy.StrategyComposition;

public class StrategyCompositionTest {
	
	private StrategyComposition composition = new StrategyComposition();
	private List<IRankingStrategy> orderedStrategies = new ArrayList<>();
	private PairStrategy pairStrategy;
	private HighCardStrategy highCardStrategy;
	private PokerHand handOne;
	private PokerHand handTwo;

	@Before
	public void setUp() throws Exception {
		highCardStrategy = new HighCardStrategy();
		pairStrategy = new PairStrategy();
		pairStrategy.setHighCardStrategy(highCardStrategy);
		orderedStrategies.add(pairStrategy);
		orderedStrategies.add(highCardStrategy);
		composition.setStrategies(orderedStrategies);
		handOne = new PokerHand();
		handTwo = new PokerHand();
	}
	
	@Test
	public void testRankResultUnSpecifiedAndUsedStrategyNullIfCompositionHasNoStrategiesSet() {
		composition.setStrategies(new ArrayList<IRankingStrategy>());
		
		handOne.setCards(generatePokerCardsWithOnePair(CardValue.ACE));
		handTwo.setCards(generatePokerCardsWithOnePair(CardValue.EIGHT));
		
		int result = composition.rank(handOne, handTwo);
		assertEquals(0, result);
		assertEquals(null, composition.getUsedStrategy());
	}

	@Test
	public void testCorrectOrderWhenWinnerPairCardsFound() {
		handOne.setCards(generatePokerCardsWithOnePair(CardValue.ACE));
		handTwo.setCards(generatePokerCardsWithOnePair(CardValue.EIGHT));
		
		int result = composition.rank(handOne, handTwo);
		assertEquals(1, result);
		assertEquals(pairStrategy.toString(), composition.getUsedStrategy().toString());
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
	
}
