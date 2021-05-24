package appli;

import jeu.Echecs;

public class Application {
	public static void main(String[] args)
	{
		Echecs echecs = new Echecs();
		
		try
		{
			echecs.initialiser();
			echecs.commencer();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		/*Echiquier echiquier = new Echiquier(fabrique);
		System.out.println(echiquier);
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("> ");
		String ligne = scanner.nextLine();
		while (!ligne.equals("fin"))
		{
			@SuppressWarnings("resource")
			Scanner decomposition = new Scanner(ligne);
			
			try
			{
				echiquier.jouer(new Case(decomposition.next()), new Case(decomposition.next()));
				System.out.println(echiquier);
				
				System.out.println(echiquier.mat() ? "Echec et mat" : "La partie continue");
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
			
			System.out.print("> ");
			ligne = scanner.nextLine();
		}
		
		scanner.close();*/
	}
}
