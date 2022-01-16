package BL.Tiles.Players;

import BL.Tiles.Enemies.Enemy;
import BL.Tiles.Unit;

import java.util.ArrayList;
import java.util.List;

public class Mage extends Player {
    private final String SPECIAL_ABILITY = "Blizzard";
    private Integer manaPool;
    private Integer currentMana;
    private Integer manaCost;
    private Integer spellPower;
    private Integer hitsCount;
    private Integer AbilityRange;
    protected static final int MANA_BONUS = 25;
    protected static final int SPELL_POWER=10;



    public Mage(String name, int healthCapacity, int attack, int defense,int manaPool,int manaCost,int spellPower,int hitsCount,int abilityRange){
        super( name, healthCapacity, attack, defense);
        this.manaPool=manaPool;
        this.manaCost=manaCost;
        this.currentMana=manaPool/4;
        this.spellPower=spellPower;
        this.hitsCount=hitsCount;
        this.AbilityRange=abilityRange;
    }

    public void castAbility(List<Enemy> enemies) {
        if(currentMana<manaCost){
            messageCallback.send((getName()+"  tried to cast "+SPECIAL_ABILITY+" but there was not enough mana: "+currentMana+"/"+manaCost ));
        }
        else{
            messageCallback.send((getName()+" cast "+SPECIAL_ABILITY));
            currentMana=currentMana-manaCost;
            int hits=0;
            List<Enemy> enemiesAbilityRange=enemiesAtAbilityRange(enemies,AbilityRange);
            while(hits<hitsCount&!enemiesAbilityRange.isEmpty()){
                int i=(int)(Math.random()*enemiesAbilityRange.size());
                Enemy e=enemiesAbilityRange.get(i);
                attackingWithAbility(spellPower,e,e.rollDefense());
                if(e.isDead())
                    enemiesAbilityRange.remove(e);
                hits++;
            }
        }
    }

    public void levelUp() {
        super.levelUp();
        messageCallback.send(getName()+ " reached level "+(getLevel()+1)+" +"+level*10+" Health, +"+gainAttack()+" Attack, +"+gainDefense()+" Defense, +"+gainMANA()+" Maximum Mana, +"+gainSpellPower()+" Spell Power");
        manaPool+=gainMANA();
        currentMana=Math.min(currentMana+(manaPool/4),manaPool);
        spellPower+=gainSpellPower();

    }

    @Override
    public void OnGameTick() {
        currentMana=Math.min(manaPool,currentMana+1*level);
    }

    public String describe() {
        return String.format("%s\t\tMana: %s/%s \t\tSpell Power: %d", super.describe(),currentMana,manaPool, spellPower );
    }

    protected int gainMANA(){
        return (level * MANA_BONUS);
    }
    protected int gainSpellPower(){
        return (level * SPELL_POWER);
    }

}
