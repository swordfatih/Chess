package figures;

import echecs.Case;
import echecs.Echiquier;
import echecs.IFigure;

public abstract class Figure implements IFigure {
	public enum Couleur { BLANC, NOIR }
	
	private Case position;
	private Couleur couleur;
	private boolean boug�;
	
	public Figure(Couleur couleur, Case position) 
	{
		this.couleur = couleur;
		this.position = position;
		boug� = false;
	}
	
	@Override
	public abstract boolean potentiel(Case dest, Echiquier echiquier);
	
	@Override
	public void dessiner(char[][] t) 
	{
		t[position.getColonne()][position.getLigne()] = couleur == Couleur.BLANC ? Character.toUpperCase(getSymbole()) : Character.toLowerCase(getSymbole());
	}
	
	@Override
	public void d�placer(Case dest, boolean simulation)
	{
		position = dest;
		
		if(!simulation)
			boug� = true;
	}
	
	@Override
	public boolean occupe(Case c) 
	{
		return position.equals(c);
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
	public Couleur getCouleur()
	{
		return couleur;
	}
	
	@Override
	public boolean aBoug�()
	{
		return boug�;
	}

	@Override
	public Case getCase()
	{
		return position;
	}
}
