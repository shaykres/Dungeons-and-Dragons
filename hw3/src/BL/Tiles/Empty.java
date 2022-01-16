package BL.Tiles;

import BL.GameManager.Position;

public class Empty extends Tile {
    public static final char EmptyTile = '.';
    public Empty(){
        super(EmptyTile);
    }
    public  Empty(Position p){
        super(EmptyTile);
        this.position=p;
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
        //swap(this, unit);
    }
}
