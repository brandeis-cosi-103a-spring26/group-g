package edu.brandeis.cosi103a.ip2;

/**
 * Constants for all card definitions in the game.
 */
public class Cards {
    // Money cards
    public static final CardDefinition BITCOIN = new CardDefinition("Bitcoin", 0, 0, 1, CardType.MONEY);
    public static final CardDefinition ETHEREUM = new CardDefinition("Ethereum", 3, 0, 2, CardType.MONEY);
    public static final CardDefinition DOGECOIN = new CardDefinition("Dogecoin", 6, 0, 3, CardType.MONEY);
    
    // Automation cards (give points)
    public static final CardDefinition METHOD = new CardDefinition("Method", 2, 1, 0, CardType.AUTOMATION);
    public static final CardDefinition MODULE = new CardDefinition("Module", 5, 3, 0, CardType.AUTOMATION);
    public static final CardDefinition FRAMEWORK = new CardDefinition("Framework", 8, 6, 0, CardType.AUTOMATION);
    
    // Bug cards (negative points)
    public static final CardDefinition BUG = new CardDefinition("Bug", 0, -1, 0, CardType.BUG);
    
    // Action cards (name, cost, points, moneyValue, type, extraActions, extraBuys, extraMoney, extraCards)
    public static final CardDefinition REFACTOR = new CardDefinition("Refactor", 2, 0, 0, CardType.ACTION, 
                                                                     1, 0, 0, 1);  // +1 action, +1 card
    public static final CardDefinition CODE_REVIEW = new CardDefinition("Code Review", 5, 0, 0, CardType.ACTION, 
                                                                        0, 1, 2, 0);  // +1 buy, +2 money
    public static final CardDefinition EVERGREEN_TEST = new CardDefinition("Evergreen Test", 4, 0, 0, CardType.ACTION, 
                                                                           0, 0, 0, 3);  // +3 cards

    private Cards() {
        // Utility class, prevent instantiation
    }
}
