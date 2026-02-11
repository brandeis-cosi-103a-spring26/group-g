package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a base player in Automation: The Game.
 */
public class BasePlayer {
    private String name;
    private List<Card> deck;
    private List<Card> hand;
    private List<Card> discard;
    private List<Card> playedCards;

    public BasePlayer(String name) {
        this.name = name;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discard = new ArrayList<>();
        this.playedCards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addToDeck(Card card) {
        deck.add(card);
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * Draw cards from deck to hand.
     */
    public void drawHand(int count) {
        for (int i = 0; i < count; i++) {
            if (deck.isEmpty()) {
                // Shuffle discard pile into deck
                deck.addAll(discard);
                discard.clear();
                Collections.shuffle(deck);
            }
            if (!deck.isEmpty()) {
                hand.add(deck.remove(0));
            }
        }
    }

    /**
     * Play all cryptocurrency cards from hand and return total coin value.
     */
    public int playCoins() {
        int coins = 0;
        List<Card> toPlay = new ArrayList<>();
        for (Card card : hand) {
            if (!card.isAutomation()) {
                coins += card.getValue();
                toPlay.add(card);
            }
        }
        hand.removeAll(toPlay);
        playedCards.addAll(toPlay);
        return coins;
    }

    /**
     * Buy a card and add it to discard pile.
     */
    public void buyCard(Card card) {
        discard.add(card);
    }

    /**
     * End turn: discard hand and played cards, draw new hand.
     */
    public void endTurn() {
        discard.addAll(hand);
        discard.addAll(playedCards);
        hand.clear();
        playedCards.clear();
        drawHand(5);
    }

    /**
     * Calculate total Automation Points in player's deck (all cards they own).
     */
    public int calculateAPs() {
        int aps = 0;
        for (Card card : deck) {
            if (card.isAutomation()) {
                aps += card.getValue();
            }
        }
        for (Card card : hand) {
            if (card.isAutomation()) {
                aps += card.getValue();
            }
        }
        for (Card card : discard) {
            if (card.isAutomation()) {
                aps += card.getValue();
            }
        }
        for (Card card : playedCards) {
            if (card.isAutomation()) {
                aps += card.getValue();
            }
        }
        return aps;
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public List<Card> getDiscard() {
        return discard;
    }
}
