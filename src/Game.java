import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class Game
{
    // STATIC VARIABLES
    static boolean fighting = false;
    static String enemyAttacking = "";
    static boolean guarding = false;
    static int damage;
    static String playerName = "";
    static String playerClass = "";
    static int turn;
    
    // STATUS EFFECTS
    static boolean enemyBurning = false;
    
    // RANDOM OBJECT
    static Random generator = new Random();
    
    ////////////////////////////// ROOM OBJECTS //////////////////////////////
    static Room entrance = new Room("Entrance", "You stand in front of a decrepit dungeon. The mist is getting thicker...", true, false, false, false, true, new ArrayList<String>(), new ArrayList<String>());
    static Room courtyard = new Room("Courtyard", "You stand in the middle of a large courtyard. The air hangs still...", true, false, true, true, true, List.of("feral hound"), new ArrayList<String>());
    static Room currentRoom = new Room(); // will be used to reference different room objects later
    
    ////////////////////////////// ACTOR OBJECTS //////////////////////////////
    static Actor hound = new Actor("feral hound", 1, 5, 400, 400, 0, "claws", new ArrayList<String>(), false);
    static Actor enemy = new Actor(); // will be used to reference different actors later

    static Actor player = new Actor(); // creates a default actor object for the player that will be updated later
    
    ////////////////////////////// MAIN METHOD //////////////////////////////
    public static void main (String[] args)
    {
        // VARIABLES
        boolean playing = true;
        boolean confirming = true;
        int timeCount = 0; // will be used to count the amount of "turns" the player has spent in a room
        boolean firstTurn = true;
        boolean firstBattle = true;
        boolean allowedMove = false;
        boolean intentionalFight = false;
        String input; // will be used to store scanner input

        Scanner scan = new Scanner (System.in); // scanner object

        // PLAYER CLASS DESCRIPTIONS
        String mercDesc = "Mercenaries deal in close combat, making them more vulnerable to attacks, \nbut their stealth lets them walk around unnoticed.";
        String knightDesc = "Knights are especially resilient against enemy attacks, but their armor \nreduces their stealth.";
        String priestDesc = "The dark priest has a strong affinity to magic, but lacks physical \nstrength.";
        String outlanderDesc = "The outlander uses a bow to attack the enemy from afar, dealing less \ndamage but evading more attacks.";

        ////////////////////////////// PLAYER SETUP //////////////////////////////

        System.out.println("Welcome!");
        System.out.println("To get a list of commands, enter \"help\"\n----------");

        System.out.println("\nWhat's your name, traveler?");
        
        // validation Loop
        while (confirming)
        {
            playerName = scan.nextLine();
            
            System.out.println("\n> " + playerName); 
            System.out.println("\nIs this name correct? (Yes/No)");
            
            input = scan.nextLine();
            input = input.toLowerCase();

            if (input.equals("no"))
            {
                System.out.println("\nWhat's your name, traveler?");
            }
            else
            {
                confirming = false;
            }
        }

        player.setName(playerName); //sets the name attribute for the player object

        System.out.println("\nChoose your class:" +
                "\n\n1) Mercenary\n----------\n" + mercDesc +
                "\n\n2) Knight\n----------\n" + knightDesc +
                "\n\n3) Dark Priest\n----------\n" + priestDesc +
                "\n\n4) Outlander\n----------\n" + outlanderDesc);

        confirming = true;
        
        // validation loop
        while (confirming)
        {
            int input2 = scan.nextInt();
            System.out.println("\n> " + input2);
            if (input2 == 1)
            {
                System.out.println("\nYou have chosen the Mercenary class.");
                playerClass = "mercenary";
                confirming = false;
            }
            else if (input2 == 2)
            {
                System.out.println("\nYou have chosen the Knight class.");
                playerClass = "knight";
                confirming = false;
            }
            else if (input2 == 3)
            {
                System.out.println("\nYou have chosen the Dark Priest class.");
                playerClass = "darkPriest";
                confirming = false;
            }
            else if (input2 == 4)
            {
                System.out.println("\nYou have chosen the Outlander class.");
                playerClass = "outlander";
                confirming = false;
            }
            else
            {
                System.out.println("\nPlease input 1, 2, 3, or 4.");
            }
        }
        
        player.setPlayerClass(playerClass); // sets the playerClass attribute of player object
        
        // if-statements to set player attributes based on chosen class
        if (playerClass.equals("mercenary"))
        {
            player.setAtk(30);
            player.setDef(10);
            player.setGold(15);
            player.setWeapon("dagger");
            player.addItem("something");
        }
        else if (playerClass.equals("knight"))
        {
            player.setAtk(40);
            player.setDef(50);
            player.setGold(40);
            player.setWeapon("sword");
            player.addItem("something");
        }
        else if (playerClass.equals("darkPriest"))
        {
            player.setAtk(15);
            player.setDef(20);
            player.setGold(15);
            player.addItem("something");
            player.addSpell("fireball");
        }
        else //outlander
        {
            player.setAtk(20);
            player.setDef(20);
            player.setGold(10);
            player.setWeapon("bow");
            player.addItem("something");
        }
        
        currentRoom = entrance;
        
        ////////////////////////////// THE GAME //////////////////////////////
        System.out.println(currentRoom.roomState()); // room description for the starting room
         
        while (playing) // loop to keep the game going 
        {
            if (!intentionalFight) // boolean intentionalFight will be true whenever the player choses to fight something
            {
               if (currentRoom.ambushCheck(playerClass)) // checks to see if the player is going to get ambushed 
                {
                    enemyAttacking = currentRoom.randomEnemy();
                    
                    fighting = true;
                    firstTurn = false; // the enemy will get the first turn of the battle
                } 
            }
            
            if (allowedMove == true) // will be true after the player moves to a new room
            {
                System.out.println(currentRoom.roomState()); // prints upon entering room
                allowedMove = false;
                timeCount = 0; // "time" reset
            }
            
            if (fighting)
            {
                boolean playerTurn = false;
                turn = 0; //Will keep track of the amount of turns
                guarding = false;
                
                copyEnemyObjects();
                
                System.out.println("\n-----COMBAT-----\n");
                
                if (firstBattle) // the player gets a small tutorial on their first battle
                {
                    System.out.println("It seems like you've entered your first battle!\n");
                    System.out.println("Every turn, you get the option to [ATTACK], use [SPELLS], \n[GUARD], use an [ITEM], or [FLEE] if you're a coward.\n");
                    System.out.println("Watch your HP! When it becomes 0, it's GAME OVER.\n");
                    
                    System.out.println("\n...\n");
                    scan.nextLine();
                    firstBattle = false; // so the tutorial doesn't print again
                }
                
                // combat loop
                while (fighting)
                {
                    turn++; // first turn = 1 and each repetition of the loop will increase turn by 1
                    System.out.println("\n////////////////////////////////////////////////////////////");
                    System.out.println("\nTURN " + turn);
                    
                    // prints the hp visual for the player and enemy
                    System.out.println("\n" + player.getName().toUpperCase() + " HP: " + player.hpVisual());
                    System.out.println("\n" + enemy.getName().toUpperCase() + " HP: " + enemy.hpVisual());
                    
                    playerTurn = false;
                    guarding = false;
                    
                    if (!firstTurn) // if the player doesn't have the first turn
                    {
                        System.out.println("\nYou are ambushed by a " + enemyAttacking + "!");
                        System.out.println("\n...\n");
                        enemyTurn();
                        
                        // prints the hp visual after the enemy's turn
                        System.out.println("\n" + player.getName().toUpperCase() + " HP: " + player.hpVisual());
                        System.out.println("\n" + enemy.getName().toUpperCase() + " HP: " + enemy.hpVisual());
                        
                        scan.nextLine();
                        
                        // if the player or enemy is dead the fighting ends
                        if (player.isDead())
                        {
                            fighting = false;
                        }
                        if (enemy.isDead())
                        {
                            fighting = false;
                        }
                        firstTurn = true;
                    }
                    
                    if (fighting) // if the player dies from an enemy's turn above then this code is skipped over
                    {
                        playerTurn = true;
                        System.out.println("\n///////////////YOUR TURN///////////////\n");
                        System.out.println("[Attack]  [Spells]  [Guard]  [Item]  [Flee]\n");
                        
                        // start of player turn
                        while (playerTurn) // repeats until the player has inputted a valid command
                        {
                            input = scan.nextLine();
                            input = input.toLowerCase();
                    
                            System.out.println("\n> " + input);
                    
                            if (input.equals("attack"))
                            {
                                if (player.missChance()) // if the player misses
                                {
                                    System.out.println("\nYou miss! The " + enemy.getName() + " narrowly dodges your attack!");
                                }
                                else // if the player doesn't miss
                                {
                                    damage = player.dmgCalc();
                                    System.out.println("\nYou deal " + damage + " damage to the " + enemy.getName() + ".");
                                    enemy.subHp(damage);
                                }
                                
                                playerTurn = false; //ends player turn
                            }
                            else if (input.equals("spells") || input.equals("spell"))
                            {
                                System.out.println(player.openSpells());
                                System.out.println("What spell do you want to use?");
                                
                                input = scan.nextLine();
                                input = input.toLowerCase();
                                System.out.println("\n> " + input);
                                
                                if (input.equals("fireball")) // very long if-statement for each spell the player can cast
                                {
                                    if (player.hasSpell("fireball"))
                                    {
                                        if (player.missChance())
                                        {
                                            System.out.println("\nYou miss! The " + enemy.getName() + " narrowly dodges your attack!");
                                        }
                                        else
                                        {
                                            damage = player.spellDmgCalc("fireball");
                                            System.out.println("\nYou deal " + damage + " damage to the " + enemy.getName() + ".");
                                            if (burningChance())
                                            {
                                                System.out.println("\nThe " + enemy.getName() + " catches on fire!");
                                                enemyBurning = true;
                                            }
                                            enemy.subHp(damage);
                                        }
                                        playerTurn = false;
                                    }
                                    else
                                    {
                                        System.out.println("You don't have that spell!");
                                    }
                                }
                                else
                                {
                                    System.out.println("Invalid spell name.");
                                }
                            }
                            else if (input.equals("guard"))
                            {
                                System.out.println("\nYou prepare for the enemy's next attack.");
                                guarding = true; // will affect the amount of damage the enemy does during its turn
                                playerTurn = false;
                            }
                            else if (input.equals("item"))
                            {
                                System.out.println(player.openInventory()); // prints inventory for player reference
                                System.out.println("\nWhat do you want to use?");
                                
                                input = scan.nextLine();
                                input = input.toLowerCase();
                                System.out.println("\n> " + input);
                                // very long if-statement for each item
                            }
                            else if (input.equals("flee") || input.equals("run"))
                            {
                                if (player.runChance()) // sees if the player is successful in running away
                                {
                                    System.out.println("\nYou ran away!");
                                    fighting = false;
                                }
                                else
                                {
                                    System.out.println("\nYou were unable to run away!");
                                }
                                playerTurn = false; // an unsuccessful attempt to run away also counts as the player's run
                            }
                            else
                            {
                                System.out.println("\nInvalid command. Use [Attack]/[Spells]/[Guard]/[Item]/[Flee].");
                            }
                        }
                    
                        // checks to see if the player or enemy is dead
                        if (player.isDead())
                        {
                            fighting = false;
                        }
                        if (enemy.isDead())
                        {
                            fighting = false;
                        }
                    }
                    
                    if (fighting) // skips enemy turn if the fight has ended
                    {
                        System.out.println("\n...\n");
                        scan.nextLine();
                        
                        // enemy turn
                        enemyTurn();
                        
                        // checks to see if the enemy or player is dead
                        if (player.isDead())
                        {
                            fighting = false;
                        }
                        if (enemy.isDead())
                        {
                            fighting = false;
                        }
                    }
                }
                // post battle code
                if (player.getDeathState() == true) // player is dead
                {
                    System.out.println("\nYou succumb to your wounds."); // womp womp
                    System.out.println("\n////////////////////////////// GAME OVER //////////////////////////////");
                }
                else if (enemy.getDeathState() == true)
                {
                    System.out.println("\nYou are victorious!");
                    currentRoom.removeEnemy(currentRoom.getEnemyIndex(enemyAttacking)); // removes the dead enemy from the room's enemylist
                    intentionalFight = false; // resets for the next fight
                    
                    System.out.println("\n...\n");
                    scan.nextLine();
                }
            }
            else
            {
                input = scan.nextLine();
                input = input.toLowerCase();
                timeCount++; // increases this counter for every "turn" the player takes within a room

                System.out.println("\n> " + input);
                
                ////////////////////////////// COMMANDS //////////////////////////////
                // each turn outside of combat lets the player input commands to do different things
                
                if (input.equals("help"))
                {
                    System.out.println("\nThese are the commands:");
                    System.out.println("\ninventory = lists the items in your inventory");
                    System.out.println("\nprofile = opens your profile");
                    System.out.println("\nspells = displays the spells you've learned");
                    System.out.println("\nmove = lets you move to different areas");
                    System.out.println("\nlook = you look at your surroundings");
                    System.out.println("\nquit = quits the game :(");
                }
                
                if (input.equals("move"))
                {
                    allowedMove = false;
                    System.out.println("\nWhere do you want to move? (North/East/South/West)");

                    input = scan.nextLine();
                    input = input.toLowerCase();
                    
                    System.out.println("\n> " + input);
                    
                    allowedMove = currentRoom.canMove(input); // checks to see if the player can move in that direction
                    if (allowedMove)
                    {
                        Game.moveTo(input); // moves the player to a different room
                    }
                }

                if (input.equals("move north"))
                {
                    allowedMove = currentRoom.canMove("north");
                    if (allowedMove)
                    {
                        Game.moveTo("north");
                    }
                }
                
                if (input.equals("move east"))
                {
                    allowedMove = currentRoom.canMove("east");
                    if (allowedMove)
                    {
                        Game.moveTo("east");
                    }
                }
                
                if (input.equals("move south"))
                {
                    allowedMove = currentRoom.canMove("south");
                    if (allowedMove)
                    {
                        Game.moveTo("south");
                    }
                }
                
                if (input.equals("move west"))
                {
                    allowedMove = currentRoom.canMove("west");
                    if (allowedMove)
                    {
                        Game.moveTo("west");
                    }
                }

                if (input.equals("inventory"))
                {
                    System.out.println(player.openInventory());
                }

                if (input.equals("profile"))
                {
                    System.out.println(player.openProfile());
                }

                if (input.equals("spells"))
                {
                    System.out.println(player.openSpells());
                }
                
                if (input.equals("look") || input.equals("look around"))
                {
                    System.out.println(currentRoom.roomState());
                }
                
                if (input.equals("fight") || input.equals("attack"))
                {
                    if (currentRoom.getEnemyCount() > 0) // checks if there's more than one enemy in the room
                    {
                        if (currentRoom.getEnemyCount() == 1) // if there's only 1 enemy, the code to choose which enemy to attack is unnecessary
                        {
                            enemyAttacking = currentRoom.getEnemy(0);
                            System.out.println("\nYou attack the " + enemyAttacking + "!");
                            
                            copyEnemyObjects();
                            fighting = true;
                            intentionalFight = true; // will ensure the code to check to see if the player is ambushed does not run
                        }
                        else
                        {
                            System.out.println("\n" + currentRoom.getEnemyList());
                            System.out.println("\nWhich enemy will you attack?");
                            
                            input = scan.nextLine();
                            input = input.toLowerCase();
                            
                            System.out.println("\n> " + input);
                            
                            if (currentRoom.enemyListContains(input))
                            {
                                enemyAttacking = currentRoom.getEnemy(currentRoom.getEnemyIndex(input));
                                System.out.println("You attack the " + enemyAttacking + "!");
                                
                                copyEnemyObjects();
                                
                                fighting = true;
                                intentionalFight = true;
                            }
                            else
                            {
                                System.out.println("\nInvalid name.");
                            }
                        }
                    }
                    else
                    {
                        System.out.println("\nThere are no enemies nearby.");
                    }
                    
                }
                
                if (input.equals("quit"))
                {
                    playing = false; // allows the player to leave the loop, therefore ending the game
                }
            }
        }
    System.out.println("\n Thank you for playing!");
    }
    
    ////////////////////////////// STATIC METHODS //////////////////////////////
    public static void moveTo(String direction) // handles the code for the player moving from rooms in different locations
    {
        if (currentRoom.getName().equals("Entrance") && (direction.equals("north") || direction.equals("n")))
        {
            currentRoom = courtyard;
        }
    }
    
    public static void enemyTurn()
    {
        int factor = 3;
        // knights get a bonus by increasing the factor by which enemy damage is reduced when guarding
        if (playerClass.equals("knight"))
        {
           factor = 5; 
        }

        System.out.println("\n///////////////ENEMY TURN///////////////\n");
        
        // if-statement for text for different enemies
        if (enemy.getName().equals("feral hound"))
        {
            System.out.println("\nThe feral hound growls at you...");
            System.out.println("It lunges at you!");
        }
                        
        if (enemy.enemyMissChance(playerClass)) // sees if the enemy misses its attack
        {
            System.out.println("\nYou dodge the attack!");
        }
        else
        {
            damage = enemy.dmgCalc();
            if (guarding) // damage reduction if the player chose to guard
            {
                damage /= factor;
            }
            System.out.println("\nYou take " + damage + " damage!");
            player.subHp(damage);
        }
        
        if (enemyBurning) // if the player had used fireball on the enemy
        {
            int burningDmg = generator.nextInt(100)+1;
            System.out.println("\nThe " + enemyAttacking + " is burning...");
            enemy.subHp(burningDmg);
            
            System.out.println("\nThe " + enemyAttacking + " takes an extra " + burningDmg + " damage!");
            
            burningDmg = generator.nextInt(100)+1;
            enemy.subHp(burningDmg);
            
            System.out.println("\nThe " + enemyAttacking + " takes an extra " + burningDmg + " damage!");
            
            burningDmg = generator.nextInt(100)+1;
            enemy.subHp(burningDmg);
            
            System.out.println("\nThe " + enemyAttacking + " takes an extra " + burningDmg + " damage!");
            enemyBurning = false;
        }
        
    }
    
    public static boolean burningChance() // calculates if the enemy catches on fire
    {
        boolean output = false;
        int chance = Game.generator.nextInt(11)+1;
        if (chance <= 3) // 30% chance
        {
            output = true;
        }
        return output;
    }
    
    public static void copyEnemyObjects() // assigning references to the enemy variable
    {
        if (enemyAttacking.equals("feral hound"))
        {
            enemy = hound;
        }
    }
} // end main class

