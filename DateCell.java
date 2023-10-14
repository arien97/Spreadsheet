package textExcel5;

import java.text.*;
import java.util.Date;

/* Represents a cell that stores a date. */
public class DateCell extends Cell 
{
	// the date this cell holds
	private Date date;
	
	// an object that helps us parse and format the date
	private SimpleDateFormat format;
	
	// construct a new DateCell. If the input string isn't
	// actually a date, this will throw an exception so the caller
	// knows it was invalid.
	public DateCell(String input) throws ParseException
	{
		super(input);
		
		format = new SimpleDateFormat("mm/dd/yyyy");
		date = format.parse(input);
	}

	// return the date appropriately formatted for display in 
	// the grid.
	@Override
	public String getDisplayValue() 
	{
		return format.format(date);
	}

}
