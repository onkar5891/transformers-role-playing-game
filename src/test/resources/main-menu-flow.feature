Feature: Main menu flow

  Scenario: Player can start new battle
    Given Battle start mode is mocked
    When Player chooses to "START" battle
    Then Player should be redirected to player creation menu

  Scenario: Player can load new battle
    Given Battle load mode is mocked
    And A Sample battle was previously saved
    When Player chooses to "LOAD" battle
    Then Player should be redirected to battlefield view
