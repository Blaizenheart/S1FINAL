import java.util.List;
import java.util.ArrayList;

public class InteractiveObj
{
    ////////////////////////////// INSTANCE VARIABLES //////////////////////////////
    private String name;
    private String desc;
    private boolean locked;
    private boolean lootable;
    private boolean readable;
    private List<String> itemsList;
    
    ////////////////////////////// CONSTRUCTORS //////////////////////////////
    // Default Constructor
    public InteractiveObj()
    {
        name = "";
        desc = "";
        locked = false;
        lootable = false;
        readable = false;
        itemsList = new ArrayList<>();
    }
    
    // Full Constructor
    public InteractiveObj(String name, String desc, boolean locked, boolean lootable, boolean readable, List<String> itemsList)
    {
        this.name = name;
        this.desc = desc;
        this.locked = locked;
        this.lootable = lootable;
        this.readable = readable;
        this.items = new ArrayList<>(itemsList);
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
    
    public boolean isReadable()
    {
        return readable;
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
        loot = false;
    }
    
    public void removeItem(int index)
    {
        itemsList.remove(index);
    }
    
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
