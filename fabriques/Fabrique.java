package fabriques;

import java.util.HashMap;

import echecs.IFabrique;
import echecs.IFigure;

public class Fabrique implements IFabrique {
	private HashMap<String, IFigure> figures;
	
	public Fabrique()
	{
		figures = new HashMap<String, IFigure>();
	}
	
	public IFigure fabriquer(int colonne, int ligne)
	{
		return figures.get(coordsToKey(colonne, ligne));
	}
	
	public void ajouter(IFigure figure)
	{
		figures.put(coordsToKey(figure.getColonne(), figure.getLigne()), figure);
	}
	
	private String coordsToKey(int colonne, int ligne)
	{
		return colonne + "," + ligne;
	}
}
