package figures;

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

	public int getColonne()
	{
		return colonne;
	}
	
	public int getLigne()
	{
		return ligne;
	}
	
	public void déplacer(int x, int y)
	{
		colonne = x;
		ligne = y;
	}
	
	public boolean estBlanc()
	{
		return blanc;
	}
	
	public boolean craintEchec()
	{
		return false;
	}
	
	public boolean occupe(int colonne, int ligne) 
	{
		return this.colonne == colonne && this.ligne == ligne;
	}
	
	public abstract boolean potentiel(int colonne, int ligne);
	
	public abstract char getSymbole();
	
	@Override
	public void dessiner(char[][] t) 
	{
		t[colonne][ligne] = blanc ? Character.toUpperCase(getSymbole()) : Character.toLowerCase(getSymbole());
	}
}
