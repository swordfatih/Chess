package echecs;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public class Echiquier {
	private static final int TAILLE = 8;
	private static final int INSUFFISANCE_MATERIEL = 3;
	
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
	
	public boolean pat(boolean blanc)
	{
		boolean[][] grille = new boolean[TAILLE][TAILLE];
	
		// Stalemate
		for(IFigure figure : figures)
		{
			if(figure.estBlanc() == blanc)
			{
				possibles(figure, grille);
				
				boolean peutJouer = false;
				for(int i = 0; i < grille.length; ++i)
				{
					for(int j = 0; j < grille[i].length; ++j)
					{
						if(grille[i][j] == true)
						{
							peutJouer = true;
						}
					}
				}
				
				if(peutJouer == true)
				{
					return false;
				}
			}
		}
		
		// Insuffisance matériel
		if(figures.size() <= INSUFFISANCE_MATERIEL)
		{
			for(IFigure figure : figures)
			{
				if(figure.insuffisant() == false)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean mat() 
	{
		IFigure echec = echec();

		if(echec != null)
		{
			// Le roi ne peut pas se sauver
			boolean[][] grille = new boolean[TAILLE][TAILLE];
			possibles(echec, grille);
			
			for(int i = 0; i < grille.length; ++i)
			{
				for(int j = 0; j < grille[i].length; ++j)
				{
					if(grille[i][j] == true)
					{
						return false;
					}
				}
			}
			
			ArrayList<IFigure> attaquants = attaquants(echec, echec.getColonne(), echec.getLigne());
			
			// Aucune pièce attaquante ne peut être capturé
			if(attaquants.size() == 1)
			{
				for(IFigure figure : figures)
				{
					if(figure.estBlanc() == echec.estBlanc())
					{
						for(IFigure attaquant : attaquants)
						{
							try
							{
								if(peutJouer(figure.getColonne(), figure.getLigne(), attaquant.getColonne(), attaquant.getLigne()) != null)
								{
									return false;
								}
							} catch(Exception e) {}
						}
					}
				}
				
				// Aucune interposition n'est possible
				for(IFigure figure : figures)
				{
					if(figure.estBlanc() == echec.estBlanc())
					{
						for(IFigure attaquant : attaquants)
						{
							possibles(figure, grille);
							
							for(int i = 0; i < grille.length; ++i)
							{
								for(int j = 0; j < grille[i].length; ++j)
								{
									if(grille[i][j] == true)
									{
										try
										{
											if(peutJouer(figure.getColonne(), figure.getLigne(), i, j) != null)
											{
												return false;
											}
										} catch(Exception e) {}
									}
								}
							}
						}
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public void jouer(int colSrc, int ligneSrc, int colDest, int ligneDest) throws Exception
	{
		// On vérifie si on peut jouer : une exception est levée si on ne peut pas
		IFigure figure = peutJouer(colSrc, ligneSrc, colDest, ligneDest);
		
		// La prise
		IFigure occupant = occupant(colDest, ligneDest);
		
		if(occupant != null)
		{
			figures.remove(occupant);
		}
		
		// Le clouage
		if(simulation(colSrc, ligneSrc, colDest, ligneDest, () -> {
			IFigure echec = echec();
			return echec != null && echec.estBlanc() == figure.estBlanc();
		}))
		{
			throw new Exception("La pièce est clouée");
		}
		
		// On joue
		figure.déplacer(colDest, ligneDest);
	}
	
	/**
	 * Simuler un coup : on joue un coup, on appelle le lambda 
	 * passé en paramètre puis on revient en arrière
	 * 
	 * @param colSrc
	 * @param ligneSrc
	 * @param colDest
	 * @param ligneDest
	 * @param lambda
	 * @return Le résultat de la simulation
	 * @throws Exception
	 */
	private boolean simulation(int colSrc, int ligneSrc, int colDest, int ligneDest, BooleanSupplier lambda) throws Exception
	{
		if(!coordsValide(colSrc, ligneSrc) || !coordsValide(colDest, ligneDest))
		{
			throw new Exception("Les coordonnées ne sont pas valides");
		}
		
		IFigure figure = occupant(colSrc, ligneSrc);
		
		figure.déplacer(colDest, ligneDest);
		
		boolean result = lambda.getAsBoolean();
		
		figure.déplacer(colSrc, ligneSrc);
		
		return result;
	}

	private IFigure peutJouer(int colSrc, int ligneSrc, int colDest, int ligneDest) throws Exception
	{
		if(!coordsValide(colSrc, ligneSrc) || !coordsValide(colDest, ligneDest))
		{
			throw new Exception("Les coordonnées ne sont pas valides");
		}
		
		IFigure figure = occupant(colSrc, ligneSrc);
		
		if(figure == null)
		{
			throw new Exception("La case donnée est vide");
		}
		
		if(!figure.potentiel(colDest, ligneDest, this))
		{
			throw new Exception("La figure ne peut pas atteindre la case donnée");
		}
		
		IFigure occupant = occupant(colDest, ligneDest);
		
		if(occupant != null)
		{
			if(occupant.estBlanc() == figure.estBlanc())
			{
				throw new Exception("La case est occupée par un alliée");
			}
		
			if(occupant.peutEtreMat())
			{
				throw new Exception("La case est occupée par une figure qui ne peut pas être capturée");
			}
		}
		
		if(simulation(colSrc, ligneSrc, colDest, ligneDest, () -> {
			return figure.peutEtreMat() && menace(figure, colDest, ligneDest);
		}))
		{
			throw new Exception("La case est menacée");
		};

		return figure;
	}
	
	private void possibles(IFigure figure, boolean[][] grille)
	{
		for(int i = 0; i < grille.length; ++i)
		{
			for(int j = 0; j < grille[i].length; ++j)
			{
				try
				{
					grille[i][j] = peutJouer(figure.getColonne(), figure.getLigne(), i, j) != null;
				}
				catch(Exception e)
				{
					grille[i][j] = false;
				}
			}
		}
	}
	
	private IFigure echec()
	{
		for(IFigure f : figures)
		{
			if(f.peutEtreMat() && menace(f, f.getColonne(), f.getLigne()))
			{
				return f;
			}
		}
		
		return null;
	}
	
	private boolean menace(IFigure figure, int colonne, int ligne)
	{
		for(IFigure f : figures)
		{
			if(figure != f && f.estBlanc() != figure.estBlanc() && f.potentiel(colonne, ligne, this))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private ArrayList<IFigure> attaquants(IFigure figure, int colonne, int ligne)
	{
		ArrayList<IFigure> attaquants = new ArrayList<IFigure>();
		
		for(IFigure f : figures)
		{
			if(figure != f && f.estBlanc() != figure.estBlanc() && f.potentiel(colonne, ligne, this))
			{
				attaquants.add(f);
			}
		}
		
		return attaquants;
	}
	
	public IFigure occupant(int colonne, int ligne)
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
