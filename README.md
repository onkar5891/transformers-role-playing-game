# Transformers Role Playing Game

### Installation
  * Install the game
    * Slow (Run tests, too)
      - **``mvn clean install``**
    * Fast (Skip tests)
      - **``mvn clean install -DskipTests``**

  * Run the game
    * Normal Mode
      - **``java -jar target/transformers-rpg.jar``**
    * Prettier Mode
      - This allows player to easily:
        * recognize the mission
        * be aware of things
        * identify enemies on battlefield
        * identify attacks made by him/her or the enemy and the impact caused
        * and more..
      - **``java -DprettyPrint=true -jar target/transformers-rpg.jar``**

### Features
  * Create player character **(An Autobot)**
    - Provides default values so that if player is happy with predefined configuration, he/she can hit the Return key directly
    - Some of the configurations include Name, Gender, Weapons, Alternate Mode
  * Fair play (available to both User player [Autobot] and CPU player [Decepticon])
    - Before attacking, CPU chooses any of the available weapons or the transformer's deadly attack: **BY TRANSFORMING**
    - Health reward after killing an opponent
  * Save/Load battlefield state
    - After creating An Autobot, obviously mission of the player is started and is placed on the battlefield
    - From this point, player can save the state by choosing '0' (hidden option) from the menu
    - If player has lost after saving the state, the game can be relaunched and same state can be loaded

### Extensions
  * Multiple levels
  * Suicidal places (like Fire) on the battlefield
  * Hidden rewards (like Health Booster, Weapons) on the battlefield
  * Decepticon to retreat as well
