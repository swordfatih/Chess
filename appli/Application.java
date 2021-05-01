package appli;

import echecs.Echiquier;
import echecs.IFabrique;

public class Application {
	public static void main(String[] args)
	{
		IFabrique fabrique = new FabriqueFin();
		
		Echiquier echiquier = new Echiquier(fabrique);
		System.out.println(echiquier);
	}
}
