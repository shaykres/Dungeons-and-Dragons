package BL.Tiles;

import BL.GameManager.Position;

public abstract class Tile implements Comparable<Tile> {
    protected char tile;
    protected Position position;

    protected Tile(char tile){
        this.tile = tile;
    }

    public char getTile() {
        return tile;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract void accept(Unit unit);

    @Override
    public int compareTo(Tile tile) {
        return getPosition().compareTo(tile.getPosition());
    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }

    protected void swap(Tile t1, Tile t2){
        Position p = new Position(t1.getPosition().getX(),t1.getPosition().getY());
        t1.setPosition(t2.getPosition());
        t2.setPosition(p);
    }
}
