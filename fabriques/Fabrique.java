package fabriques;

import java.util.HashMap;

import echecs.Case;
import echecs.IFabrique;
import echecs.IFigure;

public class Fabrique implements IFabrique {
	private HashMap<String, IFigure> figures;
	
	public Fabrique()
	{
		figures = new HashMap<String, IFigure>();
	}
	
	public IFigure fabriquer(Case c)
	{
		return figures.get(caseToKey(c));
	}
	
	public void ajouter(IFigure figure)
	{
		figures.put(caseToKey(figure.getCase()), figure);
	}
	
	private String caseToKey(Case c)
	{
		return c.getColonne() + "," + c.getLigne();
	}
}
