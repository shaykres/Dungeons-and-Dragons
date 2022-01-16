package BL.GameManager;

public class Position {
    protected int x;
    protected int y;

    public Position(int x,int y){
        this.x=x;
        this.y=y;
    }
    public int getX(){return x;}
    public int getY(){return y;}



    public int Range(Position p1){
        return (int)(Math.sqrt(Math.pow((p1.x-this.x),2)+Math.pow((p1.y-this.y),2)));
    }

    public void Translate (int shiftRight,int shiftUp){
        x=x+shiftRight;
        y=y+shiftUp;
    }


    public int compareTo(Position p1){
        if(x==p1.x&y==p1.y)
            return 0;
        else if(y<p1.y)
            return -1;
        else if(y>p1.y)
            return 1;
        else if(x<p1.x)
            return -1;
        else return 1;
    }
}