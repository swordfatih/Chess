package jeu;

import echecs.IFigure;

public interface Joueur {
	String getCoup() throws Exception;
	IFigure getPromotion(IFigure promu) throws Exception;
}
