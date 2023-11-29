import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Game
{
    static boolean fighting = false;
    static Room currentRoom = new Room();
    static Actor enemy = new Actor();
    static Random generator = new Random();
    static String enemyAttacking = "";

    ////////////////////////////// ENEMY AND ROOM OBJECTS //////////////////////////////
    static Room entrance = new Room("Entrance", "You stand in front of a decrepit dungeon.", true, false, false, false, true, new ArrayList<>(), new ArrayList<>());
    static Room courtyard = new Room("Courtyard", "You stand in the middle of a large courtyard. The air hangs still...", true, false, true, true, true, List.of("Feral Hound"), new ArrayList<>());

    static Actor hound = new Actor("Feral Hound", 1, 5, 400, 400, 0, "claws", new ArrayList<>(), false);

    ////////////////////////////// MAIN METHOD //////////////////////////////
    public static void main (String[] args)
    {
        //VARIABLES
        boolean playing = true;
        boolean confirming = true;
        String input;
        String playerName = "";
        String playerClass = "";
        int timeCount = 0;
        boolean firstTurn = true;
        boolean firstBattle = true;
        boolean allowedMove = false;

        Scanner scan = new Scanner (System.in);
        Actor player = new Actor();

        //PLAYER CLASS DESCRIPTIONS
        String mercDesc = "The mercenary specializes in SOMETHING";
        String knightDesc = "The knight excels in SOMETHING";
        String priestDesc = "The dark priest has a strong affinity with magic BLAH BLAH";
        String outlanderDesc = "The outlander uses brute strength BLAH BLAH";

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
            player.setDef(20);
            player.setGold(15);
            player.setWeapon("dagger");
            player.addItem("something");

        }
        else if (playerClass.equals("knight"))
        {

            player.setAtk(30);
            player.setDef(30);
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
            player.addSpell("cool spell");

        }
        else //outlander
        {

            player.setAtk(40);
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
            fighting = currentRoom.ambushCheck();
            firstTurn = !fighting;

            if (allowedMove) //allowedMove is false until the player moves
            {
                System.out.println(currentRoom.roomState()); //will print upon entering room
                allowedMove = false;
                timeCount = 0; //time reset
            }

            if (fighting)
            {
                int damage;
                boolean playerTurn = false;
                boolean guarding = false;

                enemyAttacking = currentRoom.enemyCheck();

                if (enemyAttacking.equals("Feral Hound"))
                {
                    enemy = hound;
                }

                System.out.println("You are ambushed by a " + enemyAttacking + ".");
                System.out.println("\n-----COMBAT-----\n");

                if (firstBattle) //TUTORIAL
                {
                    System.out.println("It seems like you've entered your first battle!\n");
                    System.out.println("Every turn, you get the option to [ATTACK], [GUARD], use an [ITEM], or [FLEE] if you're a coward.\n");
                    System.out.println("Watch your HP! When it becomes 0, it's GAME OVER.\n");
                    firstBattle = false;
                }

                //ENTER COMBAT LOOP
                while (fighting)
                {
                    System.out.println("\n////////////////////////////////////////////////////////////");
                    System.out.println("\n" + player.getName().toUpperCase() + " HP: " + player.hpVisual());
                    System.out.println("\n" + enemy.getName().toUpperCase() + " HP: " + enemy.hpVisual());
                    playerTurn = false;
                    guarding = false;

                    if (!firstTurn) //extra enemy turn
                    {

                    }

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
                                System.out.println("You miss! The " + enemy.getName() + " narrowly dodges your attack!");
                            }
                            else
                            {
                                damage = player.dmgCalc();
                                System.out.println("\nYou deal " + damage + " damage to the " + enemy.getName() + ".");
                                enemy.subHp(damage);
                            }
                            playerTurn = false;
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
                            System.out.println("\nInvalid command. Use [Attack]/[Guard]/[Item]/[Flee].");
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

                    if (!fighting) //skips enemy turn if you're dead or the enemy is dead
                    {
                        //Enemy Turn
                        System.out.println("\nENEMY TURN\n");

                        if (enemy.getName().equals("Feral Hound"))
                        {
                            System.out.println("The feral hound growls at you...");
                        }

                        if (enemy.missChance())
                        {
                            System.out.println("\nYou dodge the attack!");
                        }
                        else
                        {
                            damage = enemy.dmgCalc();
                            if (guarding) //damage reduction
                            {
                                damage /= 2;
                            }
                            System.out.println("\nYou take " + damage + " damage!");
                            player.subHp(damage);
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

                    allowedMove = currentRoom.canMove(input); //true or false?

                    if (allowedMove)
                    {
                        Game.moveTo(input);
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
}

