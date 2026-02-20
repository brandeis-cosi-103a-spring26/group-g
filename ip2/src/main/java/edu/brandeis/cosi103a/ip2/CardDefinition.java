package edu.brandeis.cosi103a.ip2;

/**
 * Represents the definition of a card with all its properties.
 * This is an immutable class that defines what a card is and what it does.
 */
public class CardDefinition {
    private final String name;
    private final int cost;
    private final int points;      // Automation Points for scoring
    private final int moneyValue;  // Coin value when played as money
    private final CardType type;
    
    // Action card properties
    private final int extraActions;  // How many extra actions this card provides
    private final int extraBuys;     // How many extra buys this card provides
    private final int extraMoney;    // Extra money this action card provides
    private final int extraCards;    // How many cards to draw

    public CardDefinition(String name, int cost, int points, int moneyValue, CardType type) {
        this(name, cost, points, moneyValue, type, 0, 0, 0, 0);
    }

    public CardDefinition(String name, int cost, int points, int moneyValue, CardType type,
                         int extraActions, int extraBuys, int extraMoney, int extraCards) {
        this.name = name;
        this.cost = cost;
        this.points = points;
        this.moneyValue = moneyValue;
        this.type = type;
        this.extraActions = extraActions;
        this.extraBuys = extraBuys;
        this.extraMoney = extraMoney;
        this.extraCards = extraCards;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getPoints() {
        return points;
    }

    public int getMoneyValue() {
        return moneyValue;
    }

    public CardType getType() {
        return type;
    }

    public int getExtraActions() {
        return extraActions;
    }

    public int getExtraBuys() {
        return extraBuys;
    }

    public int getExtraMoney() {
        return extraMoney;
    }

    public int getExtraCards() {
        return extraCards;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CardDefinition)) return false;
        CardDefinition other = (CardDefinition) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
