# Transformers Role Playing Game

## Interacting with the game
  * **INSTALL**
    * Slow *(You want to ensure everything is working!)*
      - **``mvn clean install``**
    * Fast *(You are desperate to play the game!)*
      - **``mvn clean install -DskipTests``**

  * **RUN**
    * Normal Mode
      - **``java -jar target/transformers-rpg.jar``**
    * Prettier Mode
      - This allows player to easily:
        * *recognize the mission*
        * *be aware of things*
        * *identify enemies on battlefield*
        * *identify attacks made by him/her or the enemy and the impact caused*
      - If console supports ANSI colors, use **``java -DprettyPrint=true -jar target/transformers-rpg.jar``**
      - Otherwise, launch it as  **``mvn exec:java -Dexec.mainClass=org.hasbro.transformers.TransformersRPG -DprettyPrint``**

## Definitions
  > **Autobot** - User Player, **Decepticon** - CPU Player

## Features
  * **Create player (An Autobot)**
    - Loaded with default values so that if player is happy with predefined configuration, he/she can hit the Return key directly
    - Some of the configurations include Name, Gender, Weapons, Alternate Mode
  * **Fair play (available to both Autobot and Decepticon)**
    - Before attacking, CPU chooses any of the available weapons or the transformer's deadly attack: **BY TRANSFORMING**
    - Health reward after killing an opponent
  * **Save/Load battlefield state**
    - After creating An Autobot, obviously mission of the player is started and is placed on the battlefield
    - From this point, player can save the state by choosing '0' (hidden option) from the menu
    - If player has lost after saving the state, the game can be relaunched and same state can be loaded
  * **Color mode (But not important)**
    - This was my personal choice as I like to see different texts with different colors on terminal
    - So, I decided to invested some hours on this and came up with three colors viz., RED, GREEN and CYAN for the game

## Extensions
  * **Suicidal places on the battlefield**
    - Imagine there is fire on some locations of battlefield
    - In this case, if Autobot tries to go inside the fire, his/her health needs to be reduced
  * **Hidden rewards on the battlefield**
    - If we add one more enemy on the battlefield, it would be a One vs Three battle
    - So, for better fair play, there can be some locations on battlefield where health rewards, weapons are present
    - Player needs to identify the best path by which all enemies can be defeated
  * **Decepticons to retreat as well**
    - As of now, only player is given an option to retreat
    - Currently, decepticon is able to defend Autobot's attack, choose weapon and attack, or transform and attack randomly
    - But in some cases, decepticon, too may retreat which will be added advantage to Autobot
  * **Post level completion**
    - After winning the level, actions like weapons getting more stronger, giving Autobot another mission, etc. can be implemented
    - This will be for better engagement of player with the game
