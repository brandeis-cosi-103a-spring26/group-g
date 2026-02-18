package edu.brandeis.cosi103a.ip2;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Represents the current state of the game visible to players.
 * This is an immutable snapshot of the game state.
 */
public class GameState {
    private final TurnPhase currentPhase;
    private final String currentPlayerName;
    private final ImmutableList<CardDefinition> currentPlayerHand;
    private final int currentPlayerActions;
    private final int currentPlayerBuys;
    private final int currentPlayerMoney;
    private final ImmutableMap<CardDefinition, Integer> availableCards;
    private final ImmutableList<String> playerNames;

    public GameState(TurnPhase currentPhase, 
                    String currentPlayerName,
                    ImmutableList<CardDefinition> currentPlayerHand,
                    int currentPlayerActions,
                    int currentPlayerBuys,
                    int currentPlayerMoney,
                    ImmutableMap<CardDefinition, Integer> availableCards,
                    ImmutableList<String> playerNames) {
        this.currentPhase = currentPhase;
        this.currentPlayerName = currentPlayerName;
        this.currentPlayerHand = currentPlayerHand;
        this.currentPlayerActions = currentPlayerActions;
        this.currentPlayerBuys = currentPlayerBuys;
        this.currentPlayerMoney = currentPlayerMoney;
        this.availableCards = availableCards;
        this.playerNames = playerNames;
    }

    public TurnPhase getCurrentPhase() {
        return currentPhase;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public ImmutableList<CardDefinition> getCurrentPlayerHand() {
        return currentPlayerHand;
    }

    public int getCurrentPlayerActions() {
        return currentPlayerActions;
    }

    public int getCurrentPlayerBuys() {
        return currentPlayerBuys;
    }

    public int getCurrentPlayerMoney() {
        return currentPlayerMoney;
    }

    public ImmutableMap<CardDefinition, Integer> getAvailableCards() {
        return availableCards;
    }

    public ImmutableList<String> getPlayerNames() {
        return playerNames;
    }
}
