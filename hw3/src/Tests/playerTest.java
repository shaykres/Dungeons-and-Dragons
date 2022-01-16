package Tests;

import BL.GameManager.MessageCallback;
import BL.GameManager.Position;
import BL.Tiles.Players.Mage;
import BL.Tiles.Players.Player;
import BL.Tiles.Players.Rogue;
import BL.Tiles.Players.Warrior;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class playerTest{
    Player wr;
    Player mg;
    Player rg;
    @Before
    public void setUp() throws Exception {
        wr = new Warrior("shay", 100, 100, 100, 100);
        mg = new Mage("guy", 100, 100, 100, 100, 100, 100, 100, 100);
        rg = new Rogue("gal gadot", 100, 100, 100, 100);


        wr.SetMessageCallBack((msg)->System.out.println(msg));
        mg.SetMessageCallBack((msg)->System.out.println(msg));
        rg.SetMessageCallBack((msg)->System.out.println(msg));

    }

    @Test
    public void LevelUpWarrior() {
        wr.gainExperience(50);
        Assert.assertEquals(2, wr.getLevel());
        Assert.assertEquals(115,wr.getHealth().getHealthPool());
        Assert.assertEquals(106, wr.getAttack());

    }

    @Test
    public void LevelUpMage() {
        mg.gainExperience(50);
        Assert.assertEquals(2, mg.getLevel());
        Assert.assertEquals(110, mg.getHealth().getHealthPool());
        Assert.assertEquals(104, mg.getAttack());
    }

    @Test
    public void LevelUpRogue() {
        rg.gainExperience(50);
        Assert.assertEquals(2, rg.getLevel());
        Assert.assertEquals(110, rg.getHealth().getHealthPool());
        Assert.assertEquals(107, rg.getAttack());
    }
}
