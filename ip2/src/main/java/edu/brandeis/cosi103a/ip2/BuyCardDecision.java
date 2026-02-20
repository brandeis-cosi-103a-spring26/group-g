package edu.brandeis.cosi103a.ip2;

/**
 * Decision to buy a card from the supply.
 */
public class BuyCardDecision extends Decision {
    private final CardDefinition card;

    public BuyCardDecision(CardDefinition card) {
        this.card = card;
    }

    public CardDefinition getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "Buy " + card.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BuyCardDecision)) return false;
        BuyCardDecision other = (BuyCardDecision) obj;
        return card.equals(other.card);
    }

    @Override
    public int hashCode() {
        return card.hashCode();
    }
}
