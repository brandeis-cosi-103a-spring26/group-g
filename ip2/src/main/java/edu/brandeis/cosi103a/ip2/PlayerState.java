package edu.brandeis.cosi103a.ip2;

import java.util.*;

/**
 * Represents the complete state of a player during the game.
 * This includes their deck, hand, discard pile, and played cards.
 */
public class PlayerState {
    private final Player player;
    private final List<CardDefinition> deck;
    private final List<CardDefinition> hand;
    private final List<CardDefinition> discard;
    private final List<CardDefinition> played;
    
    // Turn resources
    private int actions;
    private int buys;
    private int money;

    public PlayerState(Player player) {
        this.player = player;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discard = new ArrayList<>();
        this.played = new ArrayList<>();
        
        // Initialize starting deck: 7 Bitcoin, 3 Method
        for (int i = 0; i < 7; i++) {
            deck.add(Cards.BITCOIN);
        }
        for (int i = 0; i < 3; i++) {
            deck.add(Cards.METHOD);
        }
        
        shuffleDeck();
        drawCards(5);
    }

    public Player getPlayer() {
        return player;
    }

    public List<CardDefinition> getHand() {
        return new ArrayList<>(hand);
    }

    public List<CardDefinition> getDeck() {
        return new ArrayList<>(deck);
    }

    public List<CardDefinition> getDiscard() {
        return new ArrayList<>(discard);
    }

    public List<CardDefinition> getPlayed() {
        return new ArrayList<>(played);
    }

    public int getActions() {
        return actions;
    }

    public int getBuys() {
        return buys;
    }

    public int getMoney() {
        return money;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public void setBuys(int buys) {
        this.buys = buys;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Starts a new turn by resetting resources.
     */
    public void startTurn() {
        actions = 1;
        buys = 1;
        money = 0;
    }

    /**
     * Plays a card from hand.
     */
    public boolean playCard(CardDefinition card) {
        if (hand.remove(card)) {
            played.add(card);
            return true;
        }
        return false;
    }

    /**
     * Gains a card (adds to discard pile).
     */
    public void gainCard(CardDefinition card) {
        discard.add(card);
    }

    /**
     * Draws cards from deck to hand, shuffling discard if needed.
     */
    public void drawCards(int count) {
        for (int i = 0; i < count; i++) {
            if (deck.isEmpty()) {
                deck.addAll(discard);
                discard.clear();
                shuffleDeck();
            }
            if (!deck.isEmpty()) {
                hand.add(deck.remove(0));
            }
        }
    }

    /**
     * Shuffles the deck.
     */
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * Ends the turn: discard hand and played cards, draw new hand.
     */
    public void cleanup() {
        discard.addAll(hand);
        discard.addAll(played);
        hand.clear();
        played.clear();
        drawCards(5);
    }

    /**
     * Calculates the total score (points) for this player.
     */
    public int calculateScore() {
        int score = 0;
        List<CardDefinition> allCards = new ArrayList<>();
        allCards.addAll(deck);
        allCards.addAll(hand);
        allCards.addAll(discard);
        allCards.addAll(played);
        
        for (CardDefinition card : allCards) {
            score += card.getPoints();
        }
        
        return score;
    }

    /**
     * Gets all cards owned by this player (for final results).
     */
    public List<CardDefinition> getAllCards() {
        List<CardDefinition> allCards = new ArrayList<>();
        allCards.addAll(deck);
        allCards.addAll(hand);
        allCards.addAll(discard);
        allCards.addAll(played);
        return allCards;
    }
}
