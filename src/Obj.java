import java.util.List;
import java.util.ArrayList;

public class Obj
{
    ////////////////////////////// INSTANCE VARIABLES //////////////////////////////
    private String name;
    private String desc; // description to print for the player upon using the "examine (object)" command
    private boolean locked; // is it locked?
    private boolean lootable;
    private boolean obtainable; // can you take it?
    private List<String> itemsList; // items that could be inside of the object
    
    ////////////////////////////// CONSTRUCTORS //////////////////////////////
    // Default Constructor
    public Obj()
    {
        name = "";
        desc = "";
        locked = false;
        lootable = false;
        obtainable = false;
        itemsList = new ArrayList<>();
    }
    
    // Full Constructor
    public Obj(String name, String desc, boolean locked, boolean lootable, boolean obtainable, List<String> itemsList)
    {
        this.name = name;
        this.desc = desc;
        this.locked = locked;
        this.lootable = lootable;
        this.obtainable = obtainable;
        this.itemsList = new ArrayList<>(itemsList);
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
    
    public boolean isLocked()
    {
        return locked;
    }
    
    public boolean isLootable()
    {
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
    
    public void setLoot()
    {
        lootable = false;
    }
    
    public void removeItem(int index)
    {
        itemsList.remove(index);
    }
    
    ////////////////////////////// BRAIN METHODS //////////////////////////////
    
    ////////////////////////////// TO STRING //////////////////////////////
    public String getItems() //meant to output the items inside the interactive obj (usually some kind of container)
    {
        String output = "";
        if (!itemsList.isEmpty()) 
        {
            output = "Inside the " + name + " is ";
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
