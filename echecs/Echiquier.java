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
	
	public void jouer(int colSrc, int ligneSrc, int colDest, int ligneDest)
	{
		IFigure occupant = peutJouer(colSrc, ligneSrc, colDest, ligneDest);
		if(occupant != null)
		{
			occupant.déplacer(colDest, ligneDest);
		}
	}

	private IFigure peutJouer(int colSrc, int ligneSrc, int colDest, int ligneDest)
	{
		if(!coordsValide(colSrc, ligneSrc) || !coordsValide(colDest, ligneDest))
		{
			throw new RuntimeException("Les coordonnées ne sont pas valides");
		}
		
		IFigure figure = occupant(colSrc, ligneSrc);
		
		if(figure == null)
		{
			throw new RuntimeException("La case donnée est vide");
		}
		
		if(figure.craintEchec() && menace(figure, colDest, ligneDest))
		{
			throw new RuntimeException("La case est menacée");
		}
		
		if(!figure.potentiel(colDest, ligneDest, this))
		{
			throw new RuntimeException("La figure ne peut pas atteindre la case donnée");
		}
		
		IFigure occupant = occupant(colDest, ligneDest);
		
		if(occupant != null && occupant.estBlanc() == figure.estBlanc())
		{
			throw new RuntimeException("La case est occupée par un alliée");
		}
		
		return figure;
	}
	
	private boolean menace(IFigure figure, int colonne, int ligne)
	{
		for(IFigure f : figures)
		{
			if(figure != f && f.potentiel(colonne, ligne, this))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private IFigure occupant(int colonne, int ligne)
	{
		for(IFigure f : figures)
		{
			if(f.occupe(colonne, ligne))
			{
				return f;
			}
		}
		
		return null;
	}
	
	private boolean coordsValide(int colonne, int ligne)
	{
		return colonne >= 0 && ligne >= 0 && colonne < TAILLE && ligne < TAILLE;
	}
	
	public String toString() 
	{
		// Récupérer la grille des figures
		char t[][] = new char[TAILLE][TAILLE];
		
		for(IFigure figure : figures)
		{
			figure.dessiner(t);
		}
		
		StringBuilder affichage = new StringBuilder();
		
		// Affichage des lettres de début
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
			
			// Affichage des nombres de début
			affichage.append(" " + (TAILLE - i) + " ");
			
			// Affichage des colonnes
			for(int j = 0; j < TAILLE; ++j)
			{
				affichage.append("| " + t[j][TAILLE - i - 1] + " ");
			}
			
			// Affichage des nombres de fin
			affichage.append("| " + (TAILLE - i) + " ");
			
			affichage.append(System.lineSeparator());
		}
		
		// Affichage de la dernière ligne
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
