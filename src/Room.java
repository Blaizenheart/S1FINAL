import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Room
{
    ////////////////////////////// INSTANCE VARIABLES //////////////////////////////
    private String name; // the name of the room
    private String desc; // the description that will be displayed upon entering the room
    
    // directions the player can exit to
    final private boolean northExit;
    final private boolean eastExit;
    final private boolean southExit;
    final private boolean westExit;
    
    private boolean light; // if the room is illuminated or not
    private List<String> enemyList; // list of enemies present in the room
    private List<String> interactiveObjs; // list of things the player can interact with

    ////////////////////////////// CONSTRUCTORS //////////////////////////////
    // Default Constructor
    public Room()
    {
        name = "";
        desc = ""; 
        northExit = false;
        eastExit = false;
        southExit = false;
        westExit = false;
        light = false;
        enemyList = new ArrayList<>();
        interactiveObjs = new ArrayList<>();

    }

    //Full Constructor
    public Room(String name, String desc, boolean northExit, boolean eastExit, boolean southExit,
    boolean westExit, boolean light, List<String> enemyList, List<String> interactiveObjs)
    {
        this.name = name;
        this.desc = desc;
        this.northExit = northExit;
        this.eastExit = eastExit;
        this.southExit = southExit;
        this.westExit = westExit;
        this.light = light;
        this.enemyList = new ArrayList<>(enemyList);
        this.interactiveObjs = new ArrayList<>(interactiveObjs);
    }

    ////////////////////////////// GETTERS //////////////////////////////
    public String getName()
    {
        return name;
    }

    public String getDesc()
    {
        return desc;
    }

    public boolean getNorthExit()
    {
        return northExit;
    }

    public boolean getEastExit()
    {
        return eastExit;
    }

    public boolean getSouthExit()
    {
        return southExit;
    }

    public boolean getWestExit()
    {
        return westExit;
    }

    public boolean getLight()
    {
        return light;
    }

    public int getEnemyCount()
    {
        return enemyList.size();
    }
    
    public String getEnemy(int index) 
    {
        // returns the enemy at a specific index of enemyList
        return enemyList.get(index);
    }
    
    public int getEnemyIndex(String search)
    {
        // returns the index of an enemy within enemyList
        return enemyList.indexOf(search);
    }
    
    public boolean enemyListContains(String search)
    {
        //returns true if the enemyList contains a certain enemy
        boolean output = false;
        if (enemyList.contains(search))
        {
            output = true;
        }
        return output;
    }

    ////////////////////////////// SETTERS ////////////////////////////// 
    public void setLight(boolean light)
    {
        this.light = light;
    }
    
    public void addInteractiveObj(String obj)
    {
        interactiveObjs.add(obj);
    }
    
    public void removeEnemy(int index)
    {
        //removes an enemy at a certain index of enemyList
        enemyList.remove(index);
    }
    
    ////////////////////////////// TO STRING //////////////////////////////
    public String getInteractiveObjs() //outputs a list of interactive objects in the room
    {
        String output = "";
        if (!interactiveObjs.isEmpty()) 
        {
            output = "There is ";
            if (interactiveObjs.size() == 1) 
            {
                output += "a " + interactiveObjs.get(0) + ".";
            } 
            else 
            {
                for (int i = 0; i < interactiveObjs.size() - 1; i++) 
                {
                    output += interactiveObjs.get(i) + ", ";
                }
                output += "and a " + interactiveObjs.get(interactiveObjs.size() - 1) + ".";
            }
        }
        return output;
    }
    
    public String getEnemyList() // outputs the list of enemies in the room
    {
        String output = "";
        if (!enemyList.isEmpty()) 
        {
            output = "There is ";
            if (enemyList.size() == 1) 
            {
                output += "a " + enemyList.get(0) + ".";
            } 
            else 
            {
                for (int i = 0; i < enemyList.size() - 1; i++) 
                {
                    output += enemyList.get(i) + ", ";
                }
                output += "and a " + enemyList.get(enemyList.size() - 1) + ".";
            }
        }
        return output;
    }
    
    public String roomState()
    {
        String output = "\n-----" + name + "-----\n";
        if (light) // checks to see if the room has light
        {
            output += desc;
            // lists out possible exits
            output += "\nExits: ";
            if (northExit)
            {
                output += "N ";
            }
            if (eastExit)
            {
                output += "E ";
            }
            if (southExit)
            {
                output += "S ";
            }
            if (westExit)
            {
                output += "W ";
            }
            
            if (enemyList.size() > 0)
            {
                output += "\nThere are " + enemyList.size() + " hostile enemies nearby: ";
                for (String enemy : enemyList)
                {
                    output += enemy;
                }
            }

            output += "\n";
        }
        else
        {
            output += "It's too dark to see anything.";
        }
        return output;
    }

    public boolean ambushCheck(String playerClass) // calculates and returns true if the player is ambushed
    {
        int chance = Game.generator.nextInt(11) + 1; // 1 - 10
        boolean ambush = false;
        int bonus = 0;
        if (playerClass.equals("mercenary")) // mercenaries have a decreased chance of being ambushed
        {
            bonus = 2;
        }
        if (enemyList.size() > 0) // player can only get ambushed when enemies are present
        {
            if (light) // odds of getting ambushed change depending on whether the room is lit up or not
            {
                if (chance >= 5 + bonus) //50% chance (30% if mercenary)
                {
                    ambush = true;
                }
                else
                {
                    ambush = false;
                }
            }
            else
            {
                if (chance >= 3 + bonus) // 70% chance (50% chance if mercenary)
                {
                    ambush = true;
                }
                else
                {
                    ambush = false;
                }
            }
        }
        return ambush;
    }
    
    public String randomEnemy() //Returns random enemy
    {
        String result;
        int index = Game.generator.nextInt(enemyList.size());
        result = enemyList.get(index);
        return result;
    }
    
    public boolean canMove(String direction) // returns true if the player can move in a certain direction
    {
        boolean output = false;
        if ((direction.equals("north") || direction.equals("n")) && northExit)
        {
            output = true;
        }
        else if ((direction.equals("east") || direction.equals("e")) && eastExit)
        {
            output = true;
        }
        else if ((direction.equals("south") || direction.equals("s")) && southExit)
        {
            output = true;
        }
        else if ((direction.equals("west") || direction.equals("w")) && westExit)
        {
            output = true;
        }
        return output;
    }
}
