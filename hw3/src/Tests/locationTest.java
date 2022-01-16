package Tests;

import BL.GameManager.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class locationTest {

    Position p12 = new Position(1,2);
    Position p22 = new Position(2,2);
    Position p23 = new Position(2,3);
    Position p99 = new Position(9,9);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void swapTest() {
        Assert.assertEquals(1, p12.Range(p22));
        Assert.assertEquals(1, p12.Range(p23));
        Assert.assertEquals(9, p22.Range(p99));
    }
}
