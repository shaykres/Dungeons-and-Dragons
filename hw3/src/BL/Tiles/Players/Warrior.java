package BL.Tiles.Players;

import BL.Tiles.Enemies.Enemy;
import java.util.List;

public class Warrior extends Player {
    private final String SPECIAL_ABILITY = "Avenger’s Shield";
    public int CoolDown;
    public int  remainingCooldown;
    protected static final int abilityRange=3;
    protected static final int HealthBonus=5;
    protected static final int AttackBonus=2;
    protected static final int defenseBonus=1;

    public Warrior(String name, int healthCapacity, int attack, int defense, int coolDown){
        super(name, healthCapacity, attack, defense);
        CoolDown = coolDown;
        remainingCooldown = 0;
    }
    @Override
    public void castAbility(List<Enemy> enemies) {
        if(remainingCooldown>0){
            messageCallback.send((getName()+"  tried to cast "+SPECIAL_ABILITY+" but there is a cool down: "+remainingCooldown));
        }
        else{
            remainingCooldown=CoolDown;
            messageCallback.send((getName()+" cast "+SPECIAL_ABILITY+" healing for "+(10*defense)));
            remainingCooldown = CoolDown+1;
            List<Enemy> nearEnemies = enemiesAtAbilityRange(enemies, abilityRange);
            if (nearEnemies.isEmpty()){}
            else{
                Enemy enemy = nearEnemies.get((int)(Math.random()*nearEnemies.size()));
                attackingWithAbility((int)(0.1*health.getHealthPool()), enemy, enemy.rollDefense());
            }
            health.setHealthAmount(health.getHealthAmount() + 10*defense); // the set action doesn't let the amount growout the pool.
        }
    }

    @Override
    public void OnGameTick() {
        if(remainingCooldown>0)
            remainingCooldown --;
    }

    public void levelUp() {
        super.levelUp();
        messageCallback.send(getName() + " reached level " + (getLevel()+1) + " +" + gainHealth() + " Health, +" + gainAttack() + " Attack, +" + gainDefense() + " Defense");
        remainingCooldown = 0;
        health.setHealthPool(health.getHealthPool() + (level * HealthBonus));
        attack +=( level * AttackBonus);
        defense +=(level * defenseBonus);


    }


    public String describe() {
        return String.format("%s\t\tCooldown: %s/%s ", super.describe(),remainingCooldown,CoolDown );
    }

    protected int gainHealth(){
        return (level * HealthBonus)+super.gainHealth();
    }
    protected int gainAttack(){
        return (level * AttackBonus)+super.gainAttack();
    }
    protected int gainDefense(){
        return (level * defenseBonus)+super.gainDefense();
    }

}