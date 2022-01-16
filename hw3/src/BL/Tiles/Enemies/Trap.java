package BL.Tiles.Enemies;

import BL.GameManager.Position;
import BL.Tiles.Players.Player;

public class Trap extends Enemy {

    private Integer visibilityTime;
    private Integer invisibilityTime;
    private  Integer ticksCount;
    private boolean visible;
    private char OriginalChar;

    public Trap(char tile, String name, int healthCapacity, int attack, int defense, Integer experience, Integer visibilityTime,Integer invisibilityTime) {
        super(tile, name, healthCapacity, attack, defense, experience);
        this.visibilityTime=visibilityTime;
        this.invisibilityTime=invisibilityTime;
        ticksCount=0;
        OriginalChar = tile;
        visible=true;
    }

    @Override
    public String toString() {
        if(visible)
            return tile+"";
        return ".";
    }

    @Override
    public Position eTurn(Player p) {
        Boolean visible = ticksCount < visibilityTime;
        if (visible)
            this.tile = '.';
        else
            tile = OriginalChar;
        if (ticksCount == (visibilityTime + invisibilityTime))
            ticksCount = 0;
        else
            ticksCount++;
        if (this.position.Range(p.getPosition())<2)
            visit(p);
        return position;
    }
}