package edu.brandeis.cosi103a.ip2;

import com.google.common.collect.ImmutableList;

/**
 * A simple player implementation that makes basic strategic decisions.
 * This player follows a simple strategy:
 * - In ACTION phase: Play all action cards
 * - In MONEY phase: Play all money cards
 * - In BUY phase: Buy the best card affordable (Framework > Module > Dogecoin > Method > Ethereum > Bitcoin)
 */
public class SimplePlayer implements Player {
    private final String name;

    public SimplePlayer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Decision makeDecision(GameState state, ImmutableList<Decision> options) {
        if (options.isEmpty()) {
            return null;
        }

        // If there's only one option or one is EndPhase, prefer action decisions
        if (options.size() == 1) {
            return options.get(0);
        }

        switch (state.getCurrentPhase()) {
            case ACTION:
                // Play action cards if available, otherwise end phase
                return findBestPlayCardDecision(options, CardType.ACTION);
                
            case MONEY:
                // Play money cards if available, otherwise end phase
                Decision moneyDecision = findBestPlayCardDecision(options, CardType.MONEY);
                if (moneyDecision == null) {
                    // Try playing any other card for money value
                    moneyDecision = findFirstPlayCardDecision(options);
                }
                return moneyDecision != null ? moneyDecision : getEndPhaseDecision(options);
                
            case BUY:
                // Buy the best card we can afford
                return findBestBuyDecision(options);
                
            case GAIN:
                // Gain the best card available
                return findBestGainDecision(options);
                
            default:
                return options.get(0);
        }
    }

    private Decision findBestPlayCardDecision(ImmutableList<Decision> options, CardType preferredType) {
        for (Decision option : options) {
            if (option instanceof PlayCardDecision) {
                PlayCardDecision playDecision = (PlayCardDecision) option;
                if (playDecision.getCard().getType() == preferredType) {
                    return option;
                }
            }
        }
        return getEndPhaseDecision(options);
    }

    private Decision findFirstPlayCardDecision(ImmutableList<Decision> options) {
        for (Decision option : options) {
            if (option instanceof PlayCardDecision) {
                return option;
            }
        }
        return null;
    }

    private Decision findBestBuyDecision(ImmutableList<Decision> options) {
        Decision bestBuy = null;
        int bestPriority = -1;

        for (Decision option : options) {
            if (option instanceof BuyCardDecision) {
                BuyCardDecision buyDecision = (BuyCardDecision) option;
                int priority = getCardPriority(buyDecision.getCard());
                if (priority > bestPriority) {
                    bestPriority = priority;
                    bestBuy = option;
                }
            }
        }

        return bestBuy != null ? bestBuy : getEndPhaseDecision(options);
    }

    private Decision findBestGainDecision(ImmutableList<Decision> options) {
        Decision bestGain = null;
        int bestPriority = -1;

        for (Decision option : options) {
            if (option instanceof GainCardDecision) {
                GainCardDecision gainDecision = (GainCardDecision) option;
                int priority = getCardPriority(gainDecision.getCard());
                if (priority > bestPriority) {
                    bestPriority = priority;
                    bestGain = option;
                }
            }
        }

        return bestGain != null ? bestGain : options.get(0);
    }

    private Decision getEndPhaseDecision(ImmutableList<Decision> options) {
        for (Decision option : options) {
            if (option instanceof EndPhaseDecision) {
                return option;
            }
        }
        return options.get(0);
    }

    private int getCardPriority(CardDefinition card) {
        // Priority: Framework > Module > Dogecoin > Code Review > Evergreen Test > Method > Refactor > Ethereum > Bitcoin > Bug
        switch (card.getName()) {
            case "Framework":
                return 10;
            case "Module":
                return 9;
            case "Dogecoin":
                return 8;
            case "Code Review":
                return 7;
            case "Evergreen Test":
                return 6;
            case "Method":
                return 5;
            case "Refactor":
                return 4;
            case "Ethereum":
                return 3;
            case "Bitcoin":
                return 2;
            case "Bug":
                return 1;
            default:
                return 0;
        }
    }
}
