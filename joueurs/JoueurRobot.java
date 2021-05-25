package joueurs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import appli.Joueur;
import echecs.Case;
import echecs.Echiquier;
import echecs.IFigure;
import figures.Dame;
import figures.Figure.Couleur;

public class JoueurRobot implements Joueur {
	private HashMap<String, Integer> scores;
	
	public JoueurRobot()
	{
		scores = new HashMap<String, Integer>();
		
		scores.put("r", 10000);
		scores.put("d", 1000);
		scores.put("t", 525);
		scores.put("c", 350);
		scores.put("p", 100);
	}
	
	@Override
	public boolean accepteNul()
	{
		return false;
	}
	
	@Override
	public String getCoup(Echiquier echiquier)
	{
		String meilleurCoup = new String();
		AtomicInteger meilleurScore = new AtomicInteger(0);
		AtomicBoolean first = new AtomicBoolean(true);
		
		for(int i = 0; i < Echiquier.TAILLE; ++i)
			for(int j = 0; j < Echiquier.TAILLE; ++j)
			{
				IFigure occupant = echiquier.occupant(new Case(i, j));
				
				if(occupant == null || occupant.getCouleur() != echiquier.getCouleurCourante())
					continue;
				
				ArrayList<Case> mouvements = echiquier.mouvementsPossibles(occupant);
					
				if(mouvements.size() > 0)
				{
					Collections.shuffle(mouvements);
					
					for(Case c : mouvements)
					{
						if(echiquier.simulation(occupant.getCase(), c, () -> {
							int score = score(echiquier, echiquier.getCouleurCourante());
							if(first.get() || score > meilleurScore.get())
							{
								meilleurScore.set(score);
								first.set(false);
								return true;
							}
								
							return false;
						})) {
							meilleurCoup = occupant.getCase().toString() + c.toString();
						};
					}
				}
			}

		if(meilleurCoup.isEmpty())
			return "a";
		
		return meilleurCoup;
	}
	
	@Override
	public IFigure getPromotion(IFigure promu)
	{
		return new Dame(promu.getCouleur(), promu.getCase());
	}
	
	private int score(Echiquier echiquier, Couleur couleur)
	{
		int valeur = 0;
		
		for(int i = 0; i < Echiquier.TAILLE; ++i)
			for(int j = 0; j < Echiquier.TAILLE; ++j)
			{
				IFigure occupant = echiquier.occupant(new Case(i, j));
				if(occupant != null && scores.containsKey(occupant.toString().toLowerCase()))
					valeur += occupant.getCouleur() == couleur ? scores.get(occupant.toString()) : -1;
				
				if(echiquier.echec() != null)
					valeur += 1000;
			}
		
		return valeur;
	}
	
	@Override
	public String toString()
	{
		return "Robot";
	}
}
