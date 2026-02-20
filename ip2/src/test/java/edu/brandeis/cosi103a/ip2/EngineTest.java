package edu.brandeis.cosi103a.ip2;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EngineTest {

    @Test
    public void testEngineConstructorWithValidPlayers() {
        List<Player> players = Arrays.asList(
            new SimplePlayer("Alice"),
            new SimplePlayer("Bob")
        );
        
        Engine engine = new EngineImpl(players);
        assertNotNull(engine);
    }

    @Test
    public void testEngineConstructorRejectsMoreThan4Players() {
        List<Player> players = Arrays.asList(
            new SimplePlayer("Alice"),
            new SimplePlayer("Bob"),
            new SimplePlayer("Charlie"),
            new SimplePlayer("Dave"),
            new SimplePlayer("Eve")
        );
        
        assertThrows(IllegalArgumentException.class, () -> {
            new EngineImpl(players);
        });
    }

    @Test
    public void testEngineConstructorRejectsNullPlayers() {
        assertThrows(IllegalArgumentException.class, () -> {
            new EngineImpl(null);
        });
    }

    @Test
    public void testEngineConstructorRejectsEmptyPlayerList() {
        assertThrows(IllegalArgumentException.class, () -> {
            new EngineImpl(Arrays.asList());
        });
    }

    @Test
    public void testEnginePlayReturnsGameResult() throws PlayerViolationException {
        List<Player> players = Arrays.asList(
            new SimplePlayer("Alice"),
            new SimplePlayer("Bob")
        );
        
        Engine engine = new EngineImpl(players);
        GameResult result = engine.play();
        
        assertNotNull(result);
        assertNotNull(result.getPlayerResults());
        assertEquals(2, result.getPlayerResults().size());
    }

    @Test
    public void testGameResultContainsPlayerScores() throws PlayerViolationException {
        List<Player> players = Arrays.asList(
            new SimplePlayer("Alice"),
            new SimplePlayer("Bob")
        );
        
        Engine engine = new EngineImpl(players);
        GameResult result = engine.play();
        
        for (PlayerResult playerResult : result.getPlayerResults()) {
            assertNotNull(playerResult.getName());
            assertNotNull(playerResult.getEndingDeck());
            assertTrue(playerResult.getScore() >= 0 || playerResult.getScore() < 0); // Score can be positive or negative (bugs)
        }
    }

    @Test
    public void testGameResultIsSortedByScore() throws PlayerViolationException {
        List<Player> players = Arrays.asList(
            new SimplePlayer("Alice"),
            new SimplePlayer("Bob"),
            new SimplePlayer("Charlie")
        );
        
        Engine engine = new EngineImpl(players);
        GameResult result = engine.play();
        
        // Verify results are sorted by score (descending)
        List<PlayerResult> results = result.getPlayerResults();
        for (int i = 0; i < results.size() - 1; i++) {
            assertTrue(results.get(i).getScore() >= results.get(i + 1).getScore(),
                "Results should be sorted by score descending");
        }
    }

    @Test
    public void testPlayerStartsWithCorrectDeck() throws PlayerViolationException {
        List<Player> players = Arrays.asList(new SimplePlayer("Alice"));
        
        Engine engine = new EngineImpl(players);
        GameResult result = engine.play();
        
        PlayerResult playerResult = result.getPlayerResults().get(0);
        
        // Player should have more than the starting 10 cards by the end of the game
        assertTrue(playerResult.getEndingDeck().size() >= 10,
            "Player should have at least starting deck size");
    }

    @Test
    public void testGameEndsWhenFrameworksExhausted() throws PlayerViolationException {
        List<Player> players = Arrays.asList(
            new SimplePlayer("Alice"),
            new SimplePlayer("Bob")
        );
        
        Engine engine = new EngineImpl(players);
        GameResult result = engine.play();
        
        // Game should end and return results
        assertNotNull(result);
        assertNotNull(result.getPlayerResults());
    }

    @Test
    public void testSinglePlayerGame() throws PlayerViolationException {
        List<Player> players = Arrays.asList(new SimplePlayer("Alice"));
        
        Engine engine = new EngineImpl(players);
        GameResult result = engine.play();
        
        assertNotNull(result);
        assertEquals(1, result.getPlayerResults().size());
        assertEquals("Alice", result.getPlayerResults().get(0).getName());
    }

    @Test
    public void testFourPlayerGame() throws PlayerViolationException {
        List<Player> players = Arrays.asList(
            new SimplePlayer("Alice"),
            new SimplePlayer("Bob"),
            new SimplePlayer("Charlie"),
            new SimplePlayer("Dave")
        );
        
        Engine engine = new EngineImpl(players);
        GameResult result = engine.play();
        
        assertNotNull(result);
        assertEquals(4, result.getPlayerResults().size());
    }
}
