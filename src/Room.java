import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Room
{
    static int index;
    private String name;
    private String desc;
    final private boolean northExit;
    final private boolean eastExit;
    final private boolean southExit;
    final private boolean westExit;
    private boolean light;
    private List<String> enemyList;
    private List<String> interactiveObjs;

    //zero arg constructor
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

    //full constructor
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

    //getters
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
    
    public String getInteractiveObjs() 
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

    public int getEnemyCount()
    {
        return enemyList.size();
    }
    
    public String getEnemy(int index)
    {
        return enemyList.get(index);
    }
    
    public int getEnemyIndex(String search)
    {
        return enemyList.indexOf(search);
    }
    
    public boolean enemyListContains(String search)
    {
        boolean output = false;
        if (enemyList.contains(search))
        {
            output = true;
        }
        return output;
    }

    public String getEnemyList() 
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

    //setters
    public void setLight(boolean light)
    {
        this.light = light;
    }
    
    public void addInteractiveObj(String obj)
    {
        interactiveObjs.add(obj);
    }
    
    //to string
    public String roomState()
    {
        String output = "\n-----" + name + "-----\n";
        if (light)
        {
            output += desc;
            //conditional to list out possible exits
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


    public boolean ambushCheck(String playerClass)
    {
        int chance = Game.generator.nextInt(11)+1;//1 to 10
        boolean ambush = false;
        int bonus = 0;
        if (playerClass.equals("mercenary"))
        {
            bonus = 2;
        }
        
        if (enemyList.size()>0)
        {
            if (light)
            {
                if (chance >= 5 + bonus) //50 50 chance
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
                if (chance >= 3 + bonus) //70 30 chance
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
    
    public String enemyCheck() //Returns random enemy
    {
        String result;
        index = Game.generator.nextInt(enemyList.size());
        result = enemyList.get(index);
        return result;
    }
    
    public void removeEnemy(int index)
    {
        enemyList.remove(index);
    }
    
    public boolean canMove(String direction)
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
