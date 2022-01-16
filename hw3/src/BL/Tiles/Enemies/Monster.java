package BL.Tiles.Enemies;


import BL.GameManager.Position;
import BL.Tiles.Players.Player;

public class Monster extends Enemy {

    protected Integer visionRange;

    public Monster(char tile, String name, int healthCapacity, int attack, int defense, Integer experience,Integer visionRange) {
        super(tile, name, healthCapacity, attack, defense, experience);
        this.visionRange=visionRange;
    }
    @Override
    public Position eTurn(Player p){
        if (p.getPosition().Range(this.position) <= visionRange){
            return chase(p);
        }
        else{
            int r = (int)(Math.random()*4);
            if (r == 0)
                return new Position(this.position.getX() + 1, this.position.getY());
            if (r == 1)
                return new Position(this.position.getX() -1 , this.position.getY());
            if (r == 2)
                return new Position(this.position.getX() , this.position.getY() - 1);
        }
        return new Position(this.position.getX() , this.position.getY() + 1);
    }

    public Position chase(Player p) {
        int dx = getPosition().getX() - p.getPosition().getX();
        int dy = getPosition().getY() - p.getPosition().getY();
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0)
                return new Position(this.position.getX() - 1, this.position.getY());
            return new Position(this.position.getX() + 1, this.position.getY());
        } else {
            if (dy > 0)
                return new Position(this.position.getX(), this.position.getY() - 1);
            return new Position(this.position.getX(), this.position.getY() + 1);
        }
    }

    public String describe() {
        return String.format("%s\t\tVision Range: %s", super.describe(),visionRange);
    }
}