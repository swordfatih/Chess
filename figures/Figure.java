package figures;

import echecs.Echiquier;
import echecs.IFigure;

public abstract class Figure implements IFigure {
	private int colonne, ligne;
	boolean blanc;
	
	public Figure(boolean blanc, int colonne, int ligne) 
	{
		this.blanc = blanc;
		this.colonne = colonne;
		this.ligne = ligne;
	}
	
	@Override
	public void d�placer(int x, int y)
	{
		colonne = x;
		ligne = y;
	}
	
	@Override
	public boolean occupe(int colonne, int ligne) 
	{
		return this.colonne == colonne && this.ligne == ligne;
	}
	
	@Override
	public abstract boolean potentiel(int colonne, int ligne, Echiquier echiquier);
	
	@Override
	public void dessiner(char[][] t) 
	{
		t[colonne][ligne] = blanc ? Character.toUpperCase(getSymbole()) : Character.toLowerCase(getSymbole());
	}
	
	public abstract char getSymbole();
	
	@Override
	public boolean estBlanc()
	{
		return blanc;
	}
	
	@Override
	public boolean peutEtreMat()
	{
		return false;
	}
	
	@Override
	public int getColonne()
	{
		return colonne;
	}
	
	@Override
	public int getLigne()
	{
		return ligne;
	}
}
