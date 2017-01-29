package de.trialTask.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;
import de.trialTask.strategy.FlushStrategy;
import de.trialTask.strategy.FlushStrategyTest;
import de.trialTask.strategy.FourOfAKindStrategy;
import de.trialTask.strategy.FullHouseStrategy;
import de.trialTask.strategy.HighCardStrategy;
import de.trialTask.strategy.IRankingStrategy;
import de.trialTask.strategy.PairStrategy;
import de.trialTask.strategy.StraightFlushStrategy;
import de.trialTask.strategy.StraightStrategy;
import de.trialTask.strategy.StrategyComposition;
import de.trialTask.strategy.ThreeOfAKindStrategy;
import de.trialTask.strategy.TwoPairsStrategy;
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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class PokerGameGUI extends Application {
	
	private static final String NEW_CARDS_GENERATOR_BUTTON_LABEL = "Random Cards";
	private static final String START_NEW_GAME_BUTTON_LABEL = "Start New Game";
	private static final String SECOND_COLUMN_NAME = "Second Player Cards";
	private static final String FIRST_COLUMN_NAME = "First Player Cards";
	private static final String BOX_LABEL = "Poker Hands";
	private static final String STAGE_TITLE = "PokerGame_TrialTask";
	private static final String EVALUATE_BUTTON = "Evaluate Cards";
	
	private List<PokerCard> pokerDeckWithPlayingCards = new ArrayList<PokerCard>();
	
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
        stage.setTitle(STAGE_TITLE);
        stage.setWidth(450);
        stage.setHeight(550);
 
        final Label label = new Label(BOX_LABEL);
        label.setFont(new Font("Arial", 20));
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
 
        prepareRankingStrategies();

        createTableWithPlayerCards(firstPlayerTable, FIRST_COLUMN_NAME, firstPlayerData);
        createTableWithPlayerCards(secondPlayerTable, SECOND_COLUMN_NAME, secondPlayerData);
        
        Label msgLabel = new Label();
        msgLabel.setFont(new Font("Arial", 20));
        
        Button genRandomCardsButton = getRandomCardsButton(msgLabel);
        Button evaluateCardsButton = getEvaluatePokerHandsButton(msgLabel);
        Button startNewGameButton = getStartNewGameButton(msgLabel);
        
        vbox.getChildren().addAll(label, firstPlayerTable, secondPlayerTable, genRandomCardsButton, evaluateCardsButton, startNewGameButton, msgLabel);
        
        ((Group) scene.getRoot()).getChildren().add(vbox);
        stage.setScene(scene);
        stage.show();
    }

	private Button getRandomCardsButton(Label msgLabel) {
		Button genRandomCardsButton = new Button(NEW_CARDS_GENERATOR_BUTTON_LABEL);
        genRandomCardsButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		msgLabel.setText("");
        		firstPlayerData.clear();
        		PokerHand firstPlayerPokerHand = getRandomPokerHand();
        		for (PokerCard card: firstPlayerPokerHand.getCards()) {
        			if (card != null) {
        				firstPlayerData.add(card);
        			}
        		}
        		firstPlayerTable.setItems(firstPlayerData);
        		
        		secondPlayerData.clear();
        		PokerHand secondPlayerPokerHand = getRandomPokerHand();
        		for (PokerCard card: secondPlayerPokerHand.getCards()) {
        			if (card != null) {
        				secondPlayerData.add(card);
        			}
        		}
        		secondPlayerTable.setItems(secondPlayerData);
        		
        		if (firstPlayerData.isEmpty() && secondPlayerData.isEmpty()) {
        			msgLabel.setText("No more cards for this poker hand!");
        		}
        	}
        });
		return genRandomCardsButton;
	}
	
	private Button getEvaluatePokerHandsButton(Label msgLabel) {
		Button evaluateButton = new Button(EVALUATE_BUTTON);
		evaluateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				evaluatePokerHands(msgLabel);
			}
		});
		return evaluateButton;
	}

	private void evaluatePokerHands(Label winLabel) {
		// If cards still available find the winner
		if (!(firstPlayerData.isEmpty() || secondPlayerData.isEmpty())) {
			PokerHand firstPlayerPokerHand = new PokerHand();
			firstPlayerPokerHand.setCards(firstPlayerData.toArray(new PokerCard[5]));
			PokerHand secondPlayerPokerHand = new PokerHand();
			secondPlayerPokerHand.setCards(secondPlayerData.toArray(new PokerCard[5]));
			
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
	}
	
	private Button getStartNewGameButton(Label msgLabel) {
		Button startNewGameButton = new Button(START_NEW_GAME_BUTTON_LABEL);
        startNewGameButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		pokerDeckWithPlayingCards.clear();
        		msgLabel.setText("Game started! Get new poker cards!");
        	}
        });
		return startNewGameButton;
	}
    
    /**
     * Set the strategies by which the poker hands are ranked.
     * Order is important and prescribed as follows:
     * First element in the list is the strategy with higher ranking.
     * Last element in the list is the strategy with lowest ranking.  
     */
    private void prepareRankingStrategies() {
    	HighCardStrategy highCardStrategy = new HighCardStrategy();
        PairStrategy pairStrategy = new PairStrategy();
        TwoPairsStrategy twoPairsStrategy = new TwoPairsStrategy();
        ThreeOfAKindStrategy threeOfAKindStrategy = new ThreeOfAKindStrategy();
        StraightStrategy straightStrategy = new StraightStrategy();
        FlushStrategy flushStrategy = new FlushStrategy(highCardStrategy);
        FullHouseStrategy fullHouseStrategy = new FullHouseStrategy();
        FourOfAKindStrategy fourOfAKindStrategy = new FourOfAKindStrategy();
        StraightFlushStrategy straightFlushStrategy = new StraightFlushStrategy(
        		straightStrategy, flushStrategy);
        
        IRankingStrategy[] strategyArray = 
        	{straightFlushStrategy, fourOfAKindStrategy, fullHouseStrategy, flushStrategy, 
        			straightStrategy, threeOfAKindStrategy, twoPairsStrategy, pairStrategy, 
        			highCardStrategy};
        
        List<IRankingStrategy> orderedStrategies = new ArrayList<IRankingStrategy>(Arrays.asList(strategyArray));
        strategyComposition.setStrategies(orderedStrategies);
    }

	private void createTableWithPlayerCards(TableView<PokerCard> table, String columnName, ObservableList<PokerCard> data) {
		table.setEditable(true);
//		table.setSelectionModel(null);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 
        TableColumn<PokerCard, String> playerCardsColumn = new TableColumn<>(columnName);
        playerCardsColumn.setMinWidth(100);
        playerCardsColumn.setCellValueFactory(
                new PropertyValueFactory<PokerCard, String>("pokerCardAsString"));
        playerCardsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        playerCardsColumn.setOnEditCommit(
        		new EventHandler<CellEditEvent<PokerCard, String>>() {
        			@Override
        			public void handle(CellEditEvent<PokerCard, String> t) {
//        				PokerCard card = ((PokerCard) t.getTableView().getItems().get(
//        						t.getTablePosition().getRow()));
//        				String newCardAsString = t.getNewValue();
//        				PokerCard newCard = new PokerCard(newCardAsString);
//        				System.out.println(newCard.toString());
        			}
        		});
        
        table.setItems(data);
        table.getColumns().addAll(Arrays.asList(playerCardsColumn));
        
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