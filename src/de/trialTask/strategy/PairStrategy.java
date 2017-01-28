package de.trialTask.strategy;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;

public class PairStrategy implements IRankingStrategy {
	
	private PokerCard[] handOneCards;
	private PokerCard[] handTwoCards;

	@Override
	public int rank(PokerHand handOne, PokerHand handTwo) {
		int result = 0;
		
		handOneCards = handOne.getCards().clone();
		handTwoCards = handTwo.getCards().clone();
		
		if (existsPair(handOneCards) && existsPair(handTwoCards)) {
			result = comparePairs(handOneCards, handTwoCards);
		}
		
		return result;
	}
	
	private boolean existsPair(PokerCard[] cards){
		Arrays.sort(cards);
		for (int i=0; i < cards.length-1; i++){
			if (cards[i].compareTo(cards[i+1]) == 0) {
				return true;
			}
		}
		return false;
	}
	
	private int comparePairs(PokerCard[] handOneCards, PokerCard[] handTwoCards){
		int result = 0;
		List<PokerCard[]> handOneCardsSorted = getPairCardsAndRestCardsAscending(handOneCards);
		List<PokerCard[]> handTwoCardsSorted = getPairCardsAndRestCardsAscending(handTwoCards);
		
		PokerCard[] handOnePairs = handOneCardsSorted.get(0);
		PokerCard[] handTwoPairs = handTwoCardsSorted.get(0);
		
		PokerCard[] handOneRestCardsAscending = handOneCardsSorted.get(1);
		PokerCard[] handTwoRestCardsAscending = handTwoCardsSorted.get(1);
		
		if (handOnePairs[0].getValue().getNumber() > handTwoPairs[0].getValue().getNumber()) {
			result = 1;
		} else if (handOnePairs[0].getValue().getNumber() < handTwoPairs[0].getValue().getNumber()) {
			result = 2;
		} else {
			return handOneRestCardsAscending[0].compareTo(handTwoRestCardsAscending[0]);
		}
		
		return result;
	}
	
	private List<PokerCard[]> getPairCardsAndRestCardsAscending(PokerCard[] cards){
		List<PokerCard[]> cardArrays = new ArrayList<PokerCard[]>();
		PokerCard[] pairCards = new PokerCard[2];
		PokerCard[] restCards = new PokerCard[3];
		ArrayList<PokerCard> tempCardList = new ArrayList<PokerCard>(Arrays.asList(cards.clone()));
		PokerCard firstMate;
		PokerCard secondMate;
		Arrays.sort(cards);
		for (int i=0; i < cards.length-1; i++){
			if (cards[i].compareTo(cards[i+1]) == 0) {
				firstMate = cards[i];
				secondMate = cards[i+1];
				pairCards[0] = firstMate;
				pairCards[1] = secondMate;
				tempCardList.remove(firstMate);
				tempCardList.remove(secondMate);
				break;
			}
		}
		restCards = tempCardList.toArray(restCards);
		cardArrays.add(pairCards);
		cardArrays.add(restCards);
		return cardArrays;
	}
	
	@Override
	public String toString() {
		return "Find Pairs";
	}

}
