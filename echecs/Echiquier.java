package echecs;

public class Echiquier {
	private static final int MIN = 1;
	private static final int MAX = 8;
	
	public Echiquier()
	{
		
	}
	
	public String toString() 
	{
		StringBuilder affichage = new StringBuilder();
		
		// Affichage des lettres de début
		affichage.append("  ");
		for(int i = 0; i < MAX; ++i)
		{
			affichage.append("   ");
			affichage.append(intToChar(i));
		}
		affichage.append("     ");
		affichage.append(System.lineSeparator());
		
		for(int i = 0; i < MAX; ++i)
		{
			// Affichage des lignes
			affichage.append("   ");
			for(int j = 0; j < MAX; ++j)
			{
				affichage.append(" ---");
			}
			affichage.append("    ");
			
			affichage.append(System.lineSeparator());
			
			// Affichage des nombres de début
			affichage.append(" " + (MAX - i) + " ");
			
			// Affichage des colonnes
			for(int j = 0; j < MAX; ++j)
			{
				affichage.append("|   ");
			}
			
			// Affichage des nombres de fin
			affichage.append("| " + (MAX - i) + " ");
			
			affichage.append(System.lineSeparator());
		}
		
		// Affichage de la dernière ligne
		affichage.append("   ");
		for(int j = 0; j < MAX; ++j)
		{
			affichage.append(" ---");
		}
		affichage.append("    ");
		
		affichage.append(System.lineSeparator());

		// Affichage des lettres de fin
		affichage.append("  ");
		for(int i = 0; i < MAX; ++i)
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
