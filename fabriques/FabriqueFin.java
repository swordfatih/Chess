package fabriques;

import figures.Roi;
import figures.Tour;

public class FabriqueFin extends Fabrique {
	public FabriqueFin()
	{
		super();
		
		ajouter(new Roi(true, 4, 0));
		ajouter(new Tour(true, 0, 0));
		
		ajouter(new Roi(false, 5, 2));
	}
}
