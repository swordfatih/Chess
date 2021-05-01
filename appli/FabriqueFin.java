package appli;

import echecs.IFabrique;
import echecs.IFigure;
import figures.Roi;

public class FabriqueFin implements IFabrique {
	@Override
	public IFigure fabriquer(int colonne, int ligne)
	{
		IFigure f = null;
		
		if(colonne == 1 && ligne == 1)
		{
			return f = new Roi(true, 1, 1);
		}
		
		if(colonne == 5 && ligne == 2)
		{
			return f = new Roi(false, 5, 2);
		}
		
		return f;
	}
}
