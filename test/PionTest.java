package test;

import echecs.Case;
import echecs.Echiquier;
import fabriques.Fabrique;
import figures.Figure;
import figures.Pion;
import org.junit.Test;
import static org.junit.Assert.*;

public class PionTest {

    @Test
    public void creationPion() {
        Pion p1 = new Pion(Figure.Couleur.BLANC, new Case(2, 2));
        assertSame(p1.getCouleur(), Figure.Couleur.BLANC);
        assertFalse(p1.peutEtrePromu());
        assertFalse(p1.peutEtreMat());
        assertFalse(p1.peutEtreRoque());
        assertFalse(p1.aBoug�());
        assertTrue(p1.occupe(new Case(2,2)));
        assertTrue(p1.getCase().equals(new Case(2, 2)));
        assertFalse(p1.estInsuffisant());
        assertEquals(p1.getSymbole(), 'p');

        Pion p2 = new Pion(Figure.Couleur.NOIR, new Case(0, 0));
        assertNotSame(p2.getCouleur(), Figure.Couleur.BLANC);
        assertTrue(p2.peutEtrePromu());
        assertTrue(p2.occupe(new Case(0,0)));
        assertFalse(p2.getCase().equals(new Case(2, 2)));
    }

    @Test
    public void mouvementPion() throws Exception {
        Fabrique f = new Fabrique();

        Pion p =  new Pion(Figure.Couleur.BLANC, new Case(1, 1));
        f.ajouter(p);

        Echiquier e = new Echiquier(f);

        assertFalse(p.potentiel(new Case(1, 0), e));

        try {
            e.jouer(new Case(1,1), new Case(2,7));
            fail();
        } catch(Exception exception) {
            assertTrue(true);
        }

        assertTrue(p.getCase().equals(new Case(1, 1)));

        try {
            e.jouer(new Case(1,1), new Case(1,2));
            assertTrue(true);
            assertTrue(p.aBoug�());
            assertTrue(p.occupe(new Case(1,2)));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            fail();
        }
    }
}