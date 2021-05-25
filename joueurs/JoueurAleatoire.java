package joueurs;

import java.util.ArrayList;
import java.util.Random;

import appli.Joueur;
import echecs.Case;
import echecs.Echiquier;
import echecs.IFigure;
import figures.Cavalier;
import figures.Dame;
import figures.Fou;
import figures.Tour;

public class JoueurAleatoire implements Joueur {
	private static Random random = new Random();
	
	@Override
	public boolean accepteNul()
	{
		return random.nextInt(2) == 0;
	}
	
	@Override
	public String getCoup(Echiquier echiquier)
	{
		for(int i = 0; i < Echiquier.TAILLE; ++i)
			for(int j = 0; j < Echiquier.TAILLE; ++j)
			{
				IFigure occupant = echiquier.occupant(new Case(i, j));
				if(occupant != null )
				{
					ArrayList<Case> mouvements = echiquier.mouvementsPossibles(occupant);
					
					if(occupant.getCouleur() == echiquier.getCouleurCourante() && mouvements.size() > 0)
					{
						String coup = occupant.getCase().toString() + mouvements.get(random.nextInt(mouvements.size())).toString();
						System.out.println("Aléatoire joue : " + coup);
						return coup;
					}
				}
			}
		
		return "a";
	}
	
	@Override
	public IFigure getPromotion(IFigure promu) 
	{
		switch(random.nextInt(4))
		{
		case 0:
			return new Dame(promu.getCouleur(), promu.getCase());
		case 1:
			return new Tour(promu.getCouleur(), promu.getCase());
		case 2:
			return new Fou(promu.getCouleur(), promu.getCase());
		default:
			return new Cavalier(promu.getCouleur(), promu.getCase());
		}
	}
	
	@Override
	public String toString()
	{
		return "Aleatoire";
	}
}
