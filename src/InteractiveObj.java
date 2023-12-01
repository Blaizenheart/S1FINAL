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
}
