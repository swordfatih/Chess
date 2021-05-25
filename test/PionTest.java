package test;

import echecs.Case;
import echecs.Echiquier;
import fabriques.Fabrique;
import figures.Pion;
import org.junit.Test;
import static org.junit.Assert.*;

public class PionTest {

    @Test
    public void creationPion() {
        Pion p1 = new Pion(true, 2, 2);
        assertTrue(p1.estBlanc());
        assertFalse(p1.peutEtrePromu());
        assertFalse(p1.peutEtreMat());
        assertFalse(p1.peutEtreRoque());
        assertFalse(p1.aBougé());
        assertTrue(p1.occupe(new Case(2,2)));
        assertTrue(p1.getCase().equals(new Case(2, 2)));
        assertFalse(p1.estInsuffisant());
        assertEquals(p1.getSymbole(), 'p');

        Pion p2 = new Pion(false, 0,0);
        assertFalse(p2.estBlanc());
        assertTrue(p2.peutEtrePromu());
        assertTrue(p2.occupe(new Case(0,0)));
        assertFalse(p2.getCase().equals(new Case(2, 2)));
    }

    @Test
    public void mouvementPion() throws Exception {
        Fabrique f = new Fabrique();

        Pion p = new Pion(true, 1,1);
        f.ajouter(p);

        Echiquier e = new Echiquier(f);

        assertTrue(p.potentiel(new Case(1, 0), e));

        try {
            e.jouer(new Case(1,1), new Case(2,7));
            fail();
        } catch(Exception exception) {
            assertTrue(true);
        }

        assertTrue(p.getCase().equals(new Case(1, 1)));

        try {
            e.jouer(new Case(1,1), new Case(1,0));
            assertTrue(true);
            assertTrue(p.aBougé());
            assertTrue(p.occupe(new Case(1,0)));
        } catch (Exception exception) {
            fail();
        }
    }
}