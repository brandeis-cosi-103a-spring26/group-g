package edu.brandeis.cosi103a.ip2;

/**
 * Decision to gain a card (adding it directly to discard pile without spending money).
 */
public class GainCardDecision extends Decision {
    private final CardDefinition card;

    public GainCardDecision(CardDefinition card) {
        this.card = card;
    }

    public CardDefinition getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "Gain " + card.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof GainCardDecision)) return false;
        GainCardDecision other = (GainCardDecision) obj;
        return card.equals(other.card);
    }

    @Override
    public int hashCode() {
        return card.hashCode();
    }
}
