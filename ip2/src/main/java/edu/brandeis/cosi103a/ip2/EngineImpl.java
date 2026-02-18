package edu.brandeis.cosi103a.ip2;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.*;

/**
 * Implementation of the Engine interface for Automation: The Game.
 */
public class EngineImpl implements Engine {
    private final List<Player> players;
    private final CardStacks cardStacks;
    private final Map<Player, PlayerState> playerStates;

    /**
     * Creates a new Engine with the given list of players.
     * 
     * @param players The list of players (must be between 1 and 4 players)
     * @throws IllegalArgumentException if the list contains more than 4 players
     */
    public EngineImpl(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Must have at least one player");
        }
        if (players.size() > 4) {
            throw new IllegalArgumentException("Cannot have more than 4 players");
        }
        
        this.players = new ArrayList<>(players);
        this.cardStacks = new CardStacks(players.size());
        this.playerStates = new HashMap<>();
        
        // Initialize player states
        for (Player player : players) {
            playerStates.put(player, new PlayerState(player));
        }
    }

    @Override
    public GameResult play() throws PlayerViolationException {
        // Play turns until game is over
        while (!cardStacks.isGameOver()) {
            for (Player player : players) {
                if (cardStacks.isGameOver()) {
                    break;
                }
                playTurn(player);
            }
        }
        
        // Calculate and return results
        return calculateResults();
    }

    /**
     * Plays a complete turn for the given player.
     */
    private void playTurn(Player player) throws PlayerViolationException {
        PlayerState state = playerStates.get(player);
        state.startTurn();
        
        // ACTION phase
        playActionPhase(player, state);
        
        // MONEY phase
        playMoneyPhase(player, state);
        
        // BUY phase
        playBuyPhase(player, state);
        
        // CLEANUP phase
        state.cleanup();
    }

    /**
     * Executes the ACTION phase of a turn.
     */
    private void playActionPhase(Player player, PlayerState state) throws PlayerViolationException {
        while (state.getActions() > 0) {
            List<Decision> options = new ArrayList<>();
            
            // Add options to play each unplayed action card
            List<CardDefinition> unplayedActions = getUnplayedActionCards(state);
            for (CardDefinition card : unplayedActions) {
                options.add(new PlayCardDecision(card));
            }
            
            // Always add option to end phase
            options.add(EndPhaseDecision.getInstance());
            
            GameState gameState = createGameState(TurnPhase.ACTION, player, state);
            Decision decision = getPlayerDecision(player, gameState, options);
            
            if (decision instanceof EndPhaseDecision) {
                break;
            } else if (decision instanceof PlayCardDecision) {
                PlayCardDecision playDecision = (PlayCardDecision) decision;
                playActionCard(state, playDecision.getCard());
            } else {
                throw new PlayerViolationException("Invalid decision type in ACTION phase");
            }
        }
    }

    /**
     * Executes the MONEY phase of a turn.
     */
    private void playMoneyPhase(Player player, PlayerState state) throws PlayerViolationException {
        while (true) {
            List<Decision> options = new ArrayList<>();
            
            // Add options to play each unplayed card (money or otherwise)
            Set<CardDefinition> unplayedCards = new HashSet<>(state.getHand());
            for (CardDefinition card : unplayedCards) {
                options.add(new PlayCardDecision(card));
            }
            
            // Always add option to end phase
            options.add(EndPhaseDecision.getInstance());
            
            GameState gameState = createGameState(TurnPhase.MONEY, player, state);
            Decision decision = getPlayerDecision(player, gameState, options);
            
            if (decision instanceof EndPhaseDecision) {
                break;
            } else if (decision instanceof PlayCardDecision) {
                PlayCardDecision playDecision = (PlayCardDecision) decision;
                playMoneyCard(state, playDecision.getCard());
            } else {
                throw new PlayerViolationException("Invalid decision type in MONEY phase");
            }
        }
    }

    /**
     * Executes the BUY phase of a turn.
     */
    private void playBuyPhase(Player player, PlayerState state) throws PlayerViolationException {
        while (state.getBuys() > 0) {
            List<Decision> options = new ArrayList<>();
            
            // Add options to buy each affordable card
            List<CardDefinition> affordableCards = getAffordableCards(state);
            for (CardDefinition card : affordableCards) {
                options.add(new BuyCardDecision(card));
            }
            
            // Always add option to end phase
            options.add(EndPhaseDecision.getInstance());
            
            GameState gameState = createGameState(TurnPhase.BUY, player, state);
            Decision decision = getPlayerDecision(player, gameState, options);
            
            if (decision instanceof EndPhaseDecision) {
                break;
            } else if (decision instanceof BuyCardDecision) {
                BuyCardDecision buyDecision = (BuyCardDecision) decision;
                buyCard(state, buyDecision.getCard());
            } else {
                throw new PlayerViolationException("Invalid decision type in BUY phase");
            }
        }
    }

    /**
     * Gets unplayed action cards from the player's hand.
     */
    private List<CardDefinition> getUnplayedActionCards(PlayerState state) {
        List<CardDefinition> actions = new ArrayList<>();
        for (CardDefinition card : state.getHand()) {
            if (card.getType() == CardType.ACTION) {
                actions.add(card);
            }
        }
        return actions;
    }

    /**
     * Plays an action card and applies its effects.
     */
    private void playActionCard(PlayerState state, CardDefinition card) {
        if (!state.playCard(card)) {
            return;
        }
        
        // Consume one action
        state.setActions(state.getActions() - 1);
        
        // Apply card effects
        state.setActions(state.getActions() + card.getExtraActions());
        state.setBuys(state.getBuys() + card.getExtraBuys());
        state.setMoney(state.getMoney() + card.getExtraMoney());
        state.drawCards(card.getExtraCards());
    }

    /**
     * Plays a money card (or any card for its money value).
     */
    private void playMoneyCard(PlayerState state, CardDefinition card) {
        if (!state.playCard(card)) {
            return;
        }
        
        // Add money value
        state.setMoney(state.getMoney() + card.getMoneyValue());
    }

    /**
     * Gets all cards that the player can afford to buy.
     */
    private List<CardDefinition> getAffordableCards(PlayerState state) {
        List<CardDefinition> affordable = new ArrayList<>();
        for (CardDefinition card : cardStacks.getAvailableCards()) {
            if (card.getCost() <= state.getMoney()) {
                affordable.add(card);
            }
        }
        return affordable;
    }

    /**
     * Buys a card for the player.
     */
    private void buyCard(PlayerState state, CardDefinition card) {
        if (cardStacks.takeCard(card)) {
            state.gainCard(card);
            state.setMoney(state.getMoney() - card.getCost());
            state.setBuys(state.getBuys() - 1);
        }
    }

    /**
     * Creates a GameState snapshot for the player.
     */
    private GameState createGameState(TurnPhase phase, Player player, PlayerState state) {
        ImmutableList<String> playerNames = players.stream()
                .map(Player::getName)
                .collect(ImmutableList.toImmutableList());
        
        return new GameState(
            phase,
            player.getName(),
            ImmutableList.copyOf(state.getHand()),
            state.getActions(),
            state.getBuys(),
            state.getMoney(),
            ImmutableMap.copyOf(cardStacks.getAllStacks()),
            playerNames
        );
    }

    /**
     * Gets a decision from the player and validates it.
     */
    private Decision getPlayerDecision(Player player, GameState gameState, List<Decision> options) 
            throws PlayerViolationException {
        ImmutableList<Decision> immutableOptions = ImmutableList.copyOf(options);
        
        Decision decision;
        try {
            decision = player.makeDecision(gameState, immutableOptions);
        } catch (Exception e) {
            throw new PlayerViolationException(
                "Player " + player.getName() + " threw exception while making decision", e);
        }
        
        // Validate that the decision is one of the provided options
        if (decision == null) {
            throw new PlayerViolationException(
                "Player " + player.getName() + " returned null decision");
        }
        
        if (!options.contains(decision)) {
            throw new PlayerViolationException(
                "Player " + player.getName() + " chose invalid decision: " + decision);
        }
        
        return decision;
    }

    /**
     * Calculates the final game results.
     */
    private GameResult calculateResults() {
        List<PlayerResult> results = new ArrayList<>();
        
        for (Player player : players) {
            PlayerState state = playerStates.get(player);
            int score = state.calculateScore();
            ImmutableList<CardDefinition> endingDeck = ImmutableList.copyOf(state.getAllCards());
            results.add(new PlayerResult(player.getName(), score, endingDeck));
        }
        
        // Sort by score (descending)
        results.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        
        return new GameResult(ImmutableList.copyOf(results));
    }
}
