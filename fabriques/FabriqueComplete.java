package fabriques;

import echecs.Case;
import echecs.Echiquier;
import figures.Pion;
import figures.Roi;
import figures.Tour;
import figures.Cavalier;
import figures.Dame;
import figures.Figure.Couleur;
import figures.Fou;

public class FabriqueComplete extends Fabrique {
	public FabriqueComplete()
	{
		super();
		
		for(int i = 0; i < Couleur.values().length; ++i)
		{
			int ligne = i == 0 ? 0 : Echiquier.TAILLE - 1;
			Couleur couleur = Couleur.values()[i];
			
			ajouter(new Tour(couleur, new Case(0, ligne)));
			ajouter(new Cavalier(couleur, new Case(1, ligne)));
			ajouter(new Fou(couleur, new Case(2, ligne)));
			ajouter(new Dame(couleur, new Case(3, ligne)));
			ajouter(new Roi(couleur, new Case(4, ligne)));
			ajouter(new Fou(couleur, new Case(5, ligne)));
			ajouter(new Cavalier(couleur, new Case(6, ligne)));
			ajouter(new Tour(couleur, new Case(7, ligne)));
			
			for(int j = 0; j < Echiquier.TAILLE; ++j)
				ajouter(new Pion(couleur, new Case(j, ligne + (i == 0 ? 1 : -1))));
		}
	}
}
