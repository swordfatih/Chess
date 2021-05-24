package test;

import echecs.Case;
import echecs.Echiquier;
import fabriques.Fabrique;
import figures.Roi;
import org.junit.Test;
import org.junit.Assert;

/**
 * @author Antoine <antoine@jiveoff.fr> on 24/05/2021
 * @project BPO-Chess
 */
public class RoiTest {

    @Test
    public void creationRoi() {
        Roi r = new Roi(true, 1, 1);
        Assert.assertTrue(r.estBlanc());
        Assert.assertTrue(r.peutEtreMat());
        Assert.assertFalse(r.peutEtreRoque());
        Assert.assertFalse(r.peutEtrePromu());
        Assert.assertFalse(r.aBougé());
        Assert.assertTrue(r.occupe(new Case(1, 1)));
    }

    @Test
    public void mouvementsRoi() {
        Fabrique f = new Fabrique();

        Roi r = new Roi(true, 1, 1);
        Assert.assertTrue(r.estBlanc());
        Assert.assertTrue(r.peutEtreMat());
        Assert.assertFalse(r.peutEtreRoque());
        Assert.assertFalse(r.peutEtrePromu());

        f.ajouter(r);
        Echiquier e = new Echiquier(f);

        Assert.assertFalse(r.potentiel(new Case(1, 5), e));

        try {
            e.jouer(new Case(1, 1), new Case(1, 5));
            Assert.fail();
        } catch(Exception err) {
            Assert.assertTrue(true);
        }

        try {
            e.jouer(new Case(1, 1), new Case(1, 2));
            Assert.assertTrue(true);
        } catch(Exception err) {
            Assert.fail();
        }

        try {
            e.jouer(new Case(1, 2), new Case(2, 2));
            Assert.assertTrue(r.aBougé());
            Assert.assertTrue(r.occupe(new Case(2, 2)));
        } catch(Exception err) {
            Assert.fail();
        }
    }

}
