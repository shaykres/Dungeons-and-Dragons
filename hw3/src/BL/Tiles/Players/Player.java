package BL.Tiles.Players;

import BL.Tiles.*;
import BL.Tiles.Enemies.Enemy;
import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Unit {
    public static final char playerTile = '@';
    protected static final int REQ_EXP = 50;
    protected static final int ATTACK_BONUS = 4;
    protected static final int DEFENSE_BONUS = 1;
    protected static final int HEALTH_BONUS = 10;

    protected int level;
    protected int experience;

    protected Player(String name, int healthCapacity, int attack, int defense) {
        super(playerTile, name, healthCapacity, attack, defense);
        this.level = 1;
        this.experience = 0;
    }

    public abstract void castAbility(List<Enemy> enemies);

    public void onDeath(Enemy e) {
        tile='X';
        messageCallback.send(getName()+" was killed by "+e.getName());
        messageCallback.send("You lost.");
    }

    public abstract void OnGameTick();

    protected void levelUp(){
        experience = experience- REQ_EXP*level;
        level += 1;
        health.setHealthPool(health.getHealthPool() + gainHealth());
        health.setHealthAmount(health.getHealthPool());
        attack += gainAttack();
        defense += gainDefense();

    }

    public void visit(Enemy enemy) {
        messageCallback.send(getName() + " engaged in combat with " + enemy.getName());
        messageCallback.send(describe());
        messageCallback.send(enemy.describe());
        int attack = rollAttack();
        int defense = enemy.rollDefense();
        attacking(attack, enemy, defense);
        if (enemy.isDead()){
            swap(this,enemy);
            enemy.onDeath(this);
        }
    }

    public void attackingWithAbility(int attack, Enemy e, int defense){
        int damage = attack - defense;

        if (damage > 0) {
            messageCallback.send(getName()+" hit "+e.getName()+" for "+damage+" ability damage");
            e.getHealth().setHealthAmount(e.getHealth().getHealthAmount() - damage);
            messageCallback.send(getName() + " dealt " + damage + " damage to " + e.getName());
        }
        if (e.isDead()){
            e.onDeath(this);
        }
    }

    public void accept(Unit u){
        u.visit(this);
    }

    public void visit(Player p) {}
    @Override
    public String toString() {
        if(getHealth().getHealthAmount()>0) return playerTile+""; else return "X";
    }

    protected int gainHealth(){
        return level * HEALTH_BONUS;
    }
    protected int gainAttack(){
        return level * ATTACK_BONUS;
    }
    protected int gainDefense(){
        return level * DEFENSE_BONUS;
    }

    private boolean levelUpRequirement(){
        return (experience >= REQ_EXP * level);
    }

    public int getLevel() {return level; }
    public int getExperience() {return experience;}
    public void gainExperience(int ex) {
        experience += ex;
        if (levelUpRequirement()){
            levelUp();
        }
    }

    public String describe() {
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%d", super.describe(), getLevel(), getExperience(),REQ_EXP*level );
    }
    public List<Enemy> enemiesAtAbilityRange(List<Enemy> enemies, int range){
        List<Enemy> enemiesAbilityRange=new ArrayList<>();
        for(Enemy e:enemies){
            if(getPosition().Range(e.getPosition())<range)
                enemiesAbilityRange.add(e);
        }
        return enemiesAbilityRange;
    }



}