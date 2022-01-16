package BL.Tiles.Players;

import BL.Tiles.Enemies.Enemy;
import java.util.List;

public class Rogue extends Player{
    private final String SPECIAL_ABILITY = "Fan of Knives";
    private Integer cost;
    private Integer currentEnergy;
    private Integer MaxEnergy=100;
    private Integer abilityRange=2;
    protected static final int ATTACK_BONUS = 3;


    public Rogue(String name, int healthCapacity, int attack, int defense, int cost){
        super(name, healthCapacity, attack, defense);
        this.cost=cost;
        this.currentEnergy=MaxEnergy;
    }

    @Override
    public void OnGameTick() {
        currentEnergy=Math.min(currentEnergy+10,MaxEnergy);
    }

    @Override
    public void castAbility(List<Enemy> enemies) {
        if(currentEnergy<cost){
            messageCallback.send((getName()+"  tried to cast "+SPECIAL_ABILITY+" but there was not enough energy: "+currentEnergy+"/"+cost ));
        }
        else{
            messageCallback.send((getName()+" cast "+SPECIAL_ABILITY));
            currentEnergy=currentEnergy-cost;

            List<Enemy> enemiesAbilityRange=enemiesAtAbilityRange(enemies,abilityRange);
            for(Enemy e:enemiesAbilityRange)
               attackingWithAbility(attack,e,e.rollDefense());
        }
    }

    public void levelUp() {
        super.levelUp();
        messageCallback.send(getName()+ " reached level "+(getLevel()+1)+" +"+level*10+" Health, +" + gainAttack() +" Attack, +"+gainDefense()+" Defense.");
        currentEnergy=100;
        attack+=level * ATTACK_BONUS;

    }

    public String describe() {
        return String.format("%s\t\tEnergy: %s/%s ", super.describe(),currentEnergy,MaxEnergy );
    }

    protected int gainAttack(){
        return (level * ATTACK_BONUS)+super.gainAttack();
    }
}