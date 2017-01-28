package de.trialTask.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;
import de.trialTask.strategy.HighCardStrategy;
import de.trialTask.strategy.IRankingStrategy;
import de.trialTask.strategy.PairStrategy;
import de.trialTask.strategy.StrategyComposition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class PokerGameGUI extends Application {
	
	private List<PokerCard> pokerDeckWithPlayingCards = new ArrayList<PokerCard>();
	private boolean isStartNewGameVisible = false;
	
    private TableView<PokerCard> firstPlayerTable = new TableView<PokerCard>();
    private ObservableList<PokerCard> firstPlayerData =
        FXCollections.observableArrayList(new PokerCard[5]);
    
    private TableView<PokerCard> secondPlayerTable = new TableView<PokerCard>();
    private ObservableList<PokerCard> secondPlayerData =
        FXCollections.observableArrayList(new PokerCard[5]);
    
    private StrategyComposition strategyComposition = new StrategyComposition();
    
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("PokerGame_TrialTask");
        stage.setWidth(450);
        stage.setHeight(500);
 
        final Label label = new Label("Random Poker Hands");
        label.setFont(new Font("Arial", 20));
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
 
        createTableWithPlayerCards(firstPlayerTable, "First Player Cards", firstPlayerData);
        createTableWithPlayerCards(secondPlayerTable, "Second Player Cards", secondPlayerData);
        
        Button startNewGameButton = new Button("Start New Game");
        startNewGameButton.setVisible(false);
        startNewGameButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		pokerDeckWithPlayingCards.clear();
        		startNewGameButton.setVisible(false);
        	}
        });
        
        prepareRankingStrategies();
        
        Label winLabel = new Label();
        winLabel.setFont(new Font("Arial", 20));
        
        Button genRandomCardsButton = new Button("New Cards");
        genRandomCardsButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		firstPlayerData.clear();
        		PokerHand firstPlayerPokerHand = getRandomPokerHand();
        		for (PokerCard card: firstPlayerPokerHand.getCards()) {
        			firstPlayerData.add(card);
        		}
        		firstPlayerTable.setItems(firstPlayerData);
        		
        		secondPlayerData.clear();
        		PokerHand secondPlayerPokerHand = getRandomPokerHand();
        		for (PokerCard card: secondPlayerPokerHand.getCards()) {
        			secondPlayerData.add(card);
        		}
        		secondPlayerTable.setItems(secondPlayerData);
        		
        		// If cards still available find the winner
        		if (!isStartNewGameVisible) {
        			int result = strategyComposition.rank(firstPlayerPokerHand, secondPlayerPokerHand);
            		String usedStrategy = strategyComposition.getUsedStrategy().toString();
        			String winLabelText = "";
            		switch(result) {
            		case 1: 
            			winLabelText = "First Player Won! " + usedStrategy; break;
            		case 2: 
            			winLabelText = "Second Player Won! " + usedStrategy; break;
            		case 0: 
            			winLabelText = "No one won! " + usedStrategy; break;
            		}
            		winLabel.setText(winLabelText);
        		}
        		
        		// If all cards played, start new game
        		if (isStartNewGameVisible) {
        			startNewGameButton.setVisible(true);
        			winLabel.setText("");
        		}
        	}
        });
        
        vbox.getChildren().addAll(label, firstPlayerTable, secondPlayerTable, genRandomCardsButton, startNewGameButton, winLabel);
        
        ((Group) scene.getRoot()).getChildren().add(vbox);
        stage.setScene(scene);
        stage.show();
    }
    
    private void prepareRankingStrategies() {
    	HighCardStrategy highCardStrategy = new HighCardStrategy();
        PairStrategy pairStrategy = new PairStrategy();
        pairStrategy.setHighCardStrategy(highCardStrategy);
        IRankingStrategy[] strategyArray = {pairStrategy, highCardStrategy};
        
        List<IRankingStrategy> orderedStrategies = new ArrayList<IRankingStrategy>(Arrays.asList(strategyArray));
        strategyComposition.setStrategies(orderedStrategies);
    }

	private void createTableWithPlayerCards(TableView<PokerCard> table, String columnName, ObservableList<PokerCard> data) {
		table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 
        TableColumn<PokerCard, String> firstPlayerCards = new TableColumn<>(columnName);
        firstPlayerCards.setMinWidth(100);
        firstPlayerCards.setCellValueFactory(
                new PropertyValueFactory<PokerCard, String>("pokerCardAsString"));
 
        table.setItems(data);
        table.getColumns().addAll(Arrays.asList(firstPlayerCards));
        
        // resize table depending on number of rows and cell size
        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));
	}
    
    private PokerHand getRandomPokerHand() {
    	PokerHand pokerHand = new PokerHand();
    	PokerCard[] pokerHandCards = new PokerCard[5];
    	for (int i = 0; i< pokerHandCards.length; i++) {
    		PokerCard randomCard = getRandomPokerCard();
    		if (randomCard != null) {
    			pokerHandCards[i] = randomCard;
    			isStartNewGameVisible = false;
    		} else {
    			isStartNewGameVisible = true;
    		}
    	}
    	pokerHand.setCards(pokerHandCards);
    	return pokerHand;
    }
    
    private PokerCard getRandomPokerCard() {
    	if (pokerDeckWithPlayingCards.size() < 50) {
    		int randomIdForCardSuite = (int) (Math.random()*4 + 1);
    		int randomIdForCardValue = (int) (Math.random()*13 + 1);
    		CardSuite cardSuite = getCardSuiteForId(randomIdForCardSuite);
    		CardValue cardValue = getCardValueForId(randomIdForCardValue);
    		PokerCard randomCard = new PokerCard(cardSuite, cardValue);
    		if (pokerDeckWithPlayingCards.contains(randomCard)) {
    			return getRandomPokerCard();
    		}
    		pokerDeckWithPlayingCards.add(randomCard);
    		return randomCard;
    	}
    	return null;
    }
    
    private CardSuite getCardSuiteForId(int id) {
    	CardSuite cardSuite;
    	switch(id) {
    	case 1: cardSuite = CardSuite.CLUB; break;
    	case 2: cardSuite = CardSuite.DIAMOND; break;
    	case 3: cardSuite = CardSuite.HEART; break;
    	default: cardSuite = CardSuite.SPADE; break;
    	}
    	return cardSuite;
    }
    
    private CardValue getCardValueForId(int id) {
    	CardValue cardValue;
    	switch(id) {
    	case 1: cardValue = CardValue.TWO; break;
    	case 2: cardValue = CardValue.THREE; break;
    	case 3: cardValue = CardValue.FOUR; break;
    	case 4: cardValue = CardValue.FIVE; break;
    	case 5: cardValue = CardValue.SIX; break;
    	case 6: cardValue = CardValue.SEVEN; break;
    	case 7: cardValue = CardValue.EIGHT; break;
    	case 8: cardValue = CardValue.NINE; break;
    	case 9: cardValue = CardValue.TEN; break;
    	case 10: cardValue = CardValue.JACK; break;
    	case 11: cardValue = CardValue.QUEEN; break;
    	case 12: cardValue = CardValue.KING; break;
    	default: cardValue = CardValue.ACE; break;
    	}
    	return cardValue;
    }
    
}