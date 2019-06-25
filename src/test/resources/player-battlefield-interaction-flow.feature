Feature: Player's interaction on battlefield

  Scenario: Player is able to move on the battlefield freely
    Given A one on one battlefield
    When Player moves on the battlefield with sequences "UP,UP,RIGHT,DOWN,LEFT,LEFT,DOWN"
    Then Final position of the player is Row: "2", Column: "0"

  Scenario: Player wins
    Given A one on one battlefield with amateur decepticon and normal autobot
    When Player moves next to the enemy with sequences "UP" and consistently attacks the enemy who is on "LEFT" side
    Then Player should defeat the enemy

  Scenario: Player looses
    Given A one on one battlefield with legendary decepticon and normal autobot
    When Player moves with sequences "UP,RIGHT,DOWN,DOWN,LEFT,LEFT,UP" and consistently stays still next to enemy who is on "UP" side
    Then Player should be defeated by the enemy
