package de.trialTask.strategy;

import java.util.ArrayList;
import java.util.List;

import de.trialTask.model.PokerHand;

public class StrategyComposition implements IRankingStrategy {
	
	private IRankingStrategy usedStrategy;
	public IRankingStrategy getUsedStrategy() {
		return usedStrategy;
	}
	
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
				usedStrategy = strategy;
				break;
			}
		}
		return result;
	}

	
	
}
