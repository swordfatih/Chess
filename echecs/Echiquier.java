package echecs;

import java.util.ArrayList;

public class Echiquier {
	private static final int TAILLE = 8;
	
	ArrayList<IFigure> figures;
	
	public Echiquier(IFabrique fabrique)
	{
		figures = new ArrayList<IFigure>();
		
		for(int i = 0; i < TAILLE; ++i)
		{
			for(int j = 0; j < TAILLE; ++j)
			{
				IFigure f = fabrique.fabriquer(i, j);
				
				if(f != null)
				{
					figures.add(f);
				}
			}
		}
	}
	
	public String toString() 
	{
		// R�cup�rer la grille des figures
		char t[][] = new char[TAILLE][TAILLE];
		
		for(IFigure figure : figures)
		{
			figure.dessiner(t);
		}
		
		StringBuilder affichage = new StringBuilder();
		
		// Affichage des lettres de d�but
		affichage.append("  ");
		for(int i = 0; i < TAILLE; ++i)
		{
			affichage.append("   ");
			affichage.append(intToChar(i));
		}
		affichage.append("     ");
		affichage.append(System.lineSeparator());
		
		for(int i = 0; i < TAILLE; ++i)
		{
			// Affichage des lignes
			affichage.append("   ");
			for(int j = 0; j < TAILLE; ++j)
			{
				affichage.append(" ---");
			}
			affichage.append("    ");
			
			affichage.append(System.lineSeparator());
			
			// Affichage des nombres de d�but
			affichage.append(" " + (TAILLE - i) + " ");
			
			// Affichage des colonnes
			for(int j = 0; j < TAILLE; ++j)
			{
				affichage.append("| " + t[i][j] + " ");
			}
			
			// Affichage des nombres de fin
			affichage.append("| " + (TAILLE - i) + " ");
			
			affichage.append(System.lineSeparator());
		}
		
		// Affichage de la derni�re ligne
		affichage.append("   ");
		for(int j = 0; j < TAILLE; ++j)
		{
			affichage.append(" ---");
		}
		affichage.append("    ");
		
		affichage.append(System.lineSeparator());

		// Affichage des lettres de fin
		affichage.append("  ");
		for(int i = 0; i < TAILLE; ++i)
		{
			affichage.append("   ");
			affichage.append(intToChar(i));
		}
		affichage.append("     ");
		
		return affichage.toString();
	}
	
	private char intToChar(int n) 
	{
		return (char)(97 + n);
	}
}
