import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class Actor
{
    ////////////////////////////// INSTANCE VARIABLES //////////////////////////////
    private String name; // the actor's name
    private int level; // the actor's level
    private int xp; // the amount of xp accumulated

    private int atk; // the actor's attack stat
    private int hp; // amount of hit points the actor currently has
    private int maxHp; // the maximum amount of hit points the actor can have
    private int def; // the actor's defense stat

    private String weapon; // the weapon the actor has equipped
    private String playerClass; // the player's class (mercenary/knight/darkPriest/outlander)
    private int gold; // the amount of gold pieces the actor has

    private List<String> inventory; // the actor's possessions
    private List<String> spellsList; // the spells the actor knows

    private boolean deathState; // true -> dead, false -> alive

    ////////////////////////////// CONSTRUCTORS //////////////////////////////
    //Default Constructor
    public Actor()
    {
        name = "";
        level = 1;
        xp = 0;
        atk = 0;
        hp = 100;
        maxHp = 100;
        def = 0;
        weapon = "";
        playerClass = "";
        gold = 0;
        inventory = new ArrayList<>();
        spellsList = new ArrayList<>();
        deathState = false;
    }

    //Full Constructor (used for the player only)
    public Actor(String name, int level, int xp, int atk, int hp, int maxHp, int def, String weapon, String playerClass, int gold, List<String> inventory, List<String> spellsList, boolean deathState)
    {
        this.name = name;
        this.level = level;
        this.xp = xp;
        this.atk = atk;
        this.hp = hp;
        this.maxHp = maxHp;
        this.def = def;
        this.weapon = weapon;
        this.playerClass = playerClass;
        this.gold = gold;
        this.inventory = new ArrayList<>(inventory);
        this.spellsList = new ArrayList<>(spellsList);
        this.deathState = deathState;
    }

    //Alternative Constructor (for enemies)
    public Actor(String name, int level, int atk, int hp, int maxHp, int def, String weapon, List<String> inventory, boolean deathState)
    {
        this.name = name;
        this.level = level;
        this.atk = atk;
        this.hp = hp;
        this.maxHp = maxHp;
        this.def = def;
        this.weapon = weapon;
        this.inventory = new ArrayList<>(inventory);
        this.deathState = deathState;
    }

    ////////////////////////////// GETTERS //////////////////////////////
    public String getName()
    {
        return name;
    }

    public int getLevel()
    {
        return level;
    }

    public int getXP()
    {
        return xp;
    }

    public int getAtk()
    {
        return atk;
    }

    public int getHp()
    {
        return hp;
    }

    public int getMaxHp()
    {
        return maxHp;
    }

    public int getDef()
    {
        return def;
    }

    public String getWeapon()
    {
        return weapon;
    }

    public String getPlayerClass()
    {
        return playerClass;
    }

    public int getGold()
    {
        return gold;
    }
    
    public List getInventory()
    {
        return inventory;
    }

    public boolean getDeathState()
    {
        return deathState;
    }

    public boolean hasSpell(String spellName) // checks to see if the player has a certain spell
    {
        return spellsList.contains(spellName);
    }

    public boolean hasItem(String itemName)
    {
        return inventory.contains(itemName);
    }

    public int inventoryIndex(String itemName)
    { // returns the index of a certain item in the actor's inventory
        return inventory.indexOf(itemName);
    }

    public int inventorySize()
    {
        return inventory.size();
    }

    ////////////////////////////// SETTERS //////////////////////////////
    public void setName(String name)
    {
        this.name = name;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void setAtk(int atk)
    {
        this.atk = atk;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
    }

    public void setMaxHp(int maxHp)
    {
        this.maxHp = maxHp;
    }

    public void setDef(int def)
    {
        this.def = def;
    }

    public void setWeapon(String weapon)
    {
        this.weapon = weapon;
    }

    public void setPlayerClass(String playerClass)
    {
        this.playerClass = playerClass;
    }

    public void setGold(int gold)
    {
        this.gold = gold;
    }

    public void addItem(String itemName) // adds an item to the actor's inventory
    {
        inventory.add(itemName);
    }

    public void subItem(int index) // adds an item to the actor's inventory
    {
        inventory.remove(index);
    }

    public void addSpell(String spell) // adds a spell to the actor's spells list
    {
        spellsList.add(spell);
    }

    public void addHp(int hpAdd) // adds health
    {
        if ((hp + hpAdd) > maxHp) // checks to see if the health added would exceed max hp
        {
            hp = maxHp;
        }
        else
        {
            hp += hpAdd;
        }
    }

    public void subHp(int hpSub)
    {
        if ((hp - hpSub) < 0) // checks to see if the health subtracted would be below 0
        {
            hp = 0;
        }
        else
        {
            hp -= hpSub;
        }
    }

    public void addGold(int goldAdd)
    {
        gold += goldAdd;
    }

    public void subGold(int goldSub)
    {
        gold -= goldSub;
    }

    ////////////////////////////// BRAIN METHODS //////////////////////////////
    public void copyActor(Actor actor) // copies the attributes of another actor
    {
        this.name = actor.getName();
        this.level = actor.getLevel();
        this.atk = actor.getAtk();
        this.hp = actor.getHp();
        this.maxHp = actor.getMaxHp();
        this.def = actor.getDef();
        this.weapon = actor.getWeapon();
        this.inventory = actor.getInventory();
    }
    
    public boolean isDead() // checks to see if the actor is dead
    {
        boolean result = false;
        if (hp <= 0)
        {
            result = true;
            deathState = true;
        }
        return result;
    }

    public int dmgCalc() // calculates the amount of damage done
    {
        int damage;
        int additionalAtk; // weapons increase base atk
        if (weapon.equals("dagger"))
        {
            additionalAtk = 10;
        }
        else if (weapon.equals("sword"))
        {
            additionalAtk = 20;
        }
        else if (weapon.equals("bow"))
        {
            additionalAtk = 4;
        }
        else if (weapon.equals("claws"))
        {
            additionalAtk = 2;
        }
        else if (weapon.equals("cleaver"))
        {
            additionalAtk = 4;
        }
        else
        {
            additionalAtk = 0;
        }
        damage = atk + additionalAtk;

        // arbitrary damage calculations
        damage *= 10;
        damage /= 2;

        // variation in damage
        damage += Game.generator.nextInt(41)-20;
        if (damage <= 0)
        {
            damage = 1;
        }
        return damage;
    }

    public int spellDmgCalc(String spell) // calculates the damage of a spell
    {
        int damage = (int) (atk * level * 0.7);
        if (level == 1)
        {
            damage = atk;
        }
        if (playerClass.equals("darkPriest")) //dark priests get extra damage when casting spells
        {
            damage += 20;
        }
        if (spell.equals("fireball"))
        {
            damage += 10;
            damage *= 10;
            damage /= 2;
        }

        // variation in damage
        damage += Game.generator.nextInt(41)-20;
        if (damage <= 0)
        {
            damage = 1;
        }
        return damage;
    }

    public boolean missChance() //calculates if the actor misses an attack
    {
        boolean result = false;
        int chance = Game.generator.nextInt(11)+1;
        if (chance == 1) // 10% chance to miss attacks
        {
            result = true;
        }
        return result;
    }
    
    public boolean blindedChance() //calculates if the actor misses an attack
    {
        boolean result = false;
        int chance = Game.generator.nextInt(11)+1;
        if (chance == 5) // 10% chance to get blinded
        {
            result = true;
        }
        return result;
    }

    public boolean enemyMissChance(String playerClass, boolean enemyBlinded) // calculates if the enemy misses an attack
    {
        boolean result = false;
        int chance = Game.generator.nextInt(11)+1;
        if (playerClass == "outlander" || enemyBlinded == true) // enemies are more likely to miss attacks against outlanders
        {
            if (chance >= 5)
            {
                result = true;
            }
        }
        else
        {
            if (chance == 1)
            {
                result = true;
            }
        }
        return result;
    }

    public boolean runChance() // calculates whether the player is successful in running away
    {
        boolean result = false;
        int chance = Game.generator.nextInt(11)+1;
        if (chance > 5) // 50% chance
        {
            result = true;
        }
        return result;
    }

    public void levelCalc(int xpGained)
    {
        xp += xpGained;
        level += (xp/100);
        xp = xp % 100;
    }
    ////////////////////////////// TO STRING //////////////////////////////
    public String openInventory() // displays the player's inventory
    {
        String output = "";
        if (inventory.size() > 0)
        {
            output += "\n-----INVENTORY-----\n";
            for (String item : inventory)
            {
                output += "☆ " + item + "\n";
            }
        }
        else
        {
            output += "You don't have anything in your inventory.";
        }
        return output;

    }

    public String openSpells() // displays the list of spells the player knows
    {
        String output = "\n-----SPELLS-----\n";
        for (String spell : spellsList)
        {
            output += "☆ " + spell + "\n";
        }
        return output;
    }

    public String openProfile() // prints the player's information, stats, inventory, and spells list
    {
        String output = "\n-----PROFILE-----\n";
        output += "Name: " + name;
        output += "\nLevel: " + level;
        output += "\nXP: " + xp + "/100";
        output += xpVisual();
        output += "\nClass: " + playerClass.toUpperCase();
        output += "\nEquipped: " + weapon;
        output += "\nGold: " + gold;
        output += "\n-----STATS-----";
        output += "\nHP: " + hp + "/100";
        output += hpVisual();
        output += "\nATK: " + atk;
        output += "\nDEF: " + def;
        output += "\n-----INVENTORY-----\n";
        for (String item : inventory)
        {
            output += "☆ " + item + "\n";
        }
        output += "\n-----SPELLS-----\n";
        for (String spell : spellsList)
        {
            output += "☆ " + spell + "\n";
        }
        return output;
    }

    public String hpVisual() // creates a visual for the actor's hp bar
    {
        String output = "\n[";
        // calculates the percentage the actor's hp is at
        double percent = ((double) hp / maxHp) * 100;
        int count = (int) Math.round(percent / 10); // reduces the size of the hp visual by 10
        for (int i = 0; i < count; i++)
        {
            output += "X";
        }
        for (int j = 0; j < 10 - count; j++)
        {
            output += "-";
        }
        output += "]";
        output += "\n" + hp + "/" + maxHp;
        output += "\n";
        return output;
    }

    public String xpVisual() // creates a visual for the actor's hp bar
    {
        String output = "\n[";
        // calculates the percentage the actor's hp is at
        double percent = ((double) xp / 100) * 100;
        int count = (int) Math.round(percent / 10); // reduces the size of the hp visual by 10
        for (int i = 0; i < count; i++)
        {
            output += "X";
        }
        for (int j = 0; j < 10 - count; j++)
        {
            output += "-";
        }
        output += "]";
        output += "\n" + xp + "/" + 100;
        output += "\n";
        return output;
    }
}
