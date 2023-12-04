import java.util.List;
import java.util.ArrayList;

public class Room
{
    ////////////////////////////// INSTANCE VARIABLES //////////////////////////////
    private final String NAME; // the name of the room
    private final String DESC; // the description that will be displayed upon entering the room

    // directions the player can exit to
    private boolean northExit;
    private boolean eastExit;
    private boolean southExit;
    private boolean westExit;

    private boolean light; // if the room is illuminated or not
    private List<String> enemyList; // list of enemies present in the room
    private List<String> objs; // list of things the player can interact with

    ////////////////////////////// CONSTRUCTORS //////////////////////////////
    // Default Constructor
    public Room()
    {
        NAME = "";
        DESC = "";
        northExit = false;
        eastExit = false;
        southExit = false;
        westExit = false;
        light = false;
        enemyList = new ArrayList<>();
        objs = new ArrayList<>();

    }

    //Full Constructor
    public Room(String name, String desc, boolean northExit, boolean eastExit, boolean southExit,
                boolean westExit, boolean light, List<String> enemyList, List<String> objs)
    {
        this.NAME = name;
        this.DESC = desc;
        this.northExit = northExit;
        this.eastExit = eastExit;
        this.southExit = southExit;
        this.westExit = westExit;
        this.light = light;
        this.enemyList = new ArrayList<>(enemyList);
        this.objs = new ArrayList<>(objs);
    }

    ////////////////////////////// GETTERS //////////////////////////////
    public String getName()
    {
        return NAME;
    }

    public String getDesc()
    {
        return DESC;
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

    public int getObjCount()
    {
        return objs.size();
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

    public String getObj(int index)
    {
        // returns the obj at a specific index of objs
        return objs.get(index);
    }

    public int getObjIndex(String search)
    {
        // returns the index of an obj within objs
        return objs.indexOf(search);
    }

    ////////////////////////////// SETTERS //////////////////////////////
    public void setLight(boolean light)
    {
        this.light = light;
    }

    public void setNorthExit(boolean state)
    {
        northExit = state;
    }

    public void setEastExit(boolean state)
    {
        eastExit = state;
    }

    public void setSouthExit(boolean state)
    {
        southExit = state;
    }

    public void setWestExit(boolean state)
    {
        westExit = state;
    }

    public void addObj(String obj)
    {
        objs.add(obj);
    }

    public void removeEnemy(int index)
    {
        //removes an enemy at a certain index of enemyList
        enemyList.remove(index);
    }

    public void removeObj(int index)
    {
        //removes an obj at a certain index of objs
        objs.remove(index);
    }

    ////////////////////////////// BRAIN METHODS //////////////////////////////
    public boolean ambushCheck(String playerClass)
    { // calculates and returns true if the player is ambushed
        int chance = Game.generator.nextInt(11) + 1; // 1 - 10
        boolean ambush = false;
        int bonus = 0;
        // mercenaries have a decreased chance of being ambushed
        if (playerClass.equals("mercenary"))
        {
            bonus = 2;
        }
        if (!enemyList.isEmpty()) // player can only get ambushed when enemies are present
        {
            // odds of getting ambushed change depending on whether the room is lit up or not
            if (light)
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

    public boolean objsContains(String search)
    {
        //returns true if the list of objs contains a certain object
        boolean output = false;
        if (objs.contains(search))
        {
            output = true;
        }
        return output;
    }

    public String randomEnemy() //Returns random enemy
    {
        String result;
        int index = Game.generator.nextInt(enemyList.size());
        result = enemyList.get(index);
        return result;
    }

    public boolean canMove(String direction)
    { // returns true if the player can move in a certain direction
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

    ////////////////////////////// TO STRING //////////////////////////////
    public String getObjs() //outputs a list of interactive objects in the room
    {
        String output = "";
        if (!objs.isEmpty())
        {
            output = "There is ";
            if (objs.size() == 1)
            {
                output += "a " + objs.get(0) + ".";
            }
            else
            {
                for (int i = 0; i < objs.size() - 1; i++)
                {
                    output += objs.get(i) + ", ";
                }
                output += "and a " + objs.get(objs.size() - 1) + ".";
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
        String output = "\n-----" + NAME + "-----\n";
        if (light) // checks to see if the room has light
        {
            output += DESC;
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
            if (!enemyList.isEmpty())
            {
                output += "\n" + getEnemyList();
            }
            if (!objs.isEmpty())
            {
                output += "\n" + getObjs();
            }
            output += "\n";
        }
        else
        {
            output += "It's too dark to see anything.";
        }
        return output;
    }
}
