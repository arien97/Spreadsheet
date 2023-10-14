/**********************************************************
	 * Assignment: Text Excel
	 *
	 * Author: Arien Amin
	 *
	 * Description: This program takes makes a formula cell
	 *
	 * Academic Integrity: I pledge that this program represents my own work. I
	 * received help from no one in designing and debugging
	 * my program.
	 **********************************************************/
package textExcel5;

public class FormulaCell extends Cell
{
	Spreadsheet sheet;
	
	public FormulaCell(String formula, Spreadsheet s)
	{
		super(formula);
		sheet = s;
		
		if (!formula.startsWith("(") || !formula.endsWith(")"))
			throw new IllegalArgumentException("Formula values need to start and end with parenthesis. '" + formula + "' did not.");
		
	}
	
	public String getDisplayValue()
	{
		//will display the formula simplified
		return "" + ExpressionSimplifier.simplify(getOriginalValue(), sheet);
	}
}
