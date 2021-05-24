package jeu;

import java.util.Scanner;

import echecs.Case;
import echecs.Echiquier;
import echecs.IFabrique;
import echecs.IFigure;
import fabriques.FabriqueFin;
import joueurs.*;

public class Echecs {
	private enum NomJoueur { UN, DEUX };
	
	IFabrique fabrique;
	
	private Joueur joueurs[];
	private NomJoueur commence;
	private NomJoueur tour;
	
	private Echiquier echiquier;
	
	public void initialiser() throws Exception
	{
		fabrique = getFabrique();
		
		echiquier = new Echiquier(fabrique);
		
		joueurs = new Joueur[2];
		
		joueurs[NomJoueur.UN.ordinal()] = getJoueur(1);
		joueurs[NomJoueur.DEUX.ordinal()] = getJoueur(2);
		
		commence = getCommence();
		tour = commence;
	}
	
	public void commencer() throws Exception
	{
		System.out.println(echiquier);
		
		while(!echiquier.mat() && !echiquier.pat(tour == commence))
		{
			System.out.println("-------------------------------------------------");
			System.out.println("Tour du joueur " + commence.toString());
			
			String coup = joueurs[commence.ordinal()].getCoup();
			
			Case src = new Case(coup.substring(0, 2));
			Case dest = new Case(coup.substring(2, 4));
			
			try
			{
				echiquier.jouer(src, dest);
			}
			catch(IndexOutOfBoundsException e)
			{
				System.out.println("Les coordonnées données sont en dehors de l'échiquier");
			}
			catch(NumberFormatException e)
			{
				System.out.println("Les coordonnées données ne correspondent pas au format attendu");
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
			System.out.println(echiquier);
			
			IFigure promu = echiquier.promotion();
			if(promu != null)
			{
				promu = joueurs[commence.ordinal()].getPromotion(promu);
			}
			
			tour = NomJoueur.values()[Math.abs(tour.ordinal() - 1)];
		}
	}
	
	private IFabrique getFabrique() throws Exception {
		System.out.println("Choisissez la disposition de départ de votre échiquier :");
		System.out.println("'fin' ou 'f' pour une finale Roi et Tour");
		System.out.println("'normal' ou 'n' pour un début complet");
		System.out.println("'custom' ou 'c' pour un début personnalisé");
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("> ");
		String ligne = scanner.nextLine();
		
		while(ligne != "fin")
		{
			if(ligne.startsWith("f"))
			{
				return new FabriqueFin();
			}
			else if(ligne.startsWith("n"))
			{
				System.out.println("Cette fonctionnalité n'est pas encore implémentée");
				// return new FabriqueFin();
			}
			else if(ligne.startsWith("c"))
			{
				System.out.println("Cette fonctionnalité n'est pas encore implémentée");
				// return new FabriqueFin();
			}
			
			System.out.print("#> ");
			ligne = scanner.nextLine();
		}
		
		throw new Exception("La lecture de la fabrique a échoué");
	}
	
	private Joueur getJoueur(int i) throws Exception {
		System.out.println("Choisissez votre joueur n° " + i + " :");
		System.out.println("'humain' ou 'h' pour un joueur humain");
		System.out.println("'robot' ou 'r' pour une IA");
		System.out.println("'aleatoire' ou 'a' pour un joueur qui joue aléatoirement");
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("> ");
		String ligne = scanner.nextLine();
		
		while(ligne != "fin")
		{
			if(ligne.startsWith("h"))
			{
				return new JoueurHumain();
			}
			else if(ligne.startsWith("r"))
			{
				System.out.println("Cette fonctionnalité n'est pas encore implémentée");
				// return new JoueurRobot();
			}
			else if(ligne.startsWith("a"))
			{
				System.out.println("Cette fonctionnalité n'est pas encore implémentée");
				// return new JoueurAleatoire();
			}
			
			System.out.print("#> ");
			ligne = scanner.nextLine();
		}
		
		throw new Exception("La lecture du joueur a échoué");
	}
	
	private NomJoueur getCommence() throws Exception {
		System.out.println("Choisissez le joueur blanc");
		System.out.println("'1' pour le joueur 1");
		System.out.println("'2' pour le joueur 2");
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("> ");
		String ligne = scanner.nextLine();
		
		while(ligne != "fin")
		{
			if(ligne.startsWith("1"))
			{
				return NomJoueur.UN;
			}
			else if(ligne.startsWith("2"))
			{
				return NomJoueur.DEUX;
			}
			
			System.out.print("#> ");
			ligne = scanner.nextLine();
		}
		
		throw new Exception("La lecture du joueur blanc a échoué");
	}
}
