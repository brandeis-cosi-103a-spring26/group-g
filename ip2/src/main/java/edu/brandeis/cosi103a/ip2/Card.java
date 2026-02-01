package edu.brandeis.cosi103a.ip2;

/**
 * Represents a card in Automation: The Game.
 */
public class Card {
    private String name;
    private int cost;
    private int value;
    private boolean isAutomation; // true = Automation card (gives APs), false = Cryptocurrency (gives coins)

    public Card(String name, int cost, int value, boolean isAutomation) {
        this.name = name;
        this.cost = cost;
        this.value = value;
        this.isAutomation = isAutomation;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getValue() {
        return value;
    }

    public boolean isAutomation() {
        return isAutomation;
    }

    @Override
    public String toString() {
        return name + " (cost:" + cost + ", value:" + value + ")";
    }
}

