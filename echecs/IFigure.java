package echecs;

public interface IFigure {
	boolean potentiel(int colonne, int ligne);
	boolean occupe(int colonne, int ligne);
	void déplacer(int x, int y);
	void dessiner(char[][] t);
	boolean craintEchec();
	boolean estBlanc();
	int getColonne();
	int getLigne();
}
