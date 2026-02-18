# Engine Implementation for Automation: The Game

This document describes the Engine implementation for Automation: The Game.

## Overview

The Engine is responsible for orchestrating the complete game flow between players, managing game state, enforcing rules, and determining the winner.

## Architecture

### Core Components

#### 1. Engine Interface (`Engine.java`)
The main interface that defines the game execution contract:
```java
public interface Engine {
    GameResult play() throws PlayerViolationException;
}
```

#### 2. Engine Implementation (`EngineImpl.java`)
The concrete implementation that handles:
- Player turn management
- Phase transitions (ACTION, MONEY, BUY, CLEANUP, GAIN)
- Decision validation
- Game state management
- Score calculation

#### 3. Supporting Classes

**Decision Types:**
- `PlayCardDecision` - Decision to play a card from hand
- `BuyCardDecision` - Decision to buy a card from supply
- `GainCardDecision` - Decision to gain a card (free)
- `EndPhaseDecision` - Decision to end the current phase

**Card System:**
- `CardDefinition` - Immutable card definition with properties
- `CardType` - Enum for card types (MONEY, AUTOMATION, ACTION, BUG)
- `Cards` - Constants for all card definitions
- `CardStacks` - Manages the supply of available cards

**Game State:**
- `PlayerState` - Manages individual player state (deck, hand, discard, played cards)
- `GameState` - Immutable snapshot of game state for players to observe
- `TurnPhase` - Enum for turn phases

**Results:**
- `GameResult` - Contains results for all players, sorted by score
- `PlayerResult` - Individual player result (name, score, ending deck)

**Exceptions:**
- `PlayerViolationException` - Thrown when a player violates rules

## Game Flow

### 1. Initialization

The Engine is constructed with a list of 1-4 players:

```java
List<Player> players = Arrays.asList(
    new SimplePlayer("Alice"),
    new SimplePlayer("Bob")
);
Engine engine = new EngineImpl(players);
```

Each player starts with:
- 7 Bitcoin cards
- 3 Method cards
- 5 cards in hand (shuffled from starting deck)

### 2. Turn Structure

Each turn consists of four phases:

#### ACTION Phase
- Player starts with 1 action
- Engine prompts with `PlayCardDecision` for each unplayed Action card in hand
- Always includes `EndPhaseDecision` option
- Playing an action card:
  - Consumes 1 action
  - Applies card effects (extra actions, buys, money, cards drawn)
- Continues until player ends phase or runs out of actions

#### MONEY Phase
- Engine prompts with `PlayCardDecision` for all unplayed cards in hand
- Always includes `EndPhaseDecision` option
- Playing a card adds its money value to player's available money
- Continues until player ends phase

#### BUY Phase
- Player starts with 1 buy
- Engine prompts with `BuyCardDecision` for each affordable card in supply
- Always includes `EndPhaseDecision` option
- Buying a card:
  - Consumes 1 buy and the card's cost in money
  - Adds card to player's discard pile
- Continues until player ends phase or runs out of buys

#### CLEANUP Phase
- Automatic (no player decisions)
- All cards in hand and played area go to discard pile
- Draw 5 new cards from deck (shuffle discard if needed)

### 3. Game End

The game ends when all FRAMEWORK cards (8 total) have been purchased.

Final scoring:
- Each card contributes its point value to the player's score
- Method: 1 point
- Module: 3 points
- Framework: 6 points
- Bug: -1 point (negative)
- Money and Action cards: 0 points

Results are sorted by score (highest to lowest).

## Card Definitions

### Money Cards
- **Bitcoin**: Cost 0, Value 1 coin
- **Ethereum**: Cost 3, Value 2 coins
- **Dogecoin**: Cost 6, Value 3 coins

### Automation Cards (Point Cards)
- **Method**: Cost 2, 1 point
- **Module**: Cost 5, 3 points
- **Framework**: Cost 8, 6 points

### Bug Cards
- **Bug**: Cost 0, -1 point

### Action Cards
- **Refactor**: Cost 2, +1 action, +1 card drawn
- **Code Review**: Cost 5, +1 buy, +2 money
- **Evergreen Test**: Cost 4, +3 cards drawn

## Card Stack Configuration

The supply is initialized with:
- 60 Bitcoin cards
- 40 Ethereum cards
- 30 Dogecoin cards
- 14 Method cards
- 8 Module cards
- 8 Framework cards
- 10 × (number of players) Bug cards
- 10 Refactor cards
- 10 Code Review cards
- 10 Evergreen Test cards

## Player Interface

Players must implement the `Player` interface:

```java
public interface Player {
    String getName();
    Decision makeDecision(GameState state, ImmutableList<Decision> options);
}
```

### Decision Making Rules

1. **Player must return one of the provided options**
   - Engine validates that the returned decision is in the options list
   - Returns null or invalid decision → `PlayerViolationException`

2. **Player must not throw exceptions**
   - Any exception thrown during `makeDecision` → `PlayerViolationException`
   - Player should handle all errors internally

3. **Player receives immutable game state**
   - `GameState` provides read-only view of current game state
   - Includes: current phase, player's hand, actions/buys/money, available cards

## Example Usage

### Basic Game Setup

```java
// Create players
List<Player> players = Arrays.asList(
    new SimplePlayer("Alice"),
    new SimplePlayer("Bob"),
    new SimplePlayer("Charlie")
);

// Create and run engine
Engine engine = new EngineImpl(players);

try {
    GameResult result = engine.play();
    
    // Display results
    System.out.println("Game Results:");
    for (int i = 0; i < result.getPlayerResults().size(); i++) {
        PlayerResult pr = result.getPlayerResults().get(i);
        System.out.printf("%d. %s: %d points (%d cards)%n",
            i + 1, pr.getName(), pr.getScore(), pr.getEndingDeck().size());
    }
} catch (PlayerViolationException e) {
    System.err.println("Player violated rules: " + e.getMessage());
}
```

### Implementing a Custom Player

```java
public class MyPlayer implements Player {
    private final String name;
    
    public MyPlayer(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public Decision makeDecision(GameState state, ImmutableList<Decision> options) {
        // Simple strategy: always choose first option
        // In real implementation, analyze state and make strategic choice
        return options.isEmpty() ? null : options.get(0);
    }
}
```

## Testing

The implementation includes comprehensive tests:

- `EngineTest.java` - Tests engine functionality and game flow
- `GameTest.java` - Tests original game logic
- `PlayerTest.java` - Tests player implementations
- `CardTest.java` - Tests card functionality

Run tests:
```bash
mvn test
```

## Key Design Decisions

1. **Immutable GameState**: Players receive immutable snapshots of game state, preventing cheating or accidental modifications.

2. **Type-Safe Decisions**: Different decision types (PlayCard, BuyCard, GainCard, EndPhase) ensure compile-time type safety.

3. **CardDefinition vs Card**: Separates card definition (immutable properties) from card instances, reducing memory overhead.

4. **PlayerState Encapsulation**: Internal player state is managed by Engine, players only make decisions.

5. **Validation**: Engine validates all player decisions before applying them, ensuring rule compliance.

6. **Exception Handling**: Clear exception types for different failure modes (PlayerViolation vs IllegalArgument).

## Future Enhancements

Potential areas for expansion:

1. **Additional Action Cards**: Easy to add new action cards with custom effects
2. **Game Variants**: Different starting decks or card stacks
3. **Replay System**: Save/load game states for analysis
4. **AI Players**: More sophisticated player strategies
5. **Logging/Debugging**: Detailed game state logging for analysis
6. **Parallel Games**: Run multiple games concurrently for tournaments

## License

This implementation is part of the IP2 project for COSI 103a at Brandeis University.
