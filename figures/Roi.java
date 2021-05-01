package figures;

public class Roi extends Figure {
	public Roi(boolean blanc, int colonne, int ligne) 
	{
		super(blanc, colonne, ligne);
	}
	
	@Override
	public char getSymbole()
	{
		return 'r';
	}
}
