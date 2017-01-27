package de.trialTask.strategy;

import java.util.ArrayList;
import java.util.List;

import de.trialTask.model.PokerHand;

public class StrategyComposition implements IRankingStrategy {
	
	private List<IRankingStrategy> orderedStrategies = new ArrayList<>();
	public void setStrategies(List<IRankingStrategy> strategies) {
		orderedStrategies = strategies;
	}

	@Override
	public int rank(PokerHand handOne, PokerHand handTwo) {
		int result = 0;
		for (IRankingStrategy strategy : orderedStrategies) {
			result = strategy.rank(handOne, handTwo);
			if (result != 0) {
				System.out.println("Result was: " + result + ". Used strategy: " + strategy.toString());
				break;
			}
		}
		return result;
	}

	
	
}
