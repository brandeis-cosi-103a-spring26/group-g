package edu.brandeis.cosi103a.ip2;

/**
 * Enum representing the different phases of a turn.
 */
public enum TurnPhase {
    ACTION,     // Play action cards
    MONEY,      // Play money cards
    BUY,        // Buy cards
    CLEANUP,    // Discard and draw new hand
    GAIN        // Gain cards (can happen during other phases)
}
