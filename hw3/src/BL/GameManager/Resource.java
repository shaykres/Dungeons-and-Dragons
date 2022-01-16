package BL.GameManager;

public class Resource {
    protected int pool;
    protected int amount;

    public  Resource(int pool, int amount){
        this.pool=pool;
        this.amount=amount;
    }


    public int getHealthPool() {
        return pool;
    }
    public int getHealthAmount() { return amount;}
    public void setHealthAmount(int health){
        amount = health;
        if (amount>pool)
            amount = pool;
        else if(amount<0)
            amount=0;
    }

    public void setHealthPool(int health){pool = health;}

    public String toString(){
        return ""+amount+"/"+pool;
    }
}