package joueurs;

import java.util.Scanner;

import appli.Application;
import appli.Joueur;
import echecs.Echiquier;
import echecs.IFigure;
import figures.Cavalier;
import figures.Dame;
import figures.Fou;
import figures.Tour;

public class JoueurHumain implements Joueur {
	@Override
	public String getCoup(Echiquier echiquier) 
	{
		System.out.println("'a' pour abandonner, 'n' pour proposer un nul");

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	
	@Override
	public IFigure getPromotion(IFigure promu) 
	{
		return Application.getInput("Choisissez la figure choisit pour la promotion\n" 
				+ "'d' pour une dame\n"
				+ "'t' pour un tour\n"
				+ "'f' pour un fou\n"
				+ "'c' pour un cavalier", (ligne) -> {
					if(ligne.startsWith("d"))
						return new Dame(promu.getCouleur(), promu.getCase());
					else if(ligne.startsWith("t"))
						return new Tour(promu.getCouleur(), promu.getCase());
					else if(ligne.startsWith("f"))
						return new Fou(promu.getCouleur(), promu.getCase());
					else if(ligne.startsWith("c"))
						return new Cavalier(promu.getCouleur(), promu.getCase());	
								
					return null;
				});
	}
	
	@Override
	public boolean accepteNul()
	{
		return Application.getInput("Acceptez vous la proposition de nul ?\n" 
				+ "'o' pour oui\n"
				+ "'n' pour non", (ligne) -> {
					if(ligne.startsWith("o"))
						return true;
					else if(ligne.startsWith("n"))
						return false;
					
					return null;
				});
	}
	
	@Override
	public String toString()
	{
		return "Humain";
	}
}
