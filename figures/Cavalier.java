package figures;

import echecs.Case;
import echecs.Echiquier;

public class Cavalier extends Figure {
	public Cavalier(Couleur couleur, Case position) 
	{
		super(couleur, position);
	}
	
	@Override
	public boolean potentiel(Case dest, Echiquier echiquier) 
	{	
		Case delta = Case.abs(getCase().relatif(dest));
		
		if (delta.getColonne() == 2 && delta.getLigne() == 1 
				|| delta.getColonne() == 1 && delta.getLigne() == 2)
			return true;
		
		return false;
	}

	@Override
	public char getSymbole()
	{
		return 'c';
	}
}
