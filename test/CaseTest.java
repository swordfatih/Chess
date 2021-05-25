package test;

import echecs.Case;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Antoine <antoine@jiveoff.fr> on 25/05/2021
 * @project BPO-Chess
 */
public class CaseTest {

    @Test
    public void creationCase() {
        Case c1 = new Case(1, 5);
        assertEquals(c1.getColonne(), 1);
        assertEquals(c1.getLigne(), 5);
        assertEquals(c1.toString(), "b6");
        assertTrue(c1.valide());

        Case c2 = new Case(17, 37);
        assertFalse(c2.valide());
    }

    @Test
    public void comparerCases() {
        Case c1 = new Case(3,2);
        Case c2 = new Case (5,7);
        Case c3 = new Case(3,2);
        Case c4 = new Case(2, 5);

        assertTrue(c1.equals(c3));
        assertTrue(c2.relatif(c1).equals(c4));
    }

}
