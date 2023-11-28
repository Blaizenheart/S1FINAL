import java.util.List;
import java.util.ArrayList;
//gonna use these to uhhh add items into an inventory or something

public class Actor 
{
    
    private String name;
    private int level;
    private int xp;
    private int atk;
    private int hp;
    private int def;
    private String weaponEquipped;
    private String playerClass;
    private int gold;
    private List<String> inventory;
    private List<String> spellsList;
    
    //0 constructor
    public Actor ()
    {
        name = "";
        level = 0;
        xp = 0;
        atk = 0;
        hp = 0;
        def = 0;
        weaponEquipped = "";
        playerClass = "";
        gold = 0;
        inventory = new ArrayList<>();
        spellsList = new ArrayList<>();
        
    }
    //full constructor (used for player only)
    public Actor (String name, int level, int xp, int atk, int hp, int def, String weapon, String playerClass, int gold, List<String> inventory, List<String> spellsList)
    {
        this.name = name;
        this.level = level;
        this.xp = xp;
        this.atk = atk;
        this.hp = hp;
        this.def = def;
        weaponEquipped = weapon;
        this.playerClass = playerClass;
        this.gold = gold;
        this.inventory = new ArrayList<>(inventory);
        this.spellsList = new ArrayList<>(spellsList);
    }
    
    //alternate constructor (for enemies)
    public Actor (String name, int level, int atk, int hp, int def, String weapon, List<String> inventory, List<String> spellsList)
    {
        this.name = name;
        this.level = level;
        this.atk = atk;
        this.hp = hp;
        this.def = def;
        weaponEquipped = weapon;
        this.inventory = new ArrayList<>(inventory);
    }
}
