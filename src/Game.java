import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Game
{
    //STATIC VARIABLES
    static boolean fighting = false;
    static String enemyAttacking = "";
    static boolean guarding = false;
    static int damage;
    static String playerName = "";
    static String playerClass = "";
    static int turn;
    
    //STATUS EFFECTS
    static boolean playerBurning = false;
    static boolean enemyBurning = false;
     
    static Random generator = new Random();
    
    ////////////////////////////// ACTORS AND ROOM OBJECTS //////////////////////////////
    static Room entrance = new Room("Entrance", "You stand in front of a decrepit dungeon. The mist is getting thicker.", true, false, false, false, true, new ArrayList<String>(), new ArrayList<String>());
    static Room courtyard = new Room("Courtyard", "You stand in the middle of a large courtyard. The air hangs still...", true, false, true, true, true, List.of("feral hound"), new ArrayList<String>());
    
    static Actor player = new Actor();
    static Actor enemy = new Actor();
    static Actor hound = new Actor("feral hound", 1, 5, 400, 400, 0, "claws", new ArrayList<String>(), false);
    static Room currentRoom = new Room();

    ////////////////////////////// MAIN METHOD //////////////////////////////
    public static void main (String[] args)
    {
        //VARIABLES
        boolean playing = true;
        boolean confirming = true;
        String input;
        int timeCount = 0;
        boolean firstTurn = true;
        boolean firstBattle = true;
        boolean allowedMove = false;
        
        Scanner scan = new Scanner (System.in);

        //PLAYER CLASS DESCRIPTIONS
        String mercDesc = "Mercenaries deal in close combat, making them more vulnerable to attacks, \nbut their stealth lets them walk around unnoticed.";
        String knightDesc = "Knights are especially resilient against enemy attacks, but their armor reduces their stealth.";
        String priestDesc = "The dark priest has a strong affinity to magic, but lacks physical strength.";
        String outlanderDesc = "The outlander uses a bow to attack the enemy from afar, dealing less damage but evading more attacks.";

        ////////////////////////////// PLAYER SETUP //////////////////////////////

        System.out.println("Welcome!");
        System.out.println("To get a list of commands, enter \"help\"\n----------");

        System.out.println("\nWhat's your name, traveler?");
        
        //Validation Loop
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

        player.setName(playerName);

        System.out.println("\nChoose your class:" +
                "\n\n1) Mercenary\n----------\n" + mercDesc +
                "\n\n2) Knight\n----------\n" + knightDesc +
                "\n\n3) Dark Priest\n----------\n" + priestDesc +
                "\n\n4) Outlander\n----------\n" + outlanderDesc);

        confirming = true;
        
        //Validation loop
        while (confirming)
        {
            int input2 = scan.nextInt();
            System.out.println("\n>" + input2);
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
        
        player.setPlayerClass(playerClass);
        
        //Conditions to set player attributes
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
            player.setAtk(30);
            player.setDef(20);
            player.setGold(10);
            player.setWeapon("bow");
            player.addItem("something");
        }
        
        currentRoom = entrance;
        
        ////////////////////////////// THE GAME //////////////////////////////
        
        System.out.println(currentRoom.roomState()); //Initial room description
        
        while (playing)
        {
            //Checks to see if the player is getting ambushed
            if (currentRoom.ambushCheck(playerClass))
            {
                enemyAttacking = currentRoom.enemyCheck();
                System.out.println("You are ambushed by a " + enemyAttacking + ".");
                fighting = true;
                firstTurn = false;
            }
            
            if (allowedMove == true) //allowedMove is false until the player moves
            {
                System.out.println(currentRoom.roomState()); //will print upon entering room
                allowedMove = false;
                timeCount = 0; //time reset
            }
            
            if (fighting)
            {
                boolean playerTurn = false;
                turn = 0; //Will keep track of the amount of turns
                guarding = false;
                
                copyEnemyObjects();
                
                System.out.println("\n-----COMBAT-----\n");
                
                if (firstBattle) //TUTORIAL
                {
                    System.out.println("It seems like you've entered your first battle!\n");
                    System.out.println("Every turn, you get the option to [ATTACK], use [SPELLS], [GUARD], use an [ITEM], or [FLEE] if you're a coward.\n");
                    System.out.println("Watch your HP! When it becomes 0, it's GAME OVER.\n");
                    firstBattle = false;
                }
                
                //ENTER COMBAT LOOP
                while (fighting)
                {
                    turn++;
                    System.out.println("\n////////////////////////////////////////////////////////////");
                    System.out.println("\nTURN " + turn);
                    System.out.println("\n" + player.getName().toUpperCase() + " HP: " + player.hpVisual());
                    System.out.println("\n" + enemy.getName().toUpperCase() + " HP: " + enemy.hpVisual());
                    playerTurn = false;
                    guarding = false;
                    
                    if (!firstTurn) //extra enemy turn
                    {
                        enemyTurn();
                        
                        System.out.println("\n" + player.getName().toUpperCase() + " HP: " + player.hpVisual());
                        System.out.println("\n" + enemy.getName().toUpperCase() + " HP: " + enemy.hpVisual());
                        
                        //Death Checks
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
                    
                    if (fighting)
                    {
                        playerTurn = true;
                        //Player Turn
                        System.out.println("\nYOUR TURN\n");
                        while (playerTurn)
                        {
                            input = scan.nextLine();
                            input = input.toLowerCase();
                    
                            System.out.println("\n>" + input);
                    
                            if (input.equals("attack"))
                            {
                                if (player.missChance())
                                {
                                    System.out.println("\nYou miss! The " + enemy.getName() + " narrowly dodges your attack!");
                                }
                                else
                                {
                                    damage = player.dmgCalc();
                                    System.out.println("\nYou deal " + damage + " damage to the " + enemy.getName() + ".");
                                    enemy.subHp(damage);
                                }
                                playerTurn = false;
                            }
                            else if (input.equals("spells") || input.equals("spell"))
                            {
                                System.out.println(player.openSpells());
                                System.out.println("What spell do you want to use?");
                                
                                input = scan.nextLine();
                                input = input.toLowerCase();
                                System.out.println("\n>" + input);
                                
                                if (input.equals("fireball"))
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
                                guarding = true;
                                playerTurn = false;
                            }
                            else if (input.equals("item"))
                            {
                                System.out.println(player.openInventory());
                                System.out.println("\nWhat do you want to use?");
                                
                                input = scan.nextLine();
                                input = input.toLowerCase();
                                System.out.println("\n>" + input);
                                //very long if ()
                            }
                            else if (input.equals("flee") || input.equals("run"))
                            {
                                if (player.runChance())
                                {
                                    System.out.println("\nYou ran away!");
                                    fighting = false;
                                }
                                else
                                {
                                    System.out.println("\nYou were unable to run away!");
                                }
                                playerTurn = false;
                            }
                            else
                            {
                                System.out.println("\nInvalid command. Use [Attack]/[Spells]/[Guard]/[Item]/[Flee].");
                            }
                        }
                    
                        //Death Checks
                        if (player.isDead())
                        {
                            fighting = false;
                        }
                        if (enemy.isDead())
                        {
                            fighting = false;
                        }
                    }
                    
                    if (fighting) //skips enemy turn if you're dead or the enemy is dead
                    {
                        //Enemy Turn
                        enemyTurn();
                        
                        //Death Checks
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
                if (player.getDeathState() == true) //Player is dead
                {
                    System.out.println("\nYou succumb to your wounds.");
                    System.out.println("\n////////////////////////////// GAME OVER //////////////////////////////");
                    break;
                }
                else if (enemy.getDeathState() == true)
                {
                    System.out.println("\nYou are victorious!");
                    currentRoom.removeEnemy(currentRoom.getEnemyIndex(enemyAttacking));
                }
            }
            else
            {
 
                input = scan.nextLine();
                input = input.toLowerCase();
                timeCount++;

                System.out.println("\n>" + input);

                if (input.equals("move"))
                {
                    allowedMove = false;
                    System.out.println("\nWhere do you want to move? (North/East/South/West)");

                    input = scan.nextLine();
                    input = input.toLowerCase();
                    
                    System.out.println("\n>" + input);
                    
                    allowedMove = currentRoom.canMove(input);
                    if (allowedMove)
                    {
                        Game.moveTo(input);
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

                if (input.equals("quit"))
                {
                    playing = false;
                }

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
                
                if (input.equals("look"))
                {
                    System.out.println(currentRoom.roomState());
                }
                
                if (input.equals("fight") || input.equals("attack"))
                {
                    if (currentRoom.getEnemyCount() > 0)
                    {
                        if (currentRoom.getEnemyCount() == 1)
                        {
                            enemyAttacking = currentRoom.getEnemy(0);
                            System.out.println("\nYou attack the " + enemyAttacking + "!");
                            
                            copyEnemyObjects();
                            fighting = true;
                        }
                        else
                        {
                            System.out.println("\nWhich enemy will you attack?");
                            
                            input = scan.nextLine();
                            input = input.toLowerCase();
                            
                            System.out.println("\n>" + input);
                            
                            if (currentRoom.enemyListContains(input))
                            {
                                enemyAttacking = currentRoom.getEnemy(currentRoom.getEnemyIndex(input));
                                System.out.println("You attack the " + enemyAttacking + "!");
                                copyEnemyObjects();
                                fighting = true;
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
            }
        }
    System.out.println("\n thx for playing!");
}
    // MOVEMENT
    public static void moveTo(String direction)
    {
        if (currentRoom.getName().equals("Entrance") && (direction.equals("north") || direction.equals("n")))
        {
            currentRoom = courtyard;
        }
    }
    
    public static void enemyTurn()
    {
        int factor = 2;
        //Knight bonus
        if (playerClass.equals("knight"))
        {
           factor = 3; 
        }

        //Enemy Turn
        System.out.println("\nENEMY TURN\n");
        
        if (enemy.getName().equals("feral hound"))
        {
            System.out.println("The feral hound growls at you...");
        }
                        
        if (enemy.enemyMissChance(playerClass))
        {
            System.out.println("\nYou dodge the attack!");
        }
        else
        {
            damage = enemy.dmgCalc();
            if (guarding) //damage reduction
            {
                damage /= factor;
            }
            System.out.println("\nYou take " + damage + " damage!");
            player.subHp(damage);
        }
        if (enemyBurning)
        {
            int burningDmg = generator.nextInt(100)+1;
            System.out.println("\nThe enemy is burning...");
            enemy.subHp(burningDmg);
            
            System.out.println("\nThe " + enemy.getName() + " takes an extra " + burningDmg + " damage!");
            
            burningDmg = generator.nextInt(100)+1;
            enemy.subHp(burningDmg);
            
            System.out.println("\nThe " + enemy.getName() + " takes an extra " + burningDmg + " damage!");
            
            burningDmg = generator.nextInt(100)+1;
            enemy.subHp(burningDmg);
            
            System.out.println("\nThe " + enemy.getName() + " takes an extra " + burningDmg + " damage!");
            enemyBurning = false;
        }
        
    }
    
    public static boolean burningChance()
    {
        boolean output = false;
        int chance = Game.generator.nextInt(11)+1;
        if (chance <= 3)
        {
            output = true;
        }
        return output;
    }
    
    public static void copyEnemyObjects()
    {
        if (enemyAttacking.equals("feral hound"))
        {
            enemy = hound;
        }
    }
}

