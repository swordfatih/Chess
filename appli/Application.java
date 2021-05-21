package appli;

import java.util.Scanner;

import echecs.Echiquier;
import echecs.IFabrique;
import fabriques.FabriqueFin;

public class Application {
	public static void main(String[] args)
	{
		IFabrique fabrique = new FabriqueFin();
		
		Echiquier echiquier = new Echiquier(fabrique);
		System.out.println(echiquier);
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("> ");
		String ligne = scanner.nextLine();
		while (!ligne.equals("fin"))
		{
			@SuppressWarnings("resource")
			Scanner decomposition = new Scanner(ligne);
			
			int colSrc = Integer.parseInt(decomposition.next());
			int ligneSrc = Integer.parseInt(decomposition.next());
			int colDest = Integer.parseInt(decomposition.next());
			int ligneDest = Integer.parseInt(decomposition.next());
			
			try
			{
				echiquier.jouer(colSrc, ligneSrc, colDest, ligneDest);
				System.out.println(echiquier);
				
				System.out.println(echiquier.mat() ? "Echec et mat" : "La partie continue");
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
			System.out.print("> ");
			ligne = scanner.nextLine();
		}
		
		scanner.close();
	}
}
