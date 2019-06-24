Feature: Player's creation

  Scenario: Player should be placed on battlefield after all details are fed
    When Player's complete information is given
      | Name                        | Optimus |
      | Gender                      | MALE    |
      | Rifle Power                 | 10      |
      | Rifle Accuracy              | 1.0     |
      | Sword Power                 | 10      |
      | Sword Accuracy              | 1.0     |
      | Alternate Mode Identity     | HMV     |
      | Alternate Mode Power Impact | 70      |
    Then Placing the player on battlefield should trigger battlefield view

  Scenario: Incomplete details of player should not be accepted
    When Player's incomplete information is given
      | Name           | Optimus |
      | Gender         | MALE    |
      | Rifle Power    | 10      |
      | Rifle Accuracy | 1.0     |
      | Sword Power    | 250     |
    Then Placing the player on battlefield should be erroneous
