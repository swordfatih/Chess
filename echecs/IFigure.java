package echecs;

public interface IFigure {
	IFigure faireRoque(Case dest, Echiquier echiquier);
	boolean potentiel(Case dest, Echiquier echiquier);
	void dessiner(char[][] t);
	void d�placer(Case dest, boolean simulation);
	boolean peutEtreRoque();
	boolean occupe(Case c);
	boolean peutEtrePromu();
	boolean peutEtreMat();
	boolean estInsuffisant();
	boolean estBlanc();
	boolean aBoug�();
	Case getCase();
}
