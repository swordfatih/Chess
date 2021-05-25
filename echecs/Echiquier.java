package echecs;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

import figures.Figure.Couleur;

public class Echiquier {
    public static final int TAILLE = 8; // Nombre de cases d'un côté de l'échiquier
	private static final int INSUFFISANCE_THEORIQUE = 3; // Nombre insufissante de figure pour gagner si ces figures sont tous "insufissants"
	private static final int NB_COUP_POUR_PAT = 50; // La règle des 50 coups
	
	private ArrayList<IFigure> figures; // Les figures de l'échiquier
	private Couleur courante; // La couleur courante
	private int compteurCoupPat; // Compteur pour la règle des 50 coups
	
	/**
	 * Le constructeur de l'échiquier
	 * Prend en paramètre une fabrique et créer les figures de l'échiquier
	 * @param fabrique
	 */
	public Echiquier(IFabrique fabrique)
	{
		figures = new ArrayList<IFigure>();
		courante = Couleur.BLANC;
		compteurCoupPat = 0;
		
		for(int i = 0; i < TAILLE; ++i)
			for(int j = 0; j < TAILLE; ++j)
			{
				IFigure f = fabrique.fabriquer(new Case(i, j));
				if(f != null) 
					figures.add(f);
			}
	}
	
	/**
	 * Tous les attaquants d'une case donnée pour une figure
	 * @param figure
	 * @param colonne
	 * @param ligne
	 * @return
	 */
	private ArrayList<IFigure> attaquants(IFigure figure, Case c)
	{
		ArrayList<IFigure> attaquants = new ArrayList<IFigure>();
		
		for(IFigure f : figures)
			if(figure != f && f.getCouleur() != figure.getCouleur() && f.potentiel(c, this))
				attaquants.add(f);
				
		
		return attaquants;
	}
	
	/**
	 * Vérifier si une case représente une menace pour une pièce donnée
	 * @param figure Une figure qui craint l'échec
	 * @param colonne
	 * @param ligne
	 * @return
	 */
	public boolean menace(IFigure figure, Case c)
	{
		return figure.peutEtreMat() && attaquants(figure, c).size() > 0;
	}
	
	/** 
	 * Récupérer la figure en situation d'échec s'il y en a un pour une couleur donnée
	 * @return
	 */
	public IFigure echec(Couleur couleur)
	{
		for(IFigure f : figures)
			if(f.getCouleur() == couleur && menace(f, f.getCase())) 
				return f;
		
		return null;
	}
	
	/** 
	 * Récupérer la figure en situation d'échec s'il y en a un 
	 * @return
	 */
	public IFigure echec()
	{
		for(IFigure f : figures)
			if(menace(f, f.getCase())) 
				return f;
		
		return null;
	}
	
	/**
	 * Récuperer la figure occupant une case
	 * @param colonne
	 * @param ligne
	 * @return
	 */
	public IFigure occupant(Case c)
	{
		for(IFigure f : figures)
			if(f.occupe(c)) 
				return f;
		
		return null;
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
	public boolean simulation(Case src, Case dest, BooleanSupplier lambda) 
	{
		IFigure figure = occupant(src);
		if(figure == null) 
			return false;
		
		// Simuler la prise
		IFigure occupant = occupant(dest);
		if(occupant != null) 
			figures.remove(occupant);
		
		// Simuler le coup
		figure.déplacer(dest, true);
		
		// Tester
		boolean result = lambda.getAsBoolean();
		
		// Revenir en arrière
		figure.déplacer(src, true);
		if(occupant != null) 
			figures.add(occupant);
		
		return result;
	}
	
	private boolean estJouable(Case src, Case dest) 
	{
		IFigure figure = occupant(src);
		IFigure occupant = occupant(dest);
		
		if(src.equals(dest) // Les cases source et destination sont les mêmes
				|| figure == null // Aucune figure n'est trouvée 
				|| !figure.potentiel(dest, this) // La figure ne sait pas y aller
				|| (occupant != null && (occupant.getCouleur() == figure.getCouleur() // Une figure alliée s'y trouve
					|| occupant.peutEtreMat())) // Une figure imprenable s'y trouve
				|| simulation(src, dest, () -> { 
					return menace(figure, dest); }) // On vérifie si la figure se met en situation d'échec
				|| simulation(src, dest, () -> { 
						return echec(figure.getCouleur()) != null; 
				   })) // On vérifie s'il y a clouage ou échec
		{
			return false;
		}
			
		return true;
	}
	
	/**
	 * Jouer un coup
	 * @param colSrc
	 * @param ligneSrc
	 * @param colDest
	 * @param ligneDest
	 * @throws Exception
	 */
	public void jouer(Case src, Case dest) throws Exception
	{
		// On vérifie si on peut jouer : une exception est levée si on ne peut pas
		if(!estJouable(src, dest))
			throw new Exception("Coup invalide");
		
		if(!src.valide() || !dest.valide())
			throw new IndexOutOfBoundsException();
		
		IFigure figure = occupant(src);
		
		// On vérifie si c'est la bonne couleur qui est bougée
		if(figure.getCouleur() != courante)
			throw new Exception("Ce n'est pas aux " + (courante == Couleur.BLANC ? "noirs" : "blancs") + " de jouer");
		
		// On effectue le roque s'il y a lieu
		IFigure partenaire = figure.faireRoque(dest, this);
		
		if(partenaire != null)
			partenaire.déplacer(new Case(src.getColonne() + (dest.relatif(src).getColonne() > 0 ? 1 : -1), src.getLigne()), false);
			
		// On effectue la prise s'il y a
		IFigure occupant = occupant(dest);
		if(occupant != null) 
			figures.remove(occupant);
		
		// On déplace la figure
		figure.déplacer(dest, false);
		
		// Compteur pour la règle des 50 coups
		compteurCoupPat++;
		
		if(figure.peutEtrePromu() || occupant != null)
			compteurCoupPat = 0;
		
		courante = Couleur.values()[Math.abs(courante.ordinal() - 1)];
	}
	
	/**
	 * Tous les mouvements possibles d'une figure donnée
	 * @param figure
	 * @param grille
	 */
	public ArrayList<Case> mouvementsPossibles(IFigure figure)
	{
		ArrayList<Case> cases = new ArrayList<Case>();
		
		for(int i = 0; i < TAILLE; ++i)
			for(int j = 0; j < TAILLE; ++j)
			{
				Case dest = new Case(i, j);
				if(estJouable(figure.getCase(), dest))
					cases.add(dest);
			}

		return cases;
	}
	
	public boolean pat(Couleur couleur)
	{
		// Règle des 50 coups
		if(compteurCoupPat >= NB_COUP_POUR_PAT)
			return true;
		
		// Insuffisance matériel
		if(figures.size() <= INSUFFISANCE_THEORIQUE)
		{
			boolean insufissance = true;
			
			for(IFigure figure : figures)
				if(figure.estInsuffisant() == false)
					insufissance = false;
			
			if(insufissance) 
				return true;
		}
		
		// Stalemate
		for(IFigure figure : figures)
			if(figure.getCouleur() == couleur && mouvementsPossibles(figure).size() > 0)
				return false;
		
		return true;
	}
	
	public boolean mat() 
	{
		IFigure echec = echec();
			
		// S'il n'y a pas échec ou si le roi peut se sauver
		if(echec == null || mouvementsPossibles(echec).size() > 0)
			return false;
		
		// S'il y a plusieurs attaquants, le mat est inévitable
		ArrayList<IFigure> attaquants = attaquants(echec, echec.getCase());
		if(attaquants.size() > 1) return true;
		
		// Si une pièce attaquante peut être capturé
		for(IFigure figure : figures)
			if(figure.getCouleur() == echec.getCouleur())
				for(IFigure attaquant : attaquants)
					if(estJouable(figure.getCase(), attaquant.getCase()))
						return false;
		
		// Si une interposition est possible
		ArrayList<IFigure> memeCouleur = new ArrayList<IFigure>();
		
		for(IFigure figure : figures)
			if(figure != echec && figure.getCouleur() == echec.getCouleur())
				memeCouleur.add(figure);
		
		for(IFigure figure : memeCouleur)
			for(Case c : mouvementsPossibles(figure))
				if(simulation(figure.getCase(), c, () -> {
					IFigure e = echec();
					return e == null || e.getCouleur() != figure.getCouleur(); 
				}))
				{
					return false;
				}
							
		return true;
	}
	
	public IFigure promotion()
	{
		for(IFigure figure : figures)
			if(figure.peutEtrePromu()) 
				return figure;
		
		return null;
	}
	
	public void promouvoir(IFigure nouvelle)
	{
		IFigure occupant = occupant(nouvelle.getCase());
		
		if(occupant == null && promotion() == occupant)
			return;
		
		figures.set(figures.indexOf(occupant), nouvelle);
	}
	
	public Couleur getCouleurCourante()
	{
		return courante;
	}
	
	public String toString() 
	{
		// Récupérer la grille des figures
		char t[][] = new char[TAILLE][TAILLE];
		
		for(IFigure figure : figures)
			figure.dessiner(t);
		
		StringBuilder affichage = new StringBuilder();
		
		// Affichage des lettres de début
		affichage.append("  ");
		for(int i = 0; i < TAILLE; ++i)
		{
			affichage.append("   ");
			affichage.append(Case.colonneToChar(i));
		}
		affichage.append("     ");
		affichage.append(System.lineSeparator());
		
		for(int i = 0; i < TAILLE; ++i)
		{
			// Affichage des lignes
			affichage.append("   ");
			for(int j = 0; j < TAILLE; ++j)
				affichage.append(" ---");
			affichage.append("    ");
			
			affichage.append(System.lineSeparator());
			
			// Affichage des nombres de début
			affichage.append(" " + (TAILLE - i) + " ");
			
			// Affichage des colonnes
			for(int j = 0; j < TAILLE; ++j)
				affichage.append("| " + t[j][TAILLE - i - 1] + " ");
			
			// Affichage des nombres de fin
			affichage.append("| " + (TAILLE - i) + " ");
			
			affichage.append(System.lineSeparator());
		}
		
		// Affichage de la dernière ligne
		affichage.append("   ");
		for(int j = 0; j < TAILLE; ++j)
			affichage.append(" ---");
		affichage.append("    ");
		
		affichage.append(System.lineSeparator());

		// Affichage des lettres de fin
		affichage.append("  ");
		for(int i = 0; i < TAILLE; ++i)
		{
			affichage.append("   ");
			affichage.append(Case.colonneToChar(i));
		}
		affichage.append("     ");
		
		return affichage.toString();
	}
}
