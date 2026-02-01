package edu.brandeis.cosi103a.ip2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("Test Player");
    }

    @Test
    public void testPlayerCreation() {
        assertEquals("Test Player", player.getName());
        assertTrue(player.getDeck().isEmpty());
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void testAddToDeck() {
        Card bitcoin = new Card("Bitcoin", 0, 1, false);
        player.addToDeck(bitcoin);
        assertEquals(1, player.getDeck().size());
    }

    @Test
    public void testDrawHand() {
        for (int i = 0; i < 10; i++) {
            player.addToDeck(new Card("Bitcoin", 0, 1, false));
        }
        player.drawHand(5);
        assertEquals(5, player.getHand().size());
        assertEquals(5, player.getDeck().size());
    }

    @Test
    public void testPlayCoins() {
        player.addToDeck(new Card("Bitcoin", 0, 1, false));
        player.addToDeck(new Card("Bitcoin", 0, 1, false));
        player.addToDeck(new Card("Method", 2, 1, true));
        player.drawHand(3);
        
        int coins = player.playCoins();
        assertEquals(2, coins); // 2 bitcoins worth 1 each
        assertEquals(1, player.getHand().size()); // Method stays in hand
    }

    @Test
    public void testBuyCard() {
        Card module = new Card("Module", 5, 3, true);
        player.buyCard(module);
        assertEquals(1, player.getDiscard().size());
    }

    @Test
    public void testCalculateAPs() {
        player.addToDeck(new Card("Method", 2, 1, true));
        player.addToDeck(new Card("Module", 5, 3, true));
        player.addToDeck(new Card("Bitcoin", 0, 1, false));
        
        assertEquals(4, player.calculateAPs()); // 1 + 3 = 4 APs
    }

    @Test
    public void testEndTurn() {
        for (int i = 0; i < 10; i++) {
            player.addToDeck(new Card("Bitcoin", 0, 1, false));
        }
        player.drawHand(5);
        player.playCoins();
        player.endTurn();
        
        assertEquals(5, player.getHand().size()); // New hand of 5
    }

    @Test
    public void testShuffleDiscardIntoDeck() {
        // Start with only 3 cards in deck
        for (int i = 0; i < 3; i++) {
            player.addToDeck(new Card("Bitcoin", 0, 1, false));
        }
        // Add 5 cards to discard
        for (int i = 0; i < 5; i++) {
            player.buyCard(new Card("Bitcoin", 0, 1, false));
        }
        
        // Draw 5 should trigger shuffle of discard
        player.drawHand(5);
        assertEquals(5, player.getHand().size());
    }
}

