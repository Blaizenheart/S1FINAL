import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class Actor
{
    ////////////////////////////// INSTANCE VARIABLES //////////////////////////////
    private String name;
    private int level;
    private int xp;
    private int atk;
    private int hp;
    private int maxHp;
    private int def;
    private String weapon;
    private String playerClass;
    private int gold;
    private List<String> inventory;
    private List<String> spellsList;
    private boolean deathState;


    ////////////////////////////// CONSTRUCTORS //////////////////////////////
    //Default Constructor
    public Actor()
    {
        name = "";
        level = 0;
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
    
    //Full Constructor (player only)
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
    
    public boolean getDeathState()
    {
        return deathState;
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

    ////////////////////////////// BRAIN METHODS //////////////////////////////
    public void addItem(String item)
    {
        inventory.add(item);
    }
    
    public void addSpell(String spell)
    {
        spellsList.add(spell);
    }
        
    public void addHp(int hpAdd)
    {
        if ((hp + hpAdd) > maxHp) //HP CAP
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
        this.hp = hp - hpSub;
    }
    
    public void addGold(int goldAdd)
    {
        this.gold += goldAdd;
    }
    
    public void subGold(int goldSub)
    {
        if ((gold - goldSub) <= 0)
        {
            gold = 0;
        }
        else
        {
            gold -= goldSub;
        }
    }
    
    public boolean hasSpell(String spellName)
    {
        return spellsList.contains(spellName);
    }
    
    public boolean isDead()
    {
        boolean result = false;
        if (hp <= 0)
        {
            result = true;
            deathState = true;
        }
        return result;
    }
    
    public int dmgCalc()
    {
        int damage;
        int additionalAtk; //Weapons increase atk
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
        else
        {
            additionalAtk = 0;
        }
        damage = atk + additionalAtk;
        damage *= 10;
        damage /= 2;
        //Variation
        damage += Game.generator.nextInt(41)-20;
        if (damage <= 0)
        {
            damage = 1;
        }
        return damage;
    }
    
    public int spellDmgCalc(String spell)
    {
        int damage = atk;
        if (spell.equals("fireball"))
        {
            damage += 10;
            damage *= 10;
            damage /= 2;
        }
        damage += Game.generator.nextInt(41)-20;
        if (damage <= 0)
        {
            damage = 1;
        }
        return damage;
    }
    
    public boolean missChance()
    {
        boolean result = false;
        int chance = Game.generator.nextInt(11)+1;
        if (chance == 1)
        {
            result = true;
        }
        return result;
    }
    
    public boolean enemyMissChance(String playerClass)
    {
        boolean result = false;
        int chance = Game.generator.nextInt(11)+1;
        if (playerClass == "outlander")
        {
            if (chance == 5)
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
    
    public boolean runChance()
    {
        boolean result = false;
        int chance = Game.generator.nextInt(11)+1;
        if (chance > 5)
        {
            result = true;
        }
        return result;
    }
    
    ////////////////////////////// TO STRING //////////////////////////////

    public String openInventory()
    {
        String output = "\n-----INVENTORY-----\n";
        for (String item : inventory)
        {
            output += "☆ " + item + "\n";
        }
        return output;
    }

    public String openSpells()
    {
        String output = "\n-----SPELLS-----\n";
        for (String spell : spellsList)
        {
            output += "☆ " + spell + "\n";
        }
        return output;
    }

    public String openProfile()
    {
        String output = "\n-----PROFILE-----\n";
        output += "Name: " + name;
        output += "\nLevel : " + level;
        output += "\nClass: " + playerClass.toUpperCase();
        output += "\nEquipped: " + weapon;
        output += "\nGold: " + gold;
        output += "\n-----STATS-----";
        output += "\nHP: " + hp;
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
    
    public String hpVisual() 
    {
        String output = "\n[";
        double percent = ((double) hp / maxHp) * 100;
        int count = (int) Math.round(percent / 10); // 1-10
        for (int i = 0; i < count; i++) {
            output += "X";
        }
        for (int j = 0; j < 10 - count; j++) {
            output += "-";
        }
        output += "]";
        output += "\n" + hp + "/" + maxHp;
        output += "\n";
        return output;
    }
}
