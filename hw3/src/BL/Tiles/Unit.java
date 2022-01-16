package BL.Tiles;

import BL.GameManager.DeathCallback;
import BL.Tiles.Enemies.Enemy;
import BL.GameManager.MessageCallback;
import BL.GameManager.Resource;
import BL.Tiles.Players.Player;

public abstract class Unit extends Tile {
    public MessageCallback messageCallback;
    public DeathCallback deathCallBack;
    protected String name;
    protected Resource health;
    protected int attack;
    protected int defense;

    public String getName() {
        return name;
    }

    public Resource getHealth() {
        return health;
    }
    public void getHealth(Resource r) { health = r;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    protected Unit(char tile, String name, int healthCapacity, int attack, int defense) {
        super(tile);
        this.name = name;
        this.health = new Resource(healthCapacity, healthCapacity);
        this.attack = attack;
        this.defense = defense;
    }

    public void setDeathCallBack(DeathCallback deathCallBack) {
        this.deathCallBack = deathCallBack;
    }

    public void SetMessageCallBack(MessageCallback messageCallback ) {
        this.messageCallback=messageCallback;
    }

    public int rollAttack(){
        int attack = (int)(Math.random()*(getAttack()+1));
        messageCallback.send(getName() + " rolled " + attack + " attack points");
        return attack;
    }
    public int rollDefense(){
        int defense = (int)(Math.random()*(getDefense()+1));
        messageCallback.send(getName() + " rolled " + defense + " defense points");
        return defense;
    }

    public void attacking(int attack, Unit UD, int defense){
        int damage = attack - defense;
        if (damage > 0) {
            UD.getHealth().setHealthAmount(UD.getHealth().getHealthAmount() - damage);
            messageCallback.send(getName() + " dealt " + damage + " damage to " + UD.getName());
        }
    }

    public Boolean isDead(){
        return getHealth().getHealthAmount()<=0;
    }

    public void interact(Tile tile){
        tile.accept(this);
    }

    public void visit(Empty e){
        swap(this, e);
    }

    public abstract void visit(Player p);
    public abstract void visit(Enemy e);
    public abstract void accept(Unit unit);

    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth(), getAttack(), getDefense());
    }
}
