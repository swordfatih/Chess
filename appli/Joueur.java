package appli;

import echecs.Echiquier;
import echecs.IFigure;

public interface Joueur {
	String getCoup(Echiquier echiquier);
	IFigure getPromotion(IFigure promu);
	boolean accepteNul();
	String toString();
}
