package BL.Tiles;

import BL.GameManager.Position;

public class Wall extends Tile {
    public static final char WallTile = '#';
    public Wall(){
        super(WallTile);
    }
    public  Wall(Position p){
        super(WallTile);
        this.position=p;
    }

    @Override
    public void accept(Unit unit) {
    }


}