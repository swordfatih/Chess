package figures;

import echecs.Echiquier;

public class Roi extends Figure {
	public Roi(boolean blanc, int colonne, int ligne) 
	{
		super(blanc, colonne, ligne);
	}
	
	@Override
	public boolean peutEtreMat()
	{
		return true;
	}
	
	@Override
	public boolean potentiel(int colonne, int ligne, Echiquier echiquier) 
	{	
		return Math.abs(getColonne() - colonne) <= 1 && Math.abs(getLigne() - ligne) <= 1;
	}
	
	@Override
	public boolean insuffisant()
	{
		return true;
	}
	
	@Override
	public char getSymbole()
	{
		return 'r';
	}
}
