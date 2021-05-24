package figures;

import echecs.Case;
import echecs.Echiquier;
import echecs.IFigure;

public abstract class Figure implements IFigure {
	private Case pos;
	private boolean blanc;
	private boolean bougé;
	
	public Figure(boolean blanc, int colonne, int ligne) 
	{
		this.blanc = blanc;
		pos = new Case(colonne, ligne);
		bougé = false;
	}
	
	@Override
	public abstract boolean potentiel(Case dest, Echiquier echiquier);
	
	@Override
	public void dessiner(char[][] t) 
	{
		t[pos.getColonne()][pos.getLigne()] = blanc ? Character.toUpperCase(getSymbole()) : Character.toLowerCase(getSymbole());
	}
	
	@Override
	public void déplacer(Case dest, boolean simulation)
	{
		pos = dest;
		
		if(!simulation)
			bougé = true;
	}
	
	@Override
	public boolean occupe(Case c) 
	{
		return pos.equals(c);
	}
	
	@Override 
	public IFigure faireRoque(Case dest, Echiquier echiquier)
	{
		return null;
	}
	
	@Override
	public boolean peutEtreRoque()
	{
		return false;
	}
	
	public abstract char getSymbole();
	
	@Override
	public boolean peutEtrePromu()
	{
		return false;
	}
	
	@Override
	public boolean peutEtreMat()
	{
		return false;
	}
	
	@Override
	public boolean estInsuffisant()
	{
		return false;
	}
	
	@Override
	public boolean estBlanc()
	{
		return blanc;
	}
	
	@Override
	public boolean aBougé()
	{
		return bougé;
	}

	@Override
	public Case getCase()
	{
		return pos;
	}
}
