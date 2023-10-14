package textExcel5;

/* StringCell represents a cell that contains a string, anything
 * the user entered by typing something with quotes around it
 */
public class StringCell extends Cell 
{
	// create a new StringCell. If value isn't quoted, this will
	// throw an exception and no StringCell will be created.
	public StringCell(String value)
	{
		super(value);
		
		if (!value.startsWith("\"") || !value.endsWith("\""))
			throw new IllegalArgumentException("String values need to start and end with quotes. '" + value + "' did not.");
	}
	
	// return a string suitable for display in the grid
	@Override
	public String getDisplayValue() 
	{
		// since we know the constructor validated that this starts and ends with
		// quotes, we can just trim off the first and last characters of whatever
		// the user entered. The original value is stored in the parent class.
		return getOriginalValue().substring(1, getOriginalValue().length() - 1);
	}

}
