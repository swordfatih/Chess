package test;

import echecs.Case;
import echecs.Echiquier;
import fabriques.Fabrique;
import figures.Figure;
import figures.Roi;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Antoine <antoine@jiveoff.fr> on 24/05/2021
 * @project BPO-Chess
 */
public class RoiTest {

    @Test
    public void creationRoi() {
        Roi r = new Roi(Figure.Couleur.BLANC, new Case(1, 1));
        assertSame(r.getCouleur(), Figure.Couleur.BLANC);
        assertTrue(r.peutEtreMat());
        assertFalse(r.peutEtreRoque());
        assertFalse(r.peutEtrePromu());
        assertFalse(r.aBougé());
        assertTrue(r.occupe(new Case(1, 1)));
    }

    @Test
    public void mouvementsRoi() {
        Fabrique f = new Fabrique();

        Roi r = new Roi(Figure.Couleur.BLANC, new Case(1, 1));

        f.ajouter(r);
        Echiquier e = new Echiquier(f);

        assertFalse(r.potentiel(new Case(1, 5), e));

        try {
            e.jouer(new Case(1, 1), new Case(1, 5));
            fail();
        } catch(Exception err) {
            assertTrue(true);
        }

        try {
            e.jouer(new Case(1, 1), new Case(1, 2));
            assertTrue(true);
        } catch(Exception err) {
            fail();
        }

        try {
            e.jouer(new Case(1, 2), new Case(2, 2));
            assertTrue(r.aBougé());
            assertTrue(r.occupe(new Case(2, 2)));
        } catch(Exception err) {
            fail();
        }
    }

}
