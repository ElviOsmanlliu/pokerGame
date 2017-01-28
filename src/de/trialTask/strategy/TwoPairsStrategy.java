package de.trialTask.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class TwoPairsStrategy implements IRankingStrategy {
	
	private PokerCard[] handOneCards;
	private PokerCard[] handTwoCards;

	@Override
	public int rank(PokerHand handOne, PokerHand handTwo) {
		int result = 0;
		
		handOneCards = handOne.getCards().clone();
		handTwoCards = handTwo.getCards().clone();
		
		if (existsTwoDifferentPairs(handOneCards) 
				&& existsTwoDifferentPairs(handTwoCards)) {
			result = comparePairs(handOneCards, handTwoCards);
		}
		
		return result;
	}

	private boolean existsTwoDifferentPairs(PokerCard[] cards){
		CardValue pairOneValue = null;
		Arrays.sort(cards);
		for (int i=0; i < cards.length-1; i++){
			if (cards[i].compareTo(cards[i+1]) == 0) {
				if (pairOneValue == null) {
					pairOneValue = cards[i].getValue();
				} else if (cards[i].getValue() != pairOneValue 
						&& pairOneValue != null) {
					return true;
				}
			}
		}
		return false;
	}
	
	private int comparePairs(PokerCard[] handOneCards, PokerCard[] handTwoCards){
		int result = 0;
		List<PokerCard[]> handOneCardsSorted = getBothPairsAndRestCard(handOneCards);
		List<PokerCard[]> handTwoCardsSorted = getBothPairsAndRestCard(handTwoCards);
		
		PokerCard[] handOneFirstPair = handOneCardsSorted.get(0);
		PokerCard[] handTwoFirstPair = handTwoCardsSorted.get(0);
		
		PokerCard[] handOneSecondPair = handOneCardsSorted.get(1);
		PokerCard[] handTwoSecondPair = handTwoCardsSorted.get(1);
		
		PokerCard[] lastCardFirstPair = handOneCardsSorted.get(2);
		PokerCard[] lastCardSecondPair = handTwoCardsSorted.get(2);
		
		// the cards are sorted ascending
		if (handOneSecondPair[0].getValue().getNumber() > handTwoSecondPair[0].getValue().getNumber()) {
			result = 1;
		} else if (handOneSecondPair[0].getValue().getNumber() < handTwoSecondPair[0].getValue().getNumber()) {
			result = 2;
		} else {
			if (handOneFirstPair[0].getValue().getNumber() > handTwoFirstPair[0].getValue().getNumber()) {
				result = 1;
			} else if (handOneFirstPair[0].getValue().getNumber() < handTwoFirstPair[0].getValue().getNumber()) {
				result = 2;
			} else {
				if (lastCardFirstPair[0].getValue().getNumber() > lastCardSecondPair[0].getValue().getNumber()) {
					result = 1;
				} else if (lastCardFirstPair[0].getValue().getNumber() < lastCardSecondPair[0].getValue().getNumber()) {
					result = 2;
				}
			}
		}
		return result;
	}
	
	private List<PokerCard[]> getBothPairsAndRestCard(PokerCard[] cards){
		ArrayList<PokerCard> tmpCardList = new ArrayList<PokerCard>(Arrays.asList(cards.clone()));
		List<PokerCard[]> cardArrays = new ArrayList<PokerCard[]>();
		PokerCard[] firstPair = new PokerCard[2];
		PokerCard[] secondPair = new PokerCard[2];
		PokerCard[] lastCard = new PokerCard[1];
		CardValue pairOneValue = null;
		
		Arrays.sort(cards);
		for (int i=0; i < cards.length-1; i++){
			if (cards[i].compareTo(cards[i+1]) == 0) {
				if (pairOneValue == null) {
					pairOneValue = cards[i].getValue();
					firstPair[0] = cards[i];
					firstPair[1] = cards[i+1];
					tmpCardList.remove(cards[i]);
					tmpCardList.remove(cards[i+1]);
				} else if (cards[i].getValue() != pairOneValue 
						&& pairOneValue != null) {
					secondPair[0] = cards[i];
					secondPair[1] = cards[i+1];
					tmpCardList.remove(cards[i]);
					tmpCardList.remove(cards[i+1]);
					break;
				}
			}
		}
		lastCard[0] = tmpCardList.get(0);
		cardArrays.add(firstPair);
		cardArrays.add(secondPair);
		cardArrays.add(lastCard);
		return cardArrays;
	}
}