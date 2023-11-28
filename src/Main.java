import java.util.Scanner;
import java.util.Random;

public class Main
{
    public static void main (String[] args) 
    {
        Scanner scan = new Scanner (System.in);
        boolean playing = true;
        boolean confirming = true;
        String input;
        String playerName;
        String playerClass;
        
        //class descriptions
        String mercDesc = "The mercenary specializes in SOMETHING";
        String knightDesc = "The knight excels in SOMETHING";
        String priestDesc = "The dark priest has a strong affinity with magic BLAH BLAH";
        String outlanderDesc = "The outlander uses brute strength BLAH BLAH";
        
        /////////////////////////////////////////////////////////////////////////////
        
        System.out.println("Welcome!");
        System.out.println("To get a list of commands, enter \"help\"\n----------");

        //instantiates eeeeh player data
        System.out.println("\nWhat's your name, traveler?");
        
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
        //player name secureeeddd
        
        //class select
        
        System.out.println("\nChoose your class:" +
        "\n\n1) Mercenary\n----------\n" + mercDesc +
        "\n\n2) Knight\n----------\n" + knightDesc +
        "\n\n3) Dark Priest\n----------\n" + priestDesc +
        "\n\n4) Outlander\n----------\n" + outlanderDesc);
        
        confirming = true;
        while (confirming)
            {
                //
                int input2 = scan.nextInt();
                System.out.println("\n>" + input2);
                if (input2 == 1)
                {
                    System.out.println("\nYou have chosen the Mercenary class.");
                    playerClass = "mercencary";
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
        //player classss secured

        //instantiates player object from actor class
        //starting inventory depends on class
        if (playerclass.equals("mercenary"))
        {
            String player = new Player(playerName, 1, 100, 0, List<String> inventory,  exp,  playerClass)l
        }
        
        while (playing)
        {
            //updates
            input = scan.nextLine();
            input = input.toLowerCase();

            //prints user input
            System.out.println("\n>" + input);
            
            //top priority
            if (input.equals("quit"))
            {
                playing = false;
            }
            
            if (input.equals("help"))
            {
                System.out.println("\nThese are the commands:\n");
                System.out.println("inventory = lists the items in your inventory");
            }
            //
            
            //commands
            if (input.equals("inventory"))//calls player actor object thing, for loop
            {
                //player.openInventory();
            }
            
            
        }
        System.out.println("thx for playing");
    }
}
