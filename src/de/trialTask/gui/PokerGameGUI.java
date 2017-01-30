package de.trialTask.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.trialTask.model.CardSuite;
import de.trialTask.model.CardValue;
import de.trialTask.model.PokerCard;
import de.trialTask.model.PokerHand;
import de.trialTask.strategy.FlushStrategy;
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
	
	private static final String CARD_AS_STRING_PATTERN = "(\\w)+(\\s)(\\w)+";
	private static final String NEW_CARDS_GENERATOR_BUTTON_LABEL = "Random Cards";
	private static final String START_NEW_GAME_BUTTON_LABEL = "Start New Game";
	private static final String SECOND_COLUMN_NAME = "Second Player Cards";
	private static final String FIRST_COLUMN_NAME = "First Player Cards";
	private static final String BOX_LABEL = "Poker Hands";
	private static final String STAGE_TITLE = "PokerGame_TrialTask";
	private static final String EVALUATE_BUTTON = "Evaluate Cards";
	private static final PokerCard[] FIRST_PLAYER_CARDS = {
			new PokerCard(CardSuite.CLUB, CardValue.TWO),
			new PokerCard(CardSuite.CLUB, CardValue.THREE),
			new PokerCard(CardSuite.CLUB, CardValue.FOUR),
			new PokerCard(CardSuite.CLUB, CardValue.FIVE),
			new PokerCard(CardSuite.CLUB, CardValue.SIX)
	};
	private static final PokerCard[] SECOND_PLAYER_CARDS = {
			new PokerCard(CardSuite.DIAMOND, CardValue.FIVE),
			new PokerCard(CardSuite.DIAMOND, CardValue.SIX),
			new PokerCard(CardSuite.DIAMOND, CardValue.SEVEN),
			new PokerCard(CardSuite.DIAMOND, CardValue.EIGHT),
			new PokerCard(CardSuite.DIAMOND, CardValue.NINE)
	};
	
	private List<PokerCard> pokerDeck = new ArrayList<PokerCard>();
	
    private TableView<PokerCard> firstPlayerTable = new TableView<PokerCard>();
    private ObservableList<PokerCard> firstPlayerData = FXCollections.observableArrayList(FIRST_PLAYER_CARDS);
    
    private TableView<PokerCard> secondPlayerTable = new TableView<PokerCard>();
    private ObservableList<PokerCard> secondPlayerData = FXCollections.observableArrayList(SECOND_PLAYER_CARDS);
    
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
        fillPokerDeck();

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
    		case -1:
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
//        		pokerDeckWithPlayingCards.clear();
        		fillPokerDeck();
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
        				PokerCard card = ((PokerCard) t.getTableView().getItems().get(
        						t.getTablePosition().getRow()));
        				String inputToValidate = t.getNewValue();
        				PokerCard newPokerCard = getPokerCardFromParsedInput(inputToValidate);
        				if (newPokerCard != null) {
        					data.set(data.indexOf(card), newPokerCard);
        				} else {
        					// not valid input, so save the old value
        					data.set(data.indexOf(card), card);
        				}
        			}

					private PokerCard getPokerCardFromParsedInput(String newCardAsString) {
						PokerCard newPokerCard = null;
						if (newCardAsString.matches(CARD_AS_STRING_PATTERN)) {
							String[] cardElements = newCardAsString.split(" ");
							String suite = cardElements[0];
							String value = cardElements[1];
							CardSuite newCardSuite = null;
							CardValue newCardValue = null;
							for (CardSuite cardSuite : CardSuite.values()) {
								if (cardSuite.getName().equals(suite)) {
									newCardSuite = cardSuite;
									break;
								}
							}
							for (CardValue cardValue : CardValue.values()) {
								if (cardValue.getName().equals(value)) {
									newCardValue = cardValue;
									break;
								}
							}
							if (newCardSuite != null && newCardValue != null) {
								newPokerCard = new PokerCard(newCardSuite, newCardValue);
							}
						}
						return newPokerCard;
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
    		PokerCard randomCard = getRandomCard();
    		if (randomCard != null) {
    			pokerHandCards[i] = randomCard;
    		}
    	}
    	pokerHand.setCards(pokerHandCards);
    	return pokerHand;
    }
    
    private PokerCard getRandomCard() {
    	// last 2 cards are not for playing
    	if (pokerDeck.size() > 2) {
    		int indexForCard = (int) (Math.random()*pokerDeck.size());
    		return pokerDeck.remove(indexForCard);
    	}
    	return null;
    }
    
    private void fillPokerDeck() {
    	pokerDeck.clear();
    	for (CardSuite suite: CardSuite.values()) {
    		for (CardValue value: CardValue.values()) {
    			pokerDeck.add(new PokerCard(suite, value));
    		}
    	}
    }
    
}