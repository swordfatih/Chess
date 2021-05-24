package echecs;

/**
 * Représente une case de l'échiquier
 *
 */
public class Case { 
	public static final int LETTRE_A = 97;
	private int colonne, ligne; 
	
	public Case(int colonne, int ligne)
	{
		this.colonne = colonne;
		this.ligne = ligne;
	}
	
	public Case(String s)
	{
		fromString(s);
	}
	
	public int getColonne()
	{
		return colonne;
	}
	
	public int getLigne()
	{
		return ligne;
	}
	
	public boolean valide()
	{
		return colonne >= 0 && ligne >= 0 && colonne < Echiquier.TAILLE && ligne < Echiquier.TAILLE;
	}
	
	public boolean equals(Case autre)
	{
		return autre.colonne == colonne && autre.ligne == ligne;
	}
	
	public Case relatif(Case autre)
	{
		return new Case(colonne - autre.colonne, ligne - autre.ligne);
	}
	
	private void fromString(String s)
	{
		colonne = (int) (s.charAt(0)) - LETTRE_A;
		ligne = Integer.parseInt(s.charAt(1) + "") - 1;
	}
	
	public String toString()
	{
		return colonneToChar(colonne) + "" + (ligne + 1);
	}
	
	public static Case abs(Case c)
	{
		return new Case(Math.abs(c.colonne), Math.abs(c.ligne));
	}
	
	public static char colonneToChar(int colonne) 
	{
		return (char)(LETTRE_A + colonne);
	}
}
