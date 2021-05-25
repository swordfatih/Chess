package fabriques;

import echecs.Case;
import figures.Roi;
import figures.Tour;
import figures.Figure.Couleur;

public class FabriqueFin extends Fabrique {
	public FabriqueFin()
	{
		super();
		
		ajouter(new Roi(Couleur.BLANC, new Case(3, 0)));
		ajouter(new Tour(Couleur.BLANC, new Case(0, 0)));
		
		ajouter(new Roi(Couleur.NOIR, new Case(5, 2)));
	}
}
