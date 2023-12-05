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
    static boolean guarding = false;
    static boolean playerTurn = false;
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
            true, false, false, false, true, new ArrayList<>(), new ArrayList<>());

    static Room courtyard = new Room("Courtyard",
            "You stand in the middle of a large, empty courtyard. \nThe air hangs still...",
            false, true, true, true, true, List.of("feral hound", "rabid hound"), List.of("blue herb", "barrel"));

    static Room innerHall = new Room("Inner Hall",
            "The cobblestone halls of the dungeon are dimly lit \nby flickering torches. The smell of \ndeath lingers in the air.",
            true, false, false, true, true, List.of("prison guard"), new ArrayList<>());

    static Room basement = new Room("Basement",
            "The walls are crumbling at the edges. The air has a \nsort of sickly stench to it.",
            true, true, false, false, true, new ArrayList<>(), new ArrayList<>());

    static Room prisonA = new Room("Prison",
            "Rows of cells seem to extend further into the next room.\nThis seems to be the place prisoners are kept.",
            true, false, true, true, true, List.of("prisoner ghoul", "animated skeleton"), new ArrayList<>());

    static Room prisonB = new Room("Deep Prison",
            "There is water dripping off of the ceiling, pooling on top \nof the uneven cobblestone ground. This seems to be the \nsecond half of the prisons.",
            false, false, true, false, false, List.of("prisoner ghoul", "elite guard"), new ArrayList<>());

    static Room westWing = new Room("West Wing",
            "The air feels less oppressive here. This seems like it used\nto be an armory of some sort. Dirty wooden tables and odd steel \ncontraptions are scattered around the room.",
            false, true, false, false, true, new ArrayList<>(), List.of("matches", "metal door")); // north exit to hidden chamber unlocked later

    static Room hiddenChamber = new Room("Hidden Chamber",
            "This is a small, oddly normal room. There are a few dusty \nbookshelves lined against the wall.",
            false, false, true, false, true, new ArrayList<>(), new ArrayList<>());

    static Room cavern = new Room("Cavern",
            "This is a relatively big area. The ground is broken into \ndifferent chunks that are raised some distance from the actual \n"
                    + "floor of the cavern. The drop from here would be fatal. There \nare several bridges that connect the land masses.",
            false, true, false, false, false, List.of("cavebeing", "cavegnome"), List.of("metal gate")); // west exit unlocked later w/ key

    static Room mines = new Room("Mines",
            "The area is composed of diverging and converging tunnel \nstructures. This place was likely created with the intention of \nbeing used for mineral extraction. \nWhat came first, the dungeon or the mines?",
            false, true, false, false, true, List.of("zombie miner", "rocky amalgamation"), List.of("minecart"));

    static Room eastWing = new Room("East Wing",
            "A large room lined with broken suits of armor. The ceiling \nseems to be held up by wooden beams of questionable integrity.",
            true, false, false, true, true, new ArrayList<>(), new ArrayList<>());

    static Room catacombs = new Room("Catacombs",
            "Stacks of skulls yellowed from time fill the walls, and random \nbones are scattered across the floor. The catacombs are faintly"
                    + "\nlit by lanterns. The air smells stale.",
            false, false, true, false, true, new ArrayList<>(), List.of("large door")); // north exit unlocked later w/ key

    static Room catacombCell = new Room("Catacomb Cell",
            "A single cell sits at the end of the catacombs. The bars are thick.",
            false, false, true, false, true, new ArrayList<>(), new ArrayList<>());

    static Room currentRoom = new Room(); // will be used to reference different room objects later

    ////////////////////////////// ACTOR OBJECTS //////////////////////////////
    static Actor hound = new Actor("feral hound", 1, 2, 400, 400, 0, "claws", new ArrayList<>(), false);

    static Actor hound2 = new Actor("rabid hound", 1, 2, 250, 400, 0, "claws", new ArrayList<>(), false);

    static Actor prisonGuardA = new Actor("prison guard", 1, 5, 800, 800, 0, "cleaver", new ArrayList<>(), false);

    static Actor prisonGuardB = new Actor("ballistic guard", 1, 5, 500, 500, 0, "ballista", new ArrayList<>(), false);

    static Actor prisonGuardC = new Actor("elite guard", 1, 5, 1200, 1200, 0, "cleaver", List.of("copper key"), false);

    static Actor ghoul = new Actor("prisoner ghoul", 1, 1, 100, 100, 0, "", new ArrayList<>(), false);

    static Actor skeleton = new Actor("silly skeleton", 1, 1, 200, 200, 0, "", new ArrayList<>(), false);

    static Actor enemy = new Actor(); // will be used to reference different actors later

    static Actor player = new Actor(); // creates a default actor object for the player that will be updated lat

    ////////////////////////////// INTERACTABLE OBJECTS //////////////////////////////
    static Obj houndCorpse = new Obj("feral hound corpse",
            "It is the corpse of a feral hound. It has nothing valuable.", false, false, false, new ArrayList<>());

    static Obj houndCorpse2 = new Obj("rabid hound corpse",
            "It is the corpse of a rabid hound. It has nothing valuable.", false, false, false, new ArrayList<>());

    static Obj guardCorpse = new Obj("prison guard corpse",
            "It is the corpse of a mutated prison guard. Odd tumor-like \nclumps of flesh have formed all over its body. Perhaps it was human once.", false, true, false, List.of("blue vial"));
            
    static Obj eliteCorpse = new Obj("elite guard corpse",
            "It is the corpse of a mutated elite guard. This one was \nlikely in charge of the other prison guards, judging from the larger stature.", false, true, false, List.of("blue vial"));        
            
    static Obj ghoulCorpse = new Obj("ghoul corpse",
            "It is the corpse of a ghoul. It is wearing a tattered prisoner's\n attire. The veins across its body are bloodshot red, and its hands seem burnt.", false, false, false, new ArrayList<>());

    static Obj skeletonCorpse = new Obj("skeleton corpse",
            "It is a skeleton. It's bones have become disconnected from one another.", false, false, false, new ArrayList<>());

    static Obj blueHerb = new Obj("blue herb",
            "It is a small blue-colored plant with medicinal properties.", false, false, true, new ArrayList<>());

    static Obj blueVial = new Obj("blue vial",
            "It is a small glass vial of blue liquid that has strong medicinal properties.", false, false, true, new ArrayList<>());

    static Obj glassShards = new Obj("glass shards",
            "These are broken pieces of glass.", false, false, true, new ArrayList<>());

    static Obj driedMeat = new Obj("dried meat",
            "This meet had probably been left out into the sun to dry. \nIt's hard to chew, but at least it's edible.", false, false, true, new ArrayList<>());

    static Obj meatPie = new Obj("meatpie", "It's a pie made out of meat.", false, false, true, new ArrayList<>());

    static Obj matches = new Obj("matches",
            "You could probably use these to light up dark spaces.", false, false, true, new ArrayList<>());

    static Obj metalDoor = new Obj("metal door",
            "The metal door is rusting at the edges. There is a small keyhole.\n If you had a key, perhaps you could unlock it.", true, false, false, new ArrayList<>());

    static Obj rustyKey = new Obj("rusty key",
            "The metal key is rusting at the edges.", false, false, true, new ArrayList<>());
            
    static Obj metalGate = new Obj("metal gate",
            "The metal gate is locked with a copper padlock. If you had a key, \nperhaps you could unlock it.", true, false, false, new ArrayList<>());

    static Obj copperKey = new Obj("copper key",
            "The copper key seems to be in good condition.", false, false, true, new ArrayList<>());        
            
    static Obj giantDoor = new Obj("giant door",
            "The door stands tall, but fortunately, the keyhole is within your \nreach. If you had a key, perhaps you could unlock it.", true, false, false, new ArrayList<>());

    static Obj largeKey = new Obj("large key",
            "The key is large but surprisingly light.", false, false, true, new ArrayList<>());        

    static Obj minecart = new Obj("minecart",
            "The minecart is filled with rocks.", false, true, false, List.of("rusty key"));

    static Obj barrel = new Obj("barrel",
            "A wooden barrel.", false, true, false, new ArrayList<>());

    ////////////////////////////// MAIN METHOD //////////////////////////////
    public static void main (String[] args)
    {
        // VARIABLES
        boolean playing = true;
        boolean confirming = true;
        boolean running = false;
        int timeCount = 0; // will be used to count the amount of "turns" the player has spent in a room
        boolean firstTurn = true;
        boolean firstBattle = true;
        boolean allowedMove = false;

        // PLAYER CLASS DESCRIPTIONS
        String mercDesc = "Mercenaries deal in close combat, making them more vulnerable to attacks,"
                + "\nbut their stealth lets them walk around unnoticed and their strength makes them"
                + "\na formidable foe.";
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

            input = scan.nextLine().trim();
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

        System.out.println(); //new line

        // if-statements to set player attributes based on chosen class
        if (playerClass.equals("mercenary"))
        {
            player.setAtk(60);
            player.setDef(10);
            player.setGold(15);
            player.setWeapon("dagger");
            player.addItem("blue herb");

            System.out.println("You were hired for a good sum of money to find a military"
                    + "\ncommander that was captured and is being held captive inside of"
                    + "\na dungeon on the outskirts of the neighboring country.");
        }
        else if (playerClass.equals("knight"))
        {
            player.setAtk(40);
            player.setDef(60);
            player.setGold(40);
            player.setWeapon("sword");
            player.addItem("blue herb");

            System.out.println("During the war, your commander had been captured by enemy forces"
                    + "\nand now, you've taken it upon yourself to retrieve him from a"
                    + "\ndungeon on the outskirts of the neighboring country.");
        }
        else if (playerClass.equals("darkPriest"))
        {
            player.setAtk(15);
            player.setDef(20);
            player.setGold(15);
            player.addItem("blue herb");
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
            player.addItem("blue herb");
            System.out.println("Your village had been invaded by a military group, and as a"
                    + "\nresult, you had lost everything you ever cherised or cared"
                    + "\nabout. You sought out the man responsible for the deaths of"
                    + "\nyour loved ones, which brought you to a dungeon on the"
                    + "\noutskirts of the neighboring country.");
        }
        System.out.println("\nAs you near the dungeon, the mist begins to get thicker...\n");

        currentRoom = entrance;

        ////////////////////////////// THE GAME //////////////////////////////
        System.out.println(currentRoom.roomState()); // room description for the starting room

        while (playing) // loop to keep the game going
        {
            if (!intentionalFight || !running) // boolean intentionalFight will be true whenever the player chooses to fight something
            {
                if (currentRoom.ambushCheck(playerClass)) // checks to see if the player is going to get ambushed
                {
                    enemyAttacking = currentRoom.randomEnemy();

                    fighting = true;
                    firstTurn = false; // the enemy will get the first turn of the battle
                }
            } // gives the player 1 turn without getting ambushed after running away
            running = false;

            if (allowedMove) // will be true after the player moves to a new room
            {
                System.out.println(currentRoom.roomState()); // prints upon entering room
                allowedMove = false;
            }

            if (fighting)
            {
                playerTurn = false;
                turn = 0; //Will keep track of the amount of turns
                guarding = false;
                running = false;

                copyEnemyObjects();

                System.out.println("-----COMBAT-----\n");

                if (firstBattle) // the player gets a small tutorial on their first battle
                {
                    System.out.println("It seems like you've entered your first battle!\n");
                    System.out.println("Every turn, you get the option to [ATTACK], use [SPELLS],"
                            + "\n[GUARD], use an [ITEM], or [FLEE] if you're a coward.\n");
                    System.out.println("Watch your HP! When it becomes 0, it's GAME OVER.");
                    System.out.println("Press ENTER to continue.");
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
                    System.out.println(enemy.getName().toUpperCase() + " HP: " + enemy.hpVisual());

                    playerTurn = false;
                    guarding = false;

                    if (!firstTurn) // if the player doesn't have the first turn
                    {
                        System.out.println("You are ambushed by a " + enemyAttacking + "!");
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
                        System.out.println("///////////////YOUR TURN///////////////");
                        System.out.println("[Attack]  [Spells]  [Guard]  [Item]  [Flee]\n");

                        // start of player turn
                        while (playerTurn) // repeats until the player has inputted a valid command
                        {
                            input = scan.nextLine().trim();
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
                                spellsCommandBattle();
                            }
                            else if (input.equals("guard"))
                            {
                                System.out.println("\nYou prepare for the enemy's next attack.");
                                guarding = true; // will affect the amount of damage the enemy does during its turn
                                playerTurn = false;
                            }
                            else if (input.equals("item"))
                            {
                                int size = player.inventorySize();
                                useCommand();
                                // if the size of the inventory has decreased
                                // the player has successfully used an item
                                if (player.inventorySize() < size)
                                {
                                    playerTurn = false;
                                }
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
                    System.out.println("Press ENTER to continue.");
                    scan.nextLine();

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
                    if (player.getDeathState()) // player is dead
                    {
                        System.out.println("\nYou succumb to your wounds."); // womp womp
                        System.out.println("\n////////////////////////////// GAME OVER //////////////////////////////");
                        playing = false;
                    }
                    else if (enemy.getDeathState())
                    {
                        System.out.println("\nYou are victorious!");
                        currentRoom.removeEnemy(currentRoom.getEnemyIndex(enemyAttacking)); // removes the dead enemy from the room's enemyList

                        collectXP();

                        // resets status effects
                        enemyBurning = false;
                        enemyBlinded = false;

                        addEnemyCorpse(); // adds the enemy's corpse to the room as an object
                        enemyAttacking = "";
                        intentionalFight = false; // resets for the next fight
                    }
                }

            }
            else
            {
                input = scan.nextLine().trim();
                input = input.toLowerCase();
                timeCount++; // increases this counter for every "turn" the player takes within a room

                System.out.println("\n> " + input);

                ////////////////////////////// COMMANDS //////////////////////////////
                // each turn outside of combat lets the player input commands to do different things

                if (input.equals("help"))
                {
                    System.out.println("These are the commands:");
                    System.out.println("inventory = lists the items in your inventory");
                    System.out.println("profile = opens your profile");
                    System.out.println("spells = displays the spells you've learned");
                    System.out.println("move = lets you move to different areas");
                    System.out.println("look = describes the area around you");
                    System.out.println("use = lets you use an item from your inventory");
                    System.out.println("examine = lets you examine an object in the room");
                    System.out.println("search = lets you search an object in the room");
                    System.out.println("quit = quits the game :(");
                }

                //movement
                if (input.equals("move"))
                {
                    System.out.println("\nWhere do you want to move? (North/East/South/West)");

                    input = scan.nextLine().trim();
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

                if (input.equals("take") || input.equals("pick up") || input.equals("grab"))
                {
                    takeCommand();
                }

                if (input.equals("search") || input.equals("loot"))
                {
                    searchCommand();
                }

                if (input.equals("examine"))
                {
                    examineCommand();
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
    private static void moveTo(String direction) // handles the code for the player moving from rooms in different locations
    {
        if (currentRoom.getName().equals("Entrance") && (direction.equals("north") || direction.equals("n")))
        {
            currentRoom = courtyard;
        }
        else if (currentRoom.getName().equals("Courtyard") && (direction.equals("south") || direction.equals("s")))
        {
            currentRoom = entrance;
        }
        else if (currentRoom.getName().equals("Courtyard") && (direction.equals("east") || direction.equals("e")))
        {
            currentRoom = innerHall;
        }
        else if (currentRoom.getName().equals("Inner Hall") && (direction.equals("west") || direction.equals("w")))
        {
            currentRoom = courtyard;
        }
        else if (currentRoom.getName().equals("Inner Hall") && (direction.equals("north") || direction.equals("n")))
        {
            currentRoom = cavern;
        }
        else if (currentRoom.getName().equals("Cavern") && (direction.equals("south") || direction.equals("s")))
        {
            currentRoom = innerHall;
        }
        else if (currentRoom.getName().equals("Cavern") && (direction.equals("west") || direction.equals("w")))
        {
            currentRoom = mines;
        }
        else if (currentRoom.getName().equals("Mines") && (direction.equals("east") || direction.equals("e")))
        {
            currentRoom = cavern;
        }
        else if (currentRoom.getName().equals("Cavern") && (direction.equals("east") || direction.equals("e")))
        {
            currentRoom = eastWing;
        }
        else if (currentRoom.getName().equals("East Wing") && (direction.equals("west") || direction.equals("w")))
        {
            currentRoom = cavern;
        }
        else if (currentRoom.getName().equals("East Wing") && (direction.equals("north") || direction.equals("n")))
        {
            currentRoom = catacombs;
        }
        else if (currentRoom.getName().equals("Catacombs") && (direction.equals("south") || direction.equals("s")))
        {
            currentRoom = eastWing;
        }
        else if (currentRoom.getName().equals("Catacombs") && (direction.equals("north") || direction.equals("north")))
        {
            currentRoom = catacombCell;
        }
        else if (currentRoom.getName().equals("Catacomb Cell") && (direction.equals("south") || direction.equals("s")))
        {
            currentRoom = catacombs;
        }
        else if (currentRoom.getName().equals("Courtyard") && (direction.equals("west") || direction.equals("w")))
        {
            currentRoom = basement;
        }
        else if (currentRoom.getName().equals("Basement") && (direction.equals("east") || direction.equals("e")))
        {
            currentRoom = courtyard;
        }
        else if (currentRoom.getName().equals("Basement") && (direction.equals("north") || direction.equals("n")))
        {
            currentRoom = prisonA;
        }
        else if (currentRoom.getName().equals("Prison") && (direction.equals("south") || direction.equals("s")))
        {
            currentRoom = basement;
        }
        else if (currentRoom.getName().equals("Prison") && (direction.equals("west") || direction.equals("w")))
        {
            currentRoom = westWing;
        }
        else if (currentRoom.getName().equals("Prison") && (direction.equals("north") || direction.equals("n")))
        {
            currentRoom = prisonB;
        }
        else if (currentRoom.getName().equals("Deep Prison") && (direction.equals("south") || direction.equals("s")))
        {
            currentRoom = prisonA;
        }
        else if (currentRoom.getName().equals("West Wing") && (direction.equals("east") || direction.equals("e")))
        {
            currentRoom = prisonA;
        }
        else if (currentRoom.getName().equals("West Wing") && (direction.equals("north") || direction.equals("n")))
        {
            currentRoom = hiddenChamber;
        }
        else if (currentRoom.getName().equals("Hidden Chamber") && (direction.equals("south") || direction.equals("s")))
        {
            currentRoom = westWing;
        }
    }

    private static void enemyTurn()
    {
        int factor = 3;
        // knights get a bonus by increasing the factor by which enemy damage is reduced when guarding
        if (playerClass.equals("knight"))
        {
            factor = 10;
        }

        System.out.println("\n///////////////ENEMY TURN///////////////");

        // if-statement for text for different enemies
        if (enemy.getName().equals("feral hound"))
        {
            System.out.println("\nThe feral hound growls at you...");
            System.out.println("It lunges at you!");
        }
        else if (enemy.getName().equals("rabid hound"))
        {
            System.out.println("\nThe rabid hound snarls at you...");
            System.out.println("It pounces at you!");
        }
        else if (enemy.getName().equals("prison guard"))
        {
            System.out.println("\nThe prison guard raises its cleaver...");
            System.out.println("It swings at you!");
        }
        else if (enemy.getName().equals("ballistic guard"))
        {
            System.out.println("\nThe prison guard readies its ballista...");
            System.out.println("It shoots at you!");
        }
        else if (enemy.getName().equals("elite guard"))
        {
            System.out.println("\nThe elite guard readies its cleaver...");
            System.out.println("It swings at you!");
        }
        else if (enemy.getName().equals("prisoner ghoul"))
        {
            System.out.println("\nThe ghoul groans...");
            System.out.println("It tries to scratch you!");
        }
        else if (enemy.getName().equals("silly skeleton"))
        {
            System.out.println("\nThe skeleton unhinges its jaw, but no noise comes out...");
            System.out.println("It tries to grab you!");
        }

        if (enemy.enemyMissChance(playerClass, enemyBlinded)) // sees if the enemy misses its attack
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
            damage -= player.getDef()/3;
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

        System.out.println("Press ENTER to continue.");
        scan.nextLine();

    }

    private static boolean burningChance() // calculates if the enemy catches on fire
    {
        boolean output = false;
        int chance = Game.generator.nextInt(11)+1;
        if (chance <= 3) // 30% chance
        {
            output = true;
        }
        return output;
    }

    private static void copyEnemyObjects() // copying the attributes of the correspoding actor object
    {
        if (enemyAttacking.equals("feral hound"))
        {
            enemy.copyActor(hound);
        }
        else if (enemyAttacking.equals("rabid hound"))
        {
            enemy.copyActor(hound2);
        }
        else if (enemyAttacking.equals("prison guard"))
        {
            enemy.copyActor(prisonGuardA);
        }
        else if (enemyAttacking.equals("ballistic guard"))
        {
            enemy.copyActor(prisonGuardB);
        }
        else if (enemyAttacking.equals("elite guard"))
        {
            enemy.copyActor(prisonGuardC);
        }
        else if (enemyAttacking.equals("prisoner ghoul"))
        {
            enemy.copyActor(ghoul);
        }
        else if (enemyAttacking.equals("silly skeleton"))
        {
            enemy.copyActor(skeleton);
        }
    }

    private static void addEnemyCorpse() // after defeating an enemy, adds a corpse to the objects of the room
    {
        if (enemyAttacking.equals("feral hound"))
        {
            currentRoom.addObj("feral hound corpse");
        }
        else if (enemyAttacking.equals("rabid hound"))
        {
            currentRoom.addObj("rabid hound corpse");
        }
        else if (enemyAttacking.equals("prison guard"))
        {
            currentRoom.addObj("prison guard corpse");
        }
        else if (enemyAttacking.equals("ballistic guard"))
        {
            currentRoom.addObj("ballistic guard corpse");
        }
        else if (enemyAttacking.equals("elite guard"))
        {
            currentRoom.addObj("elite guard corpse");
        }
        else if (enemyAttacking.equals("prisoner ghoul"))
        {
            currentRoom.addObj("ghoul corpse");
        }
        else if (enemyAttacking.equals("silly skeleton"))
        {
            currentRoom.addObj("sheleton corpse");
        }
    }

    private static void fightCommand()
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

                input = scan.nextLine().trim();
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

    private static void useCommand()
    {
        System.out.println(player.openInventory());
        if (player.inventorySize() > 0) // checks if the player has anything
        {
            System.out.println("\nWhat do you want to use?");

            input = scan.nextLine().trim();
            input = input.toLowerCase();

            System.out.println("\n> " + input);

            if (input.equals("blue herb"))
            {
                if (player.hasItem(input))
                {
                    System.out.println("You use the " + input + ".");
                    player.subItem(player.inventoryIndex("blue herb"));
                    player.addHp(30); // blue herb adds 30 hp
                }
                else
                {
                    System.out.println("You don't have a blue herb to use!");
                }
            }
            if (input.equals("dried meat"))
            {
                if (player.hasItem(input))
                {
                    System.out.println("You eat the " + input + ".");
                    player.subItem(player.inventoryIndex("dried meat"));
                    player.addDef(5); // dried meat adds def
                }
                else
                {
                    System.out.println("You don't have a dried meat to eat!");
                }
            }
            if (input.equals("meatpie"))
            {
                if (player.hasItem(input))
                {
                    System.out.println("You eat the " + input + ".");
                    player.subItem(player.inventoryIndex("meatpie"));
                    player.addAtk(5); // meatpie adds atk
                }
                else
                {
                    System.out.println("You don't have a meatpie to eat!");
                }
            }
            else if (input.equals("glass shards"))
            {
                if (player.hasItem(input))
                {
                    System.out.println("You throw the glass shards at the " + enemyAttacking + "!");
                    player.subItem(player.inventoryIndex("glass shards"));
                    if (enemy.blindedChance())
                    {
                        enemyBlinded = true;
                        System.out.println("The enemy is blinded!");
                    }
                    else
                    {
                        System.out.println("The glass shards miss!");
                    }
                }
                else
                {
                    System.out.println("You don't have glass shards to use!");
                }
            }
            else if (input.equals("blue vial"))
            {
                if (player.hasItem(input))
                {
                    System.out.println("You use the " + input + ".");
                    player.subItem(player.inventoryIndex("blue vial"));
                    player.addHp(80); // blue vial adds 80 hp
                }
                else
                {
                    System.out.println("You don't have a blue vial to use!");
                }
            }
            else if (input.equals("matches"))
            {
                if (player.hasItem(input))
                {
                    System.out.println("You use the " + input + ".");
                    player.subItem(player.inventoryIndex("matches"));
                    System.out.println("You carry the lit match with your non dominant hand.");
                    if (!currentRoom.getLight()) //if player is in a room without light it updates the room desc
                    {
                        System.out.println("\nYou can see now!");
                        if (currentRoom.getName().equals("Cavern"))
                        {
                            cavern.setLight(true);
                        }
                        else
                        {
                            prisonB.setLight(true);
                        }
                        System.out.println(currentRoom.roomState()); //prints the room desc againeere
                    }
                    else
                    {
                        cavern.setLight(true);
                        prisonB.setLight(true);
                    }
                }
                else
                {
                    System.out.println("You don't have any matches to use!");
                }
            }
            else if (input.equals("rusty key"))
            {
                if (player.hasItem(input) && currentRoom.objsContains("metal door"))
                {
                    System.out.println("You try fitting the " + input + " into the metal door.");
                    System.out.println("It fits perfectly!");
                    currentRoom.removeObj(currentRoom.getObjIndex("metal door"));
                    player.subItem(player.inventoryIndex("rusty key"));
                    metalDoor.setLock(false);
                    currentRoom.setNorthExit(true);
                    metalDoor.setDesc("The metal door is rusting at its edges. It is unlocked now.");
                    System.out.println(currentRoom.roomState());
                }
                else
                {
                    System.out.println("You don't have a rusty key to use!");
                }
            }
            else if (input.equals("copper key"))
            {
                if (player.hasItem(input) && currentRoom.objsContains("metal gate"))
                {
                    System.out.println("You try fitting the " + input + " into the metal gate.");
                    System.out.println("It fits perfectly!");
                    currentRoom.removeObj(currentRoom.getObjIndex("metal gate"));
                    player.subItem(player.inventoryIndex("copper key"));
                    metalGate.setLock(false);
                    currentRoom.setWestExit(true);
                    metalDoor.setDesc("The metal gate is unlocked now.");
                    System.out.println(currentRoom.roomState());
                }
                else
                {
                    System.out.println("You don't have a copper key to use!");
                }
            }
            else if (input.equals("large key"))
            {
                if (player.hasItem(input) && currentRoom.objsContains("giant door"))
                {
                    System.out.println("You try fitting the " + input + " into the giant door.");
                    System.out.println("It fits perfectly!");
                    currentRoom.removeObj(currentRoom.getObjIndex("metal gate"));
                    player.subItem(player.inventoryIndex("large key"));
                    giantDoor.setLock(false);
                    currentRoom.setNorthExit(true);
                    metalDoor.setDesc("The giant door is unlocked now.");
                    System.out.println(currentRoom.roomState());
                }
                else
                {
                    System.out.println("You don't have a copper key to use!");
                }
            }
            else
            {
                System.out.println("\nInvalid item.");
            }
        }
    }

    private static void takeCommand()
    {
        if (currentRoom.getObjCount() > 0) // checks if the player has anything
        {
            System.out.println("\nWhat do you want to take?");

            input = scan.nextLine().trim();
            input = input.toLowerCase();

            System.out.println("\n> " + input);

            if (input.equals("blue herb"))
            {
                if (currentRoom.objsContains(input))
                {
                    System.out.println("You take the " + input + ".");
                    player.addItem("blue herb");
                    currentRoom.removeObj(currentRoom.getObjIndex(input));
                }
                else
                {
                    System.out.println("\nThat's not in here.");
                }
            }
            else if (input.equals("dried meat"))
            {
                if (currentRoom.objsContains(input))
                {
                    System.out.println("You take the " + input + ".");
                    player.addItem("dried meat");
                    currentRoom.removeObj(currentRoom.getObjIndex(input));
                }
                else
                {
                    System.out.println("\nThat's not in here.");
                }
            }
            else if (input.equals("meatpie"))
            {
                if (currentRoom.objsContains(input))
                {
                    System.out.println("You take the " + input + ".");
                    player.addItem("meatpie");
                    currentRoom.removeObj(currentRoom.getObjIndex(input));
                }
                else
                {
                    System.out.println("\nThat's not in here.");
                }
            }
            else if (input.equals("blue vial"))
            {
                if (currentRoom.objsContains(input))
                {
                    System.out.println("You take the " + input + ".");
                    player.addItem("blue vial");
                    currentRoom.removeObj(currentRoom.getObjIndex(input));
                }
                else
                {
                    System.out.println("\nThat's not in here.");
                }
            }
            else if (input.equals("glass shards"))
            {
                if (currentRoom.objsContains(input))
                {
                    System.out.println("You take the " + input + ".");
                    player.addItem("glass shards");
                    currentRoom.removeObj(currentRoom.getObjIndex(input));
                }
                else
                {
                    System.out.println("\nThat's not in here.");
                }
            }
            else if (input.equals("matches"))
            {
                if (currentRoom.objsContains(input))
                {
                    System.out.println("You take the " + input + ".");
                    player.addItem("matches");
                    currentRoom.removeObj(currentRoom.getObjIndex(input));
                }
                else
                {
                    System.out.println("\nThat's not in here.");
                }
            }
            else if (input.equals("large key"))
            {
                if (currentRoom.objsContains(input))
                {
                    System.out.println("You take the " + input + ".");
                    player.addItem("large key");
                    currentRoom.removeObj(currentRoom.getObjIndex(input));
                }
                else
                {
                    System.out.println("\nThat's not in here.");
                }
            }
            else
            {
                System.out.println("\nInvalid item.");
            }
        }
    }

    private static void spellsCommandBattle()
    {
        System.out.println(player.openSpells());
        System.out.println("What spell do you want to use?");

        input = scan.nextLine().trim();
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

    private static void examineCommand()
    {
        System.out.println("What do you want to examine?");

        input = scan.nextLine().trim();
        input = input.toLowerCase();
        System.out.println("\n> " + input);
        if (input.equals("feral hound corpse"))
        {
            if (currentRoom.objsContains(input))
            {
                System.out.println(houndCorpse.getDesc());
            }
        }
        else if (input.equals("rabid hound corpse"))
        {
            if (currentRoom.objsContains(input))
            {
                System.out.println(houndCorpse2.getDesc());
            }
        }
        else if (input.equals("prison guard corpse"))
        {
            if (currentRoom.objsContains(input))
            {
                System.out.println(guardCorpse.getDesc());
            }
        }
        else if (input.equals("ballistic guard corpse"))
        {
            if (currentRoom.objsContains("prison guard corpse"))
            {
                System.out.println(guardCorpse.getDesc());
            }
        }
        else if (input.equals("elite guard corpse"))
        {
            if (currentRoom.objsContains("elite guard corpse"))
            {
                System.out.println(eliteCorpse.getDesc());
            }
        }
        else if (input.equals("ghoul corpse"))
        {
            if (currentRoom.objsContains(input))
            {
                System.out.println(ghoulCorpse.getDesc());
            }
        }
        else if (input.equals("skeleton corpse"))
        {
            if (currentRoom.objsContains(input))
            {
                System.out.println(skeletonCorpse.getDesc());
            }
        }
        else if (input.equals("blue herb"))
        {
            if (currentRoom.objsContains(input) || player.hasItem("blue herb"))
            {
                System.out.println(blueHerb.getDesc());
            }
        }
        else if (input.equals("blue vial"))
        {
            if (currentRoom.objsContains(input) || player.hasItem("blue vial"))
            {
                System.out.println(blueVial.getDesc());
            }
        }
        else if (input.equals("glass shards"))
        {
            if (currentRoom.objsContains(input) || player.hasItem("glass shards"))
            {
                System.out.println(glassShards.getDesc());
            }
        }
        else if (input.equals("matches"))
        {
            if (currentRoom.objsContains(input) || player.hasItem("matches"))
            {
                System.out.println(matches.getDesc());
            }
        }
        else if (input.equals("metal door"))
        {
            if (currentRoom.objsContains(input))
            {
                System.out.println(metalDoor.getDesc());
            }
        }
        else if (input.equals("minecart"))
        {
            if (currentRoom.objsContains(input))
            {
                System.out.println(minecart.getDesc());
            }
        }
        else if (input.equals("rusty key"))
        {
            if (player.hasItem("rusty key"))
            {
                System.out.println(rustyKey.getDesc());
            }
        }
        else if (input.equals("copper key"))
        {
            if (player.hasItem("copper key"))
            {
                System.out.println(copperKey.getDesc());
            }
        }
        else if (input.equals("metal gate"))
        {
            if (player.hasItem("metal gate"))
            {
                System.out.println(metalGate.getDesc());
            }
        }
        else if (input.equals("large key"))
        {
            if (player.hasItem("large key"))
            {
                System.out.println(largeKey.getDesc());
            }
        }
        else if (input.equals("giant door"))
        {
            if (player.hasItem("giant door"))
            {
                System.out.println(giantDoor.getDesc());
            }
        }
        else
        {
            System.out.println("\nInvalid input.");
        }
    }

    private static void searchCommand()
    {
        System.out.println("What do you want to search?");

        input = scan.nextLine().trim();
        input = input.toLowerCase();
        System.out.println("\n> " + input);
        if (input.equals("barrel"))
        {
            if (currentRoom.objsContains(input))
            {
                if (barrel.isLootable())
                {
                    barrel.getItems();
                    barrel.setLoot(false); //can no longer loot it
                    System.out.println("You looted the "  + input + "!");
                    System.out.println("+" + barrel.randomLoot(player, barrel));
                }
                else
                {
                    System.out.println("There's nothing in the " + input + ".");
                }
            }
            else
            {
                System.out.println("There's no " + input + " to search.");
            }
        }
        else if (input.equals("minecart"))
        {
            if (currentRoom.objsContains(input))
            {
                if (minecart.isLootable())
                {
                    minecart.getItems();
                    minecart.randomLoot(player, minecart);
                    minecart.setLoot(false); //can no longer loot it
                    System.out.println("You looted the "  + input + "!");
                }
                else
                {
                    System.out.println("There's nothing in the " + input + ".");
                }
            }
            else
            {
                System.out.println("There's no " + input + " to search.");
            }
        }
        else if (input.equals("elite guard corpse"))
        {
            if (currentRoom.objsContains(input))
            {
                if (eliteCorpse.isLootable())
                {
                    eliteCorpse.getItems();
                    eliteCorpse.transferItems(player);
                    eliteCorpse.setLoot(false); //can no longer loot it
                    System.out.println("You looted the "  + input + "!");
                    System.out.println("+copper key");
                }
                else
                {
                    System.out.println("The elite guard doesn't have anything valuable.");
                }
            }
            else
            {
                System.out.println("There's no " + input + " to search.");
            }
        }
        else
        {
            System.out.println("\nInvalid input.");
        }
    }

    private static void collectXP()
    {
        int xpGained = 0;
        if (enemyAttacking.equals("feral hound"))
        {
            xpGained = 100;
        }
        else if (enemyAttacking.equals("rabid hound"))
        {
            xpGained = 100;
        }
        else if (enemyAttacking.equals("prison guard"))
        {
            xpGained = 200;
        }
        else if (enemyAttacking.equals("ballistic guard"))
        {
            xpGained = 200;
        }
        else if (enemyAttacking.equals("elite guard"))
        {
            xpGained = 400;
        }
        else if (enemyAttacking.equals("prisoner ghoul"))
        {
            xpGained = 50;
        }
        else if (enemyAttacking.equals("silly skeleton"))
        {
            xpGained = 75;
        }
        System.out.println("You got " + xpGained + " XP!");
        player.levelCalc(xpGained);
    }
} // end main class
