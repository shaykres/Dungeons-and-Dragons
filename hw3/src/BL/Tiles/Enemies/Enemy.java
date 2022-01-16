package BL.Tiles.Enemies;

import BL.GameManager.Position;
import BL.Tiles.Players.Player;
import BL.Tiles.Unit;

public abstract class Enemy extends Unit {

    protected Integer Experience;

    public Enemy(char tile, String name, int healthCapacity, int attack, int defense,Integer experience){
        super(tile, name, healthCapacity, attack, defense);
        this.Experience=experience;
    }

    public void onDeath(Player player) {
        messageCallback.send(getName() + " is dead. "+player.getName()+" gained "+GetExperience()+" Experience");
        deathCallBack.call();
    }

    @Override
    public void visit(Enemy e) {
        swap(this, e);
    }

    public void accept(Unit u) { // the player attack the enemy.
        u.visit(this);
    }

    public void visit(Player player) { // the enemy attack the player.
        messageCallback.send(getName() + " engaged in combat with " + player.getName());
        messageCallback.send(describe());
        messageCallback.send(player.describe());
        int attack = rollAttack();
        int defense = player.rollDefense();
        this.attacking(attack, player  , defense);
        if (player.isDead()){
            player.onDeath(this);
        }
    }

    public Integer GetExperience(){
        return Experience;
    }
    public Position gameEnemyTick(Player p){
        return this.eTurn(p);
    }

    public abstract Position eTurn(Player p);

    public String describe() {
        return String.format("%s\t\tExperience Value: %s", super.describe(), GetExperience());
    }

}