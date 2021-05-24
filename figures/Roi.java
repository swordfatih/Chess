package figures;

import echecs.Case;
import echecs.Echiquier;
import echecs.IFigure;

public class Roi extends Figure {
	public Roi(boolean blanc, int colonne, int ligne) 
	{
		super(blanc, colonne, ligne);
	}
	
	@Override
	public boolean potentiel(Case dest, Echiquier echiquier) 
	{	
		// Mouvement normal
		Case delta = Case.abs(getCase().relatif(dest));
		if (delta.getColonne() <= 1 && delta.getLigne() <= 1) 
			return true;
		
		// Roque
		boolean petit = dest.relatif(getCase()).getColonne() > 0 ? true : false;
		 
		int ligne = getCase().getLigne();
		
		IFigure partenaire = echiquier.occupant(new Case(dest.getColonne() + (petit ? 1 : -2), ligne));
			
		if(Case.abs(getCase().relatif(dest)).getColonne() != 2 // Le roi ne va pas sur une case de roque
				|| getCase().relatif(dest).getLigne() != 0
				|| aBougé() // Le roi a déjà bougé
				|| partenaire == null // Le roi n'a pas de partenaire
				|| !partenaire.peutEtreRoque() // La partenaire a déjà bougé
				|| partenaire.estBlanc() != estBlanc()) // La partenaire n'est pas alliée
		{
			return false;
		}
		
		for(int i = 1; i < 2; ++i)
		{
			Case c = new Case(getCase().getColonne() + i * (petit ? 1 : -1), ligne);
			if(echiquier.menace(partenaire, c) || echiquier.occupant(c) != null)
				return false;
		}	
		
		return true;
	}
	
	@Override
	public IFigure faireRoque(Case dest, Echiquier echiquier)
	{
		boolean petit = dest.relatif(getCase()).getColonne() > 0 ? true : false;
		return echiquier.occupant(new Case(dest.getColonne() + (petit ? 1 : -2), getCase().getLigne()));
	}
	
	@Override
	public boolean peutEtreMat()
	{
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
		return 'r';
	}
}
