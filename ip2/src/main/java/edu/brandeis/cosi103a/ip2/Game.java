package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main game logic for Automation: The Game.
 */
public class Game {
    private Player player1;
    private Player player2;
    private List<Card> supply;
    private int frameworksRemaining;
    private Random random;

    public Game() {
        this.random = new Random();
        this.supply = new ArrayList<>();
        initializeSupply();
        
        player1 = new HumanPlayer();
        player2 = new HumanPlayer();
        
        setupPlayer((BasePlayer)player1);
        setupPlayer((BasePlayer)player2);
    }

    private void initializeSupply() {
        // Automation cards
        for (int i = 0; i < 14; i++) {
            supply.add(new Card("Method", 2, 1, true));
        }
        for (int i = 0; i < 8; i++) {
            supply.add(new Card("Module", 5, 3, true));
        }
        for (int i = 0; i < 8; i++) {
            supply.add(new Card("Framework", 8, 6, true));
        }
        frameworksRemaining = 8;

        // Cryptocurrency cards
        for (int i = 0; i < 60; i++) {
            supply.add(new Card("Bitcoin", 0, 1, false));
        }
        for (int i = 0; i < 40; i++) {
            supply.add(new Card("Ethereum", 3, 2, false));
        }
        for (int i = 0; i < 30; i++) {
            supply.add(new Card("Dogecoin", 6, 3, false));
        }
    }

    private void setupPlayer(BasePlayer player) {
        // Give 7 Bitcoins and 3 Methods from supply
        int bitcoins = 0;
        int methods = 0;
        
        List<Card> toRemove = new ArrayList<>();
        for (Card card : supply) {
            if (card.getName().equals("Bitcoin") && bitcoins < 7) {
                player.addToDeck(card);
                toRemove.add(card);
                bitcoins++;
            } else if (card.getName().equals("Method") && methods < 3) {
                player.addToDeck(card);
                toRemove.add(card);
                methods++;
            }
            if (bitcoins == 7 && methods == 3) break;
        }
        supply.removeAll(toRemove);
        
        player.shuffleDeck();
        player.drawHand(5);
    }

    /**
     * Run the game until completion.
     */
    public void play() {
        Player currentPlayer = random.nextBoolean() ? player1 : player2;
        System.out.println("Starting player: " + currentPlayer.getName());
        
        int turn = 1;
        while (!isGameOver()) {
            System.out.println("\n=== Turn " + turn + ": " + currentPlayer.getName() + " ===");
            playTurn(currentPlayer);
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
            turn++;
        }
        
        announceWinner();
    }

    private void playTurn(Player player) {
        // Buy phase: play coins and buy a card
        int coins = ((BasePlayer)player).playCoins();
        System.out.println(player.getName() + " has " + coins + " coins");
        
        // Simple strategy: buy the best card we can afford
        Card toBuy = selectCardToBuy(coins);
        if (toBuy != null) {
            ((BasePlayer)player).buyCard(toBuy);
            supply.remove(toBuy);
            if (toBuy.getName().equals("Framework")) {
                frameworksRemaining--;
            }
            System.out.println(player.getName() + " buys " + toBuy);
        } else {
            System.out.println(player.getName() + " cannot afford anything");
        }
        
        // Cleanup phase
        ((BasePlayer)player).endTurn();
    }

    private Card selectCardToBuy(int coins) {
        // Priority: Framework > Module > Dogecoin > Method > Ethereum > Bitcoin
        Card best = null;
        for (Card card : supply) {
            if (card.getCost() <= coins) {
                if (best == null || isBetterBuy(card, best)) {
                    best = card;
                }
            }
        }
        return best;
    }

    private int getCardPriority(Card card) {
        // Priority: Framework > Module > Dogecoin > Method > Ethereum > Bitcoin
        switch (card.getName()) {
            case "Framework": return 6;
            case "Module": return 5;
            case "Dogecoin": return 4;
            case "Method": return 3;
            case "Ethereum": return 2;
            case "Bitcoin": return 1;
            default: return 0;
        }
    }

    private boolean isBetterBuy(Card a, Card b) {
        return getCardPriority(a) > getCardPriority(b);
    }

    public boolean isGameOver() {
        return frameworksRemaining == 0;
    }

    private void announceWinner() {
        int p1APs = ((BasePlayer)player1).calculateAPs();
        int p2APs = ((BasePlayer)player2).calculateAPs();
        
        System.out.println("\n=== GAME OVER ===");
        System.out.println(player1.getName() + " APs: " + p1APs);
        System.out.println(player2.getName() + " APs: " + p2APs);
        
        if (p1APs > p2APs) {
            System.out.println("Winner: " + player1.getName());
        } else if (p2APs > p1APs) {
            System.out.println("Winner: " + player2.getName());
        } else {
            System.out.println("It's a tie!");
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public List<Card> getSupply() {
        return supply;
    }

    public int getFrameworksRemaining() {
        return frameworksRemaining;
    }
}

