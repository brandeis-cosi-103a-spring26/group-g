package edu.brandeis.cosi103a.ip2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testGameInitialization() {
        assertNotNull(game.getPlayer1());
        assertNotNull(game.getPlayer2());
        assertEquals(8, game.getFrameworksRemaining());
    }

    @Test
    public void testPlayersHaveStarterDecks() {
        // Each player starts with 7 Bitcoins + 3 Methods = 10 cards total
        // 5 in hand, 5 in deck
        Player p1 = game.getPlayer1();
        assertEquals(5, p1.getHand().size());
        assertEquals(5, p1.getDeck().size());
    }

    @Test
    public void testSupplySetup() {
        // Supply should have cards minus the starter decks
        // Original: 14 Methods, 60 Bitcoins, 8 Modules, 8 Frameworks, 40 Ethereum, 30 Dogecoin
        // After setup: -6 Methods, -14 Bitcoins distributed to players
        int methods = 0, bitcoins = 0, modules = 0, frameworks = 0, ethereum = 0, dogecoin = 0;
        
        for (Card card : game.getSupply()) {
            switch (card.getName()) {
                case "Method": methods++; break;
                case "Bitcoin": bitcoins++; break;
                case "Module": modules++; break;
                case "Framework": frameworks++; break;
                case "Ethereum": ethereum++; break;
                case "Dogecoin": dogecoin++; break;
            }
        }
        
        assertEquals(8, methods);      // 14 - 6 (3 per player)
        assertEquals(46, bitcoins);    // 60 - 14 (7 per player)
        assertEquals(8, modules);
        assertEquals(8, frameworks);
        assertEquals(40, ethereum);
        assertEquals(30, dogecoin);
    }

    @Test
    public void testGameNotOverAtStart() {
        assertFalse(game.isGameOver());
    }

    @Test
    public void testStarterDeckAPs() {
        // Each player starts with 3 Methods (1 AP each) = 3 APs
        assertEquals(3, game.getPlayer1().calculateAPs());
        assertEquals(3, game.getPlayer2().calculateAPs());
    }
}

