package appli;

import java.util.Scanner;

import echecs.Case;
import echecs.Echiquier;
import echecs.IFabrique;
import echecs.IFigure;
import fabriques.*;
import figures.Cavalier;
import figures.Dame;
import figures.Fou;
import figures.Tour;
import figures.Figure.Couleur;
import joueurs.*;

public class Application {	
	public interface Lecteur <T> {
		T lire(String ligne);
	}
	
	public static void main(String[] args)
	{
		IFabrique fabrique = lireFabrique();
		Joueur[] joueurs = lireJoueurs();
		int tour = lireTour();
		
		Echiquier echiquier = new Echiquier(fabrique);
		String coup = new String();
		
		while(coup != "fin")
		{			
			System.out.println(echiquier);
			
			coup = joueurs[tour].getCoup(echiquier);
			
			if(coup == null)
				continue;
			
			if(coup.equals("a"))
			{
				System.out.println("Le joueur n°" + (Math.abs(tour - 1) + 1) + " a gagné par abandon");
				break;
			}
			else if(coup.equals("n"))
				if(joueurs[Math.abs(tour - 1)].accepteNul())
				{
					System.out.println("Le nul a été déclaré !");
					break;
				}
				else
				{
					System.out.println("Le nul a été refusé ! La partie continue");
					continue;
				}
			
			if(!formatValide(coup))
			{
				System.out.println("Coup invalide, réessayez");
				continue;
			}
			
			System.out.println("Le joueur n°" + (tour + 1) + " (" + joueurs[tour].toString() + ") a joué " + coup);
			
			tour = traiter(echiquier, joueurs, tour, coup);
			
			if(tour == -1)
				break;
		}
	}
	
	/**
	 * 
	 * @param echiquier
	 * @param joueurs
	 * @param tour
	 * @param coup
	 * @return Vrai si le coup termine la partie
	 */
	private static int traiter(Echiquier echiquier, Joueur[] joueurs, int tour, String coup) {
		try {
			Case src = new Case(coup.substring(0, 2));
			Case dest = new Case(coup.substring(2, 4));
			
			echiquier.jouer(src, dest);
			
			IFigure promu = echiquier.promotion();
			if(promu != null)
				echiquier.promouvoir(joueurs[tour].getPromotion(promu));
			
			if(echiquier.mat())
			{
				System.out.println("Echec et mat ! Le joueur n°" + (tour + 1) + " a gagné !");
				return -1;
			}
			else if(echiquier.pat(Couleur.BLANC) || echiquier.pat(Couleur.NOIR))
			{
				System.out.println("Situation de pat !");
				return -1;
			}
			else if(echiquier.echec() != null)
				System.out.println("Le joueur n°" + (Math.abs(tour - 1) + 1) + " est en echec");

			return Math.abs(tour - 1);
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Les coordonnées données sont en dehors de l'échiquier");
		}
		catch(NumberFormatException e) {
			System.out.println("Les coordonnées données ne correspondent pas au format attendu");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return tour;
	}

	private static boolean formatValide(String coup)
	{
		if(coup.length() != 4
				|| !Character.isAlphabetic(coup.charAt(0))
				|| !Character.isDigit(coup.charAt(1))
				|| !Character.isAlphabetic(coup.charAt(2))
				|| !Character.isDigit(coup.charAt(3)))
			return false;
		
		return true;
	}
	
	private static IFabrique lireFabrique()
	{
		IFabrique fabrique = null;
		boolean fabriqueValide = false;
		while(!fabriqueValide)
		{
			fabrique = Application.getInput("Quelle disposition de départ voulez vous ?\n"
					+ "'f' pour une disposition finale\n"
					+ "'c' pour une disposition complète\n"
					+ "'p' pour une disposition personalisée", (ligne) -> {
						if(ligne.startsWith("f"))
							return new FabriqueFin();
						else if(ligne.startsWith("c"))
							return new FabriqueComplete();
						else if(ligne.startsWith("p"))
							return new FabriquePersonnalisée();
						
						return null;
					});
			
			Echiquier echiquierTest = new Echiquier(fabrique);
			
			if(echiquierTest.mat() 
					|| echiquierTest.pat(Couleur.BLANC) 
					|| echiquierTest.pat(Couleur.NOIR) 
					|| echiquierTest.echec() != null)
				System.out.println("La fabrique n'est pas valide !");
			else
				fabriqueValide = true;
		}
		
		return fabrique;
	}
	
	private static Joueur[] lireJoueurs()
	{
		Joueur[] joueurs = new Joueur[2];
		
		for(int i = 0; i < 2; ++i)
		{
			joueurs[i] = Application.getInput("Choisissez votre joueur n°" + (i + 1) + " :\n"
					+ "'h' pour un joueur humain\n"
					+ "'r' pour une IA\n"
					+ "'a' pour un joueur qui joue aléatoirement", (ligne) -> {
						if(ligne.startsWith("h"))
							return new JoueurHumain();
						else if(ligne.startsWith("r"))
							return new JoueurRobot();
						else if(ligne.startsWith("a"))
							return new JoueurAleatoire();
						
						return null;
					});
		}
		
		return joueurs;
	}
	
	private static int lireTour()
	{
		return Application.getInput("Choisissez quel joueur commence\n"
				+ "'1' pour le joueur un\n"
				+ "'2' pour le joueur deux", (ligne) -> {
					if(ligne.startsWith("1") || ligne.startsWith("2"))
						return Integer.parseInt(ligne.substring(0, 1)) - 1;
					
					return null;
				});
	}
	
	public static <T> T getInput(String question, Lecteur<T> lecteur) 
	{
		System.out.println(question);
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("> ");
		String ligne = scanner.nextLine();
		
		T valeur = null;
		
		while(ligne != "fin")
		{			
			valeur = lecteur.lire(ligne);
			
			if(valeur != null)
			{
				return valeur;
			}
			
			System.out.print("#> ");
			ligne = scanner.nextLine();
		}
		
		throw new RuntimeException("La lecture a echoué");
	}
}
