import java.util.List;
import java.util.ArrayList;

public class Obj
{
    ////////////////////////////// INSTANCE VARIABLES //////////////////////////////
    private final String NAME;
    private String desc; // description to print for the player upon using the "examine (object)" command
    private boolean locked; // is it locked?
    private boolean lootable;
    private boolean obtainable; // can you take it?
    private List<String> itemsList; // items that could be inside the object

    ////////////////////////////// CONSTRUCTORS //////////////////////////////
    // Default Constructor
    public Obj()
    {
        NAME = "";
        desc = "";
        locked = false;
        lootable = false;
        obtainable = false;
        itemsList = new ArrayList<>();
    }

    // Full Constructor
    public Obj(String name, String desc, boolean locked, boolean lootable, boolean obtainable, List<String> itemsList)
    {
        this.NAME = name;
        this.desc = desc;
        this.locked = locked;
        this.lootable = lootable;
        this.obtainable = obtainable;
        this.itemsList = new ArrayList<>(itemsList);
    }

    ////////////////////////////// GETTERS //////////////////////////////
    public String getName()
    {
        return NAME;
    }

    public String getDesc()
    {
        return desc;
    }

    public boolean isLocked()
    {
        return locked;
    }

    public boolean isLootable()
    {
        if (itemsList.size() == 0)
        {
            lootable = false;
        }
        return lootable;
    }

    public boolean isObtainable()
    {
        return obtainable;
    }

    public boolean containsItems()
    {
        boolean output = false;
        if (itemsList.size() > 0)
        {
            output = true;
        }
        return output;
    }

    ////////////////////////////// SETTERS ////////////////////////////// 
    public void setLock()
    {
        locked = false;
    }

    public void setLoot(boolean bool)
    {
        lootable = bool;
    }

    public void removeItem(int index)
    {
        itemsList.remove(index);
    }

    ////////////////////////////// BRAIN METHODS //////////////////////////////
    
    public void transferItems(Actor actor) // transfer items into the player's inventory
    {
        for (int i = 0; i < itemsList.size(); i++)
        {
            actor.addItem(itemsList.get(i));
        }
    }
    
    public void randomLoot(Actor actor, Obj obj) // player gets a random item
    {
        int rand;
        if (obj.getName().equals("barrel"))
        {
            rand = Game.generator.nextInt(3) + 1;  // 1-3
            if (rand == 1)
            {
                actor.addItem("blue vial");
            }
            else if (rand == 2)
            {
                actor.addItem("blue herb");
            }
            else if (rand == 3)
            {
                actor.addItem("glass shards");
            }
        }
    }
    
    ////////////////////////////// TO STRING //////////////////////////////
    public String getItems() // meant to output the items inside the interactive obj (usually some kind of container)
    {
        String output = "";
        if (!itemsList.isEmpty())
        {
            output = "Inside the " + NAME + " is ";
            if (itemsList.size() == 1)
            {
                output += "a " + itemsList.get(0) + ".";
            }
            else
            {
                for (int i = 0; i < itemsList.size() - 1; i++)
                {
                    output += itemsList.get(i) + ", ";
                }
                output += "and a " + itemsList.get(itemsList.size() - 1) + ".";
            }
        }
        return output;
    }
}
