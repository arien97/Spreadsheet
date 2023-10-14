package textExcel5;

/* Represents a cell that stores a number */
public class NumberCell extends Cell 
{
	// store the number in its native form.
	private double value;
	
	// construct a new NumberCell. If the input can't be parsed, this
	// will throw a NumberFormatException and the caller will know that
	// no NumberCell could be constructed.
	public NumberCell(String input)
	{
		super(input);
		
		value = Double.parseDouble(input);
	}

	// return the number in the correct form for display in the grid.
	@Override
	public String getDisplayValue() 
	{
		return "" + value;
	}

}
