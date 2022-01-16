package BL.GameManager;

import BL.Tiles.Tile;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private List<Tile> tiles;

    public GameBoard(){
		//tiles = Arrays.stream(board).flatMap(Arrays::stream).collect(Collectors.toList());
        tiles=new ArrayList<Tile>();
    }

    public void add(Tile t){
        tiles.add(t);
    }

    public Tile GetTile(int x,int y){
        for(Tile t:tiles){
            if(t.getPosition().x==x&t.getPosition().y==y)
                return t;
        }
        return null;
    }

    public void Remove(Tile toremove){
        tiles.remove(toremove);
    }

   public String PrintBoard(){

       sortTiles();
        String board="";
        for(Tile t:tiles){
            //board+=t.toString();
           if (t.getPosition().x==0){
               board+="\n";
            }
            board+=t.toString();
        }
       //System.out.println("board");
        return board;
   }

   private void sortTiles(){
       tiles.sort(Tile::compareTo);
   }
	

}
