package joueurs;

import java.util.Scanner;

import echecs.Case;
import echecs.IFigure;
import figures.Tour;
import jeu.Joueur;

public class JoueurHumain implements Joueur {
	@Override
	public String getCoup() throws Exception
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("> ");
		String ligne = scanner.nextLine();
		
		while(ligne != "fin")
		{
			try
			{
				Case src = new Case(ligne.substring(0, 2));
				Case dest = new Case(ligne.substring(2, 4));
				
				if(src.valide() && dest.valide())
				{
					return ligne.substring(0, 4);
				}
			}
			catch(Exception e) {}
			
			System.out.print("#> ");
			ligne = scanner.nextLine();
		}
		
		throw new Exception("La lecture du coup a échoué");
	}
	
	@Override
	public IFigure getPromotion(IFigure promu) throws Exception
	{
		System.out.println("Choisissez la figure choisit pour la promotion");
		System.out.println("'dame' ou 'd' pour une dame");
		System.out.println("'tour' ou 't' pour une tour");
		System.out.println("'fou' ou 'f' pour un fou");
		System.out.println("'cavalier' ou 'c' pour un cavalier");
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("> ");
		String ligne = scanner.nextLine();
		
		while(ligne != "fin")
		{
			if(ligne.startsWith("d"))
			{
				System.out.println("Cette fonctionnalité n'est pas encore implémentée");
				//return new Tour();
			}
			else if(ligne.startsWith("t"))
			{
				return new Tour(promu.estBlanc(), promu.getCase().getColonne(), promu.getCase().getLigne());
			}
			if(ligne.startsWith("f"))
			{
				System.out.println("Cette fonctionnalité n'est pas encore implémentée");
				//return new Tour();
			}
			else if(ligne.startsWith("c"))
			{
				System.out.println("Cette fonctionnalité n'est pas encore implémentée");
				//return new Tour();
			}
			
			System.out.print("#> ");
			ligne = scanner.nextLine();
		}
		
		throw new Exception("La promotion a échoué");
	}
}
