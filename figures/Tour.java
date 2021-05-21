package figures;

import echecs.Echiquier;

public class Tour extends Figure {
	public Tour(boolean blanc, int colonne, int ligne) 
	{
		super(blanc, colonne, ligne);
	}
	
	@Override
	public boolean potentiel(int colonne, int ligne, Echiquier echiquier) 
	{	
		int dx = Math.abs(getColonne() - colonne);
		int dy = Math.abs(getLigne() - ligne);
		
		if (dx != 0 && dy != 0)
		{
			return false;
		}
			
		int gap = dx != 0 ? dx : dy;
		
		int d = dx != 0 ? (getColonne() - colonne > 0 ? -1 : 1) : (getLigne() - ligne > 0 ? -1 : 1);
		
		for (int i = 1; i < gap; ++i)
		{
			if (echiquier.occupant(dx != 0 ? getColonne() + i * d : getColonne(), dy != 0 ? getLigne() + i * d : getLigne()) != null)
			{
				return false;
			}
		}
			
		return true;
	}
	
	@Override
	public char getSymbole()
	{
		return 't';
	}
}
