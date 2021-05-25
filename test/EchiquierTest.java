package test;

import echecs.Echiquier;
import fabriques.FabriqueFin;
import figures.Figure;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Antoine <antoine@jiveoff.fr> on 25/05/2021
 * @project BPO-Chess
 */
public class EchiquierTest {

    @Test
    public void creationEchiquier() {
        Echiquier e = new Echiquier(new FabriqueFin());

        assertFalse(e.mat());
        assertFalse(e.pat(Figure.Couleur.BLANC));
        assertFalse(e.pat(Figure.Couleur.NOIR));
    }

}
