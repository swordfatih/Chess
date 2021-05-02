package appli;

import echecs.Echiquier;
import echecs.IFabrique;
import fabriques.FabriqueFin;

public class Application {
	public static void main(String[] args)
	{
		IFabrique fabrique = new FabriqueFin();
		
		Echiquier echiquier = new Echiquier(fabrique);
		System.out.println(echiquier);
		
		echiquier.jouer(1, 1, 2, 2);
		System.out.println(echiquier);
		
		echiquier.jouer(2, 2, 3, 3);
		System.out.println(echiquier);
		
		echiquier.jouer(3, 3, 4, 4);
		System.out.println(echiquier);
	}
}
