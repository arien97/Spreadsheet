/**********************************************************
	 * Assignment: Text Excel
	 *
	 * Author: Arien Amin
	 *
	 * Description: This program takes strings, ints, and dates and stores it in
	 * a spreadsheet
	 *
	 * Academic Integrity: I pledge that this program represents my own work. I
	 * received help from no one in designing and debugging
	 * my program.
	 **********************************************************/
package textExcel5;

import java.util.Arrays;

/* Spreadsheet stores a 2d array of Cell objects and is able to 
 * modify the array and display it by printing to System.out.
 */
public class Spreadsheet
{
	private final static int COLS = 7;
	private final static int ROWS = 10;
	private final static int CELL_WIDTH = 12;

	// store the cells in a 2D array
	private Cell[][] data;

	// construct a new spreadsheet. each cell in 'data' will be
	// null initially, representing an empty cell.
	public Spreadsheet()
	{
		data = new Cell[ROWS][COLS];
	}

	// print the spreadsheet to system.out
	public void print()
	{
		printHorizontalLine();
		printColumnHeaders();
		printHorizontalLine();

		for (int r = 0; r < ROWS; r++)
		{
			printRow(r);
			printHorizontalLine();
		}
	}

	// check to see if 'ref' is a valid cell reference, like A3 or G10. if
	// ref can't be parsed as a column and row, or if it is not in the
	// appropriate range for this spreadsheet, return false.
	public boolean isCellReference(String ref)
	{
		if (ref == null || ref.length() < 2 || ref.length() > 3)
			return false;

		if (ref.charAt(0) < 'A' || ref.charAt(0) > 'A' + COLS)
			return false;

		int row = Integer.parseInt(ref.substring(1));
		if (row < 1 || row > ROWS)
			return false;

		return true;
	}

	// display the value of a single cell, represented by ref, by printing
	// it to system.out.
	public void displayCell(String ref)
	{
		Cell c = data[getRow(ref)][getCol(ref)];
		String value = (c == null) ? "<empty>" : c.getOriginalValue(); // "ternary operator" - bing it :)

		System.out.println(ref + " = " + value);
	}

	// store a cell at the specified location in the grid, replacing whatever
	// might be there already.
	public void setCell(String ref, Cell cell)
	{
		data[getRow(ref)][getCol(ref)] = cell;
	}

	// given a string that is supposed to be a reference to a cell, parse the
	// row index from it (i.e. F4 will return 3 because 3 is the index of the
	// 4th row in 'data').
	private int getRow(String ref)
	{
		if (!isCellReference(ref))
			throw new IllegalArgumentException(ref + " is not a valid cell reference");

		return Integer.parseInt(ref.substring(1)) - 1;
	}

	// given a string that is supposed to be a reference to a column, parse
	// the column index from it (e.g. C7 will return 2, since C is the 3rd column
	// and its zero-based index in data would therefore be 2).
	private int getCol(String ref)
	{
		if (!isCellReference(ref))
			throw new IllegalArgumentException(ref + " is not a valid cell reference");

		return ref.charAt(0) - 'A';
	}

	// print one line of +------------+--- etc.
	private void printHorizontalLine()
	{
		for (int col = 0; col < COLS + 1; col++)
		{
			System.out.print("+");
			for (int ch = 0; ch < CELL_WIDTH; ch++)
				System.out.print("-");
		}
		System.out.println("+");
	}

	// print the column header row
	private void printColumnHeaders()
	{
		System.out.print("|" + center("", CELL_WIDTH)); // blank cell at top left

		for (int col = 0; col < COLS; col++)
		{
			System.out.print("|");
			System.out.print(center((char) (col + 'A') + "", CELL_WIDTH));
		}
		System.out.println("|");
	}

	// print the specified row of cells, including their left and right borders
	private void printRow(int row)
	{
		System.out.print("|" + center(row + 1 + "", CELL_WIDTH)); // the header column

		for (int col = 0; col < COLS; col++)
		{
			String value = (data[row][col] == null) ? "" : data[row][col].getDisplayValue();
			System.out.print("|" + center(value, CELL_WIDTH));
		}

		System.out.println("|");
	}

	// given a string 'text', truncate or pad it to make it fit exactly in
	// 'width' characters
	private String center(String text, int width)
	{
		if (text.length() > width)
			return text.substring(0, width - 1) + ">";

		String centered = "";
		int leftSpaces = (width - text.length()) / 2;
		for (int c = 0; c < leftSpaces; c++)
			centered += " ";
		centered += text;
		for (int c = centered.length(); c < width; c++)
			centered += " ";

		return centered;
	}

	// this is to clear the whole spreadsheet and will make everything null
	public void clear()
	{
		for (int row = 0; row < ROWS; row++)
		{
			for (int col = 0; col < COLS; col++)
			{
				data[row][col] = null;
			}
		}
	}

	// this is is for clearing specific cells
	public void clearRef(String ref)
	{
		data[getRow(ref)][getCol(ref)] = null;
	}

	// this will access the cell and return the display value
	public String getNumericalValue(String ref)
	{
		String value = data[getRow(ref)][getCol(ref)].getDisplayValue();
		return value;
	}

	// pre: String refArea has to be in the form "A1:B1" with no space and
	// two valid cell references on either side
	// post: will get the sum of a range of cells
	public String getSum(String refArea)
	{
		// these split the cell references apart
		String firstRef = refArea.substring(0, refArea.indexOf(":"));
		String endRef = refArea.substring(refArea.indexOf(":") + 1, refArea.length());

		// these split each cell reference into rows and column indexes
		int row = getRow(firstRef);
		int col = getCol(firstRef);
		int rowTwo = getRow(endRef);
		int colTwo = getCol(endRef);

		double sum = 0;
		for (int i = row; i <= rowTwo; i++)
		{
			for (int j = col; j <= colTwo; j++)
			{
				sum += Double.parseDouble(data[i][j].getDisplayValue());
			}
		}
		return sum + "";
	}

	// pre: String refArea has to be in the form "A1:B1" with no space and
	// two valid cell references on either side
	// post: will get the average of a range of cells
	public String getAverage(String refArea)
	{
		double avg = 0;
		double sum = Double.parseDouble(getSum(refArea));

		// these separate each cell reference
		String firstRef = refArea.substring(0, refArea.indexOf(":"));
		String endRef = refArea.substring(refArea.indexOf(":") + 1, refArea.length());

		// these split each cell reference into columns and rows
		int row = getRow(firstRef);
		int col = getCol(firstRef);
		int rowTwo = getRow(endRef);
		int colTwo = getCol(endRef);

		// to find the area of the range of cells
		int cellAmount = ((rowTwo - row) + 1) * ((colTwo - col) + 1);
		if (row == rowTwo)
		{
			cellAmount = colTwo - col + 1; // this is for if the range is
											// within the same row
		} else if (col == colTwo)
		{
			cellAmount = rowTwo - row + 1; // this is for if the range is
											// within the same column
		}

		avg = sum / cellAmount; // this gets the average

		return avg + "";
	}

	public void sort(String command, String firstRef, String secondRef)
	{
		// these are to split up the cell reference so it's easier
		// to get the values and find the range of cells
		int row = getRow(firstRef);
		int col = getCol(firstRef);
		int rowTwo = getRow(secondRef);
		int colTwo = getCol(secondRef);

		// this is to get the amount of cells that need to be ordered
		int size = ((rowTwo - row) + 1) * ((colTwo - col) + 1);

		Cell[] sortCells = new Cell[size];

		int k = 0;
		for (int i = row; i <= rowTwo; i++)			//this is for rows
		{

			for (int j = col; j <= colTwo; j++)		//this is for columns
			{
				sortCells[k] = data[i][j];
				k++;
			}
		}
		
		//the command is useful to know if its sorta or sortd
		order(sortCells, command);

		//this for-loop puts in back into the array in row-major order
		k = 0;
		for (int i = row; i <= rowTwo; i++)
		{

			for (int j = col; j <= colTwo; j++)
			{
				data[i][j] = sortCells[k];
				k++;
			}
		}

	}

	private void order(Cell cells[], String command)
	{
		//ordering using selection sort
		for (int i = 0; i < cells.length - 1; i++)
		{
			//an int for the smaller cell that's being compared
			int min = i;
			for (int j = i + 1; j < cells.length; j++)
			{
				if(command.equals("sorta"))
				{
					//the value as a double so it can be compared
					double cellJ = Double.parseDouble(cells[j].getDisplayValue());
					double cellMin = Double.parseDouble(cells[min].getDisplayValue());
					if (cellJ < cellMin)
					{
						min = j;
					}
				}
				else
					if(command.equals("sortd"))
					{
						//the value as a double so it can be compared
						double cellJ = Double.parseDouble(cells[j].getDisplayValue());
						double cellMin = Double.parseDouble(cells[min].getDisplayValue());
						if (cellJ > cellMin)
						{
							min = j;
						}
					}
			}

			//swapping the places of the cells
			Cell temp = cells[min];
			cells[min] = cells[i];
			cells[i] = temp;		
		}
		
	}

}
