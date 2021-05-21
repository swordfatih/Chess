package fabriques;

import figures.Roi;
import figures.Tour;

public class FabriqueFin extends Fabrique {
	public FabriqueFin()
	{
		super();
		ajouter(new Roi(true, 1, 1));
		ajouter(new Roi(false, 5, 2));
		ajouter(new Tour(true, 2, 5));
	}
}
