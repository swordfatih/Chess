package figures;

import echecs.Case;
import echecs.Echiquier;

public class Tour extends Figure {
	public Tour(boolean blanc, int colonne, int ligne) 
	{
		super(blanc, colonne, ligne);
	}
	
	@Override
	public boolean potentiel(Case dest, Echiquier echiquier) 
	{	
		Case relatif = getCase().relatif(dest);
		Case delta = Case.abs(relatif);
		
		if (delta.getColonne() != 0 && delta.getLigne() != 0)
			return false;

		int gap = delta.getColonne() != 0 ? delta.getColonne() : delta.getLigne();
		
		int d = delta.getColonne() != 0 ? (relatif.getColonne() > 0 ? -1 : 1) : 
			(relatif.getLigne() > 0 ? -1 : 1);
		
		for (int i = 1; i < gap; ++i)
		{
			if (echiquier.occupant(new Case(delta.getColonne() != 0 ? getCase().getColonne() + i * d : 0, 
					delta.getLigne() != 0 ? getCase().getLigne() + i * d : 0)) != null)
				return false;
		}
			
		return true;
	}
	
	@Override
	public boolean peutEtreRoque()
	{
		return !aBougé();
	}
	
	@Override
	public char getSymbole()
	{
		return 't';
	}
}
