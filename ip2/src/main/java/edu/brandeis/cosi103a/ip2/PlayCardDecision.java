package edu.brandeis.cosi103a.ip2;

/**
 * Decision to play a card from the player's hand.
 */
public class PlayCardDecision extends Decision {
    private final CardDefinition card;

    public PlayCardDecision(CardDefinition card) {
        this.card = card;
    }

    public CardDefinition getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "Play " + card.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PlayCardDecision)) return false;
        PlayCardDecision other = (PlayCardDecision) obj;
        return card.equals(other.card);
    }

    @Override
    public int hashCode() {
        return card.hashCode();
    }
}
