import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class Game
{
    // STATIC VARIABLES
    static boolean fighting = false;
    static String enemyAttacking = "";
    static boolean intentionalFight = false;
    static String currentObj = "";
    static boolean guarding = false;
    static int damage;
    static String playerName = "";
    static String playerClass = "";
    static int turn;
    static String input; // will be used to store scanner input

    
    // STATUS EFFECTS
    static boolean enemyBurning = false;
    static boolean enemyBlinded = false;
    
    // RANDOM OBJECT
    static Random generator = new Random();
    
    // SCANNER OBJECT
    static Scanner scan = new Scanner (System.in);

    
    ////////////////////////////// ROOM OBJECTS //////////////////////////////
    static Room entrance = new Room("Entrance", 
    "You stand in front of a decrepit dungeon. The mist is \ntoo thick... there's no turning back now.", 
    true, false, false, false, true, new ArrayList<String>(), new ArrayList<String>());
    
    static Room courtyard = new Room("Courtyard", 
    "You stand in the middle of a large, empty courtyard. \nThe air hangs still...", 
    true, false, true, true, true, List.of("feral hound", "feral hound"), List.of("blue herb"));
    
    static Room innerhall = new Room("Inner Hall", 
    "The cobblestone halls of the dungeon are dimly lit \nby flickering torches. The smell of \ndeath lingers in the air.", 
    true, false, false, false, true, List.of("prison guard", "prison guard"), new ArrayList<String>());
    
    static Room currentRoom = new Room(); // will be used to reference different room objects later
    
    ////////////////////////////// ACTOR OBJECTS //////////////////////////////
    static Actor hound = new Actor("feral hound", 1, 5, 400, 400, 0, "claws", new ArrayList<String>(), false);
    static Actor prisonGuardA = new Actor("prison guard", 1, 5, 400, 400, 0, "claws", new ArrayList<String>(), false);
    
    static Actor enemy = new Actor(); // will be used to reference different actors later

    static Actor player = new Actor(); // creates a default actor object for the player that will be updated later
    
    ////////////////////////////// INTERACTABLE OBJECTS //////////////////////////////
    static Obj houndCorpse = new Obj("feral hound corpse", 
    "It is the corpse of a feral hound. It has nothing valuable.", false, false, false, new ArrayList<String>());
    
    static Obj blueHerb = new Obj("blue herb", 
    "It is a small blue-colored plant with medicinal properties.", false, false, true, new ArrayList<String>());
    
    static Obj glassShards = new Obj("glass shards", 
    "These are broken pieces of glass.", false, false, true, new ArrayList<String>());
    
    static Obj obj = new Obj(); // will be used to reference different actors later
    
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
        
        // PLAYER CLASS DESCRIPTIONS
        String mercDesc = "Mercenaries deal in close combat, making them more vulnerable to attacks,"
        + "\nbut their stealth lets them walk around unnoticed.";
        String knightDesc = "Knights are especially resilient against enemy attacks,"
        + "\nbut their clunky armor reduces their stealth.";
        String priestDesc = "The dark priest has a strong affinity to magic,"
        + "\nbut lacks physical \nstrength.";
        String outlanderDesc = "The outlander uses a bow to attack the enemy from afar,"
        + "\ndealing less damage but evading more attacks.";

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
        
        scan.nextLine();
        System.out.println(); //new line
        
        // if-statements to set player attributes based on chosen class
        if (playerClass.equals("mercenary"))
        {
            player.setAtk(30);
            player.setDef(10);
            player.setGold(15);
            player.setWeapon("dagger");
            player.addItem("something");
            
            System.out.println("You were hired for a good sum of money to find a military"
            + "\ncommander that was captured and is being held captive inside of"
            + "\na dungeon on the outskirts of the neighboring country.");
        }
        else if (playerClass.equals("knight"))
        {
            player.setAtk(40);
            player.setDef(50);
            player.setGold(40);
            player.setWeapon("sword");
            player.addItem("something");
            
            System.out.println("During the war, your commander had been captured by enemy forces"
            + "\nand now, you've taken it upon yourself to retrieve him from a"
            + "\ndungeon on the outskirts of the neighboring country.");
        }
        else if (playerClass.equals("darkPriest"))
        {
            player.setAtk(15);
            player.setDef(20);
            player.setGold(15);
            player.addItem("something");
            player.addSpell("fireball");
            System.out.println("You received a vision about a man who had been captured during"
            + "\nthe war who has the power to unite the different kingdoms. You"
            + "\njourneyed out to find this man, stumbling upon a dungeon on the"
            + "\noutskirts of the neighboring country.");
        }
        else //outlander
        {
            player.setAtk(20);
            player.setDef(20);
            player.setGold(10);
            player.setWeapon("bow");
            player.addItem("something");
            System.out.println("Your village had been invaded by a military group, and as a"
            + "\nresult, you had lost everything you ever cherised or cared"
            + "\nabout. You sought out the man responsible for the deaths of"
            + "\nyour loved ones, which brought you to a dungeon on the" 
            + "\noutskirts of the neighboring country.");
        }
        System.out.println("\nAs you near the dungeon, the mist begins to get thicker...\n");
        
        scan.nextLine();
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
                boolean running = false;
                
                copyEnemyObjects();
                
                System.out.println("\n-----COMBAT-----\n");
                
                if (firstBattle) // the player gets a small tutorial on their first battle
                {
                    System.out.println("It seems like you've entered your first battle!\n");
                    System.out.println("Every turn, you get the option to [ATTACK], use [SPELLS],"
                    + "\n[GUARD], use an [ITEM], or [FLEE] if you're a coward.\n");
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
                        enemyTurn();
                        
                        // prints the hp visual after the enemy's turn
                        System.out.println("\n" + player.getName().toUpperCase() + " HP: " + player.hpVisual());
                        System.out.println("\n" + enemy.getName().toUpperCase() + " HP: " + enemy.hpVisual());
                        
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
                    // if the player dies from an enemy's turn above then this code is skipped over
                    if (fighting) 
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
                                    running = true;
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
                if (!running)
                {
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
                        // resets status effects
                        enemyBurning = false;
                        enemyBlinded = false;
                        
                        addEnemyCorpse(); // adds the enemy's corpse to the room as an object
                        enemyAttacking = "";
                        System.out.println("\n...\n");
                        scan.nextLine();
                    } 
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
                    System.out.println("\nlook = describes the area around you");
                    System.out.println("\nuse = lets you use an item from your inventory");
                    System.out.println("\nquit = quits the game :(");
                }
                
                //movement
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
                
                //displays information
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
                
                //player interaction with environment
                if (input.equals("look") || input.equals("look around"))
                {
                    System.out.println(currentRoom.roomState());
                }
                
                if (input.equals("use"))
                {
                    useCommand();
                }
                
                if (input.equals("fight") || input.equals("attack"))
                {
                    fightCommand();
                    
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
        else if (currentRoom.getName().equals("courtyard") && (direction.equals("south") || direction.equals("s")))
        {
            currentRoom = entrance;
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
    
    public static void addEnemyCorpse() // after defeating an enemy, adds a corpse to the objects of the room
    {
        if (enemyAttacking.equals("feral hound"))
        {
            currentRoom.addObj("feral hound corpse");
        }
    }
    
    public static void fightCommand()
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
    
    public static void useCommand()
    {
        System.out.println(player.openInventory());
        if (player.inventorySize() > 0) // checks if the player has anything
        {
           System.out.println("\nWhat do you want to use?");
        
            input = scan.nextLine();
            input = input.toLowerCase();
            
            System.out.println("\n> " + input);
        
            if (input == "blue herb")
            {
                if (player.hasItem(input))
                {
                    System.out.println("You use the " + input + ".");
                    player.addHp(30); // blue herb adds 30 hp
                }
                else
                {
                    System.out.println("You don't have a blue herb to use!");
                }
            }
            else
            {
                System.out.println("\nInvalid item.");
            }
        }
    }
} // end main class

