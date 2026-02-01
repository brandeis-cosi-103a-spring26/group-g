package edu.brandeis.cosi103a.ip2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testAutomationCard() {
        Card method = new Card("Method", 2, 1, true);
        assertEquals("Method", method.getName());
        assertEquals(2, method.getCost());
        assertEquals(1, method.getValue());
        assertTrue(method.isAutomation());
    }

    @Test
    public void testCryptocurrencyCard() {
        Card bitcoin = new Card("Bitcoin", 0, 1, false);
        assertEquals("Bitcoin", bitcoin.getName());
        assertEquals(0, bitcoin.getCost());
        assertEquals(1, bitcoin.getValue());
        assertFalse(bitcoin.isAutomation());
    }

    @Test
    public void testModuleCard() {
        Card module = new Card("Module", 5, 3, true);
        assertEquals(5, module.getCost());
        assertEquals(3, module.getValue());
        assertTrue(module.isAutomation());
    }

    @Test
    public void testFrameworkCard() {
        Card framework = new Card("Framework", 8, 6, true);
        assertEquals(8, framework.getCost());
        assertEquals(6, framework.getValue());
        assertTrue(framework.isAutomation());
    }

    @Test
    public void testEthereumCard() {
        Card ethereum = new Card("Ethereum", 3, 2, false);
        assertEquals(3, ethereum.getCost());
        assertEquals(2, ethereum.getValue());
        assertFalse(ethereum.isAutomation());
    }

    @Test
    public void testDogecoinCard() {
        Card dogecoin = new Card("Dogecoin", 6, 3, false);
        assertEquals(6, dogecoin.getCost());
        assertEquals(3, dogecoin.getValue());
        assertFalse(dogecoin.isAutomation());
    }

    @Test
    public void testToString() {
        Card card = new Card("Bitcoin", 0, 1, false);
        assertEquals("Bitcoin (cost:0, value:1)", card.toString());
    }
}

