package fabriques;

import appli.Application;
import echecs.Case;
import echecs.IFigure;
import figures.Cavalier;
import figures.Dame;
import figures.Fou;
import figures.Pion;
import figures.Roi;
import figures.Tour;
import figures.Figure.Couleur;

public class FabriquePersonnalisée extends Fabrique {
	public FabriquePersonnalisée()
	{
		super();
		
		System.out.println("-------- Menu de personnalisation --------");
		
		int nbFigures = Application.getInput("Combien de figures voulez-vous poser sur le plateau ?\n"
				+ "(entre 1 et 32)", (ligne) -> {
					try {
						return Integer.parseInt(ligne);
					}
					catch (Exception e) {
						return null;
					}
				});
		
		for(int i = 0; i < nbFigures; ++i)
		{
			System.out.println("Figure n°" + (i + 1) + System.lineSeparator());

			Couleur couleur = lireCouleur();
			
			Case position = lirePosition();
			
			ajouter(lireFigure(couleur, position));
		}
		
		System.out.println("-------- Fin de la personnalisation --------");
	}
	
	private static Couleur lireCouleur()
	{
		return Application.getInput("De quelle couleur est votre figure ?" + System.lineSeparator()
		+ "'b' pour blanc" + System.lineSeparator()
		+ "'n' pour noir", (ligne) -> {
			if(ligne.startsWith("b"))
				return Couleur.BLANC;
			else if(ligne.startsWith("n"))
				return Couleur.NOIR;
			
			return null;
		});
	}
	
	private static Case lirePosition()
	{
		return Application.getInput("Quelles sont les coordonnées de votre figure ?" + System.lineSeparator()
		+ "exemple: a4", (ligne) -> { 
			if(!Character.isAlphabetic(ligne.charAt(0))
					|| !Character.isDigit(ligne.charAt(1)))
				return null;
			
			Case c = new Case(ligne.substring(0, 2));
			
			if(c.valide())
				return c;
			
			return null;
		});
	}
	
	private static IFigure lireFigure(Couleur couleur, Case position)
	{
		return Application.getInput("Choisissez la figure\n" 
				+ "'r' pour un roi\n"
				+ "'d' pour une dame\n"
				+ "'t' pour un tour\n"
				+ "'f' pour un fou\n"
				+ "'c' pour un cavalier\n"
				+ "'p' pour un pion", (ligne) -> {
					if(ligne.startsWith("d"))
						return new Dame(couleur, position);
					else if(ligne.startsWith("t"))
						return new Tour(couleur, position);
					else if(ligne.startsWith("f"))
						return new Fou(couleur, position);
					else if(ligne.startsWith("c"))
						return new Cavalier(couleur, position);	
					else if(ligne.startsWith("p"))
						return new Pion(couleur, position);	
					else if(ligne.startsWith("r"))
						return new Roi(couleur, position);	
								
					return null;
				});
	}
}
