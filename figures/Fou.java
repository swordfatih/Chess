package figures;

import echecs.Case;
import echecs.Echiquier;

public class Fou extends Figure {
	public Fou(Couleur couleur, Case position) 
	{
		super(couleur, position);
	}
	
	@Override
	public boolean potentiel(Case dest, Echiquier echiquier) 
	{	
		Case relatif = getCase().relatif(dest);
		Case delta = Case.abs(relatif);
		
		if (delta.getColonne() != delta.getLigne())
			return false;
		
		int dx = relatif.getColonne() > 0 ? -1 : 1;
		int dy = relatif.getLigne() > 0 ? -1 : 1;
		
		for (int i = 1; i < delta.getColonne(); ++i)
			if (echiquier.occupant(new Case(getCase().getColonne() + i * dx, getCase().getLigne() + i * dy)) != null)
				return false;
			
		return true;
	}
	
	@Override
	public boolean estInsuffisant()
	{
		return true;
	}
	
	@Override
	public char getSymbole()
	{
		return 'f';
	}
}
