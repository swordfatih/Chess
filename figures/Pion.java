package figures;

import echecs.Case;
import echecs.Echiquier;
import echecs.IFigure;

public class Pion extends Figure {
	private final static int RANG_BLANC = 1;
	private final static int RANG_NOIR = Echiquier.TAILLE - 2;
	
	public Pion(Couleur couleur, Case position) 
	{
		super(couleur, position);
	}
	
	@Override
	public boolean potentiel(Case dest, Echiquier echiquier) 
	{	
		Case relatif = getCase().relatif(dest);
		int dy = getCouleur() == Couleur.BLANC ? -1 : 1;
		
		if(relatif.getLigne() == dy)
		{
			IFigure occupant = echiquier.occupant(dest);
			
			if(relatif.getColonne() == 0 && occupant == null) 
				return true;
			else if(Case.abs(relatif).getColonne() == 1 && occupant != null && occupant.getCouleur() != getCouleur()) 
				return true;
		}
		else if(!aBougé() && relatif.getColonne() == 0 
				&& getCase().getLigne() == (getCouleur() == Couleur.BLANC ? RANG_BLANC : RANG_NOIR)
				&& relatif.getLigne() == 2 * dy
				&& echiquier.occupant(new Case(getCase().getColonne(), getCase().getLigne() + dy * -1)) == null) 
			return true;
		
		return false;
	}
	
	@Override
	public boolean peutEtrePromu()
	{
		return getCase().getLigne() == (getCouleur() == Couleur.BLANC ? RANG_NOIR + 1 : RANG_BLANC - 1);
	}
	
	@Override
	public char getSymbole()
	{
		return 'p';
	}
}
