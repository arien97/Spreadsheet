package textExcel5;

/* Cell represents a single cell in a spreadsheet. */
public abstract class Cell 
{
	// store the original value exactly as entered by the user
	private String originalValue;
	
	// construct a new cell, given whatever the user typed.
	public Cell(String value)
	{
		originalValue = value;
	}

	// get the original value, suitable for individual display
	public String getOriginalValue() { return originalValue; }
	
	// get the formatted value, suitable for display within the
	// spreadsheet grid. Each subclass must write its own logic
	// for this.
	public abstract String getDisplayValue();

}
