package figures;

import echecs.Case;
import echecs.Echiquier;

public class Pion extends Figure {
	public Pion(boolean blanc, int colonne, int ligne) 
	{
		super(blanc, colonne, ligne);
	}
	
	@Override
	public boolean potentiel(Case dest, Echiquier echiquier) 
	{	
		Case relatif = getCase().relatif(dest);
		int dy = estBlanc() ? 1 : -1;
		
		if(relatif.getLigne() == dy)
		{
			if(relatif.getColonne() == 0) 
				return true;
			else if(relatif.getColonne() == 1 && echiquier.occupant(dest) != null) 
				return true;
		}
		else if(!aBougé() && relatif.getColonne() == 0 
				&& relatif.getLigne() == 2 * dy
				&& echiquier.occupant(new Case(getCase().getColonne(), getCase().getLigne() + dy)) == null) 
			return true;
		
		return false;
	}
	
	@Override
	public boolean peutEtrePromu()
	{
		return (estBlanc() && getCase().getColonne() == Echiquier.TAILLE - 1)
				|| (!estBlanc() && getCase().getColonne() == 0);
	}
	
	@Override
	public char getSymbole()
	{
		return 'p';
	}
}
