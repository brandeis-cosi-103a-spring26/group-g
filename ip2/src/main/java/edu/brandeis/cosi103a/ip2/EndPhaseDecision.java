package edu.brandeis.cosi103a.ip2;

/**
 * Decision to end the current phase and move to the next phase.
 */
public class EndPhaseDecision extends Decision {
    private static final EndPhaseDecision INSTANCE = new EndPhaseDecision();

    private EndPhaseDecision() {
        // Private constructor for singleton
    }

    public static EndPhaseDecision getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "End Phase";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EndPhaseDecision;
    }

    @Override
    public int hashCode() {
        return EndPhaseDecision.class.hashCode();
    }
}
