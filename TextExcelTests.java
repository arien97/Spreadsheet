package textExcel5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import testHelp.*;

public class TextExcelTests
{
	int COLUMN_WIDTH = 13;
	final int ROWS = 11;
	final int COLS = 8;
	final String newline = System.getProperty("line.separator");

	private String emptyGrid = "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n"
			+ "\\|            \\|     A      \\|     B      \\|     C      \\|     D      \\|     E      \\|     F      \\|     G      \\|\r?\n"
			+ "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n"
			+ "\\|     1      \\|            \\|            \\|            \\|            \\|            \\|            \\|            \\|\r?\n"
			+ "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n"
			+ "\\|     2      \\|            \\|            \\|            \\|            \\|            \\|            \\|            \\|\r?\n"
			+ "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n"
			+ "\\|     3      \\|            \\|            \\|            \\|            \\|            \\|            \\|            \\|\r?\n"
			+ "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n"
			+ "\\|     4      \\|            \\|            \\|            \\|            \\|            \\|            \\|            \\|\r?\n"
			+ "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n"
			+ "\\|     5      \\|            \\|            \\|            \\|            \\|            \\|            \\|            \\|\r?\n"
			+ "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n"
			+ "\\|     6      \\|            \\|            \\|            \\|            \\|            \\|            \\|            \\|\r?\n"
			+ "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n"
			+ "\\|     7      \\|            \\|            \\|            \\|            \\|            \\|            \\|            \\|\r?\n"
			+ "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n"
			+ "\\|     8      \\|            \\|            \\|            \\|            \\|            \\|            \\|            \\|\r?\n"
			+ "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n"
			+ "\\|     9      \\|            \\|            \\|            \\|            \\|            \\|            \\|            \\|\r?\n"
			+ "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n"
			+ "\\|     10     \\|            \\|            \\|            \\|            \\|            \\|            \\|            \\|\r?\n"
			+ "\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+------------\\+\r?\n";

	/*
	 * Checkpoint 1 tests
	 */

	@Test
	public void TextExcelShouldPromptGreetingFirst()
	{
		// NOTE: even though not breaking on invalid input is extra credit, it
		// would be helpful if you program doesn't break when the input is
		// empty, in case you accidentally hit enter when running your program. This
		// test will fail unless you check that the input is not empty.
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "\r\nquit");
		verify.that(response).matches("\\AWelcome to Text Excel!");
	}

	@Test
	public void TextExcelShouldPromptForACommand()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "\r\nquit");
		verify.that(response).matches("Enter a command: ");
	}

	@Test
	public void TextExcelShouldPrintGoodbyeLast()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "quit");
		verify.that(response).matches("Farewell!\\Z");
	}

	@Test
	public void TextExcelShouldPrintGrid()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "print\r\nquit");
		verify.that(response).matches(emptyGrid);
	}

	/*
	 * Checkpoint 2 tests
	 */

	@Test
	public void TextExcelCanInputString()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "A1 = \"Hello!\"\r\nprint\r\nquit");
		verify.that(getCell(response, 'A', 1)).matches("   Hello!   ");
	}

	@Test
	public void TextExcelCanInputDate()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "B8 = 02/06/1976\r\nprint\r\nquit");
		verify.that(getCell(response, 'B', 8)).matches(" 02/06/1976 ");
	}

	@Test
	public void TextExcelCanInputInt()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "D3 = 1729\r\nprint\r\nquit");
		verify.that(getCell(response, 'D', 3)).matches("   1729\\.0   ");
	}

	@Test
	public void TextExcelCanInputDecimal()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "D3 = 3.14159\r\nprint\r\nquit");
		verify.that(getCell(response, 'D', 3)).matches("  3\\.14159   ");
	}

	@Test
	public void TextExcelCanInputLongString()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel",
				"A1 = \"Hello! This string is too long.\"\r\nprint\r\nquit");
		verify.that(getCell(response, 'A', 1)).matches("Hello! This>");
	}

	@Test
	public void TextExcelCanInputLongDecimal()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel",
				"D3 = 3.141592653589793238\r\nprint\r\nquit");
		verify.that(getCell(response, 'D', 3)).matches("3\\.141592653>");
	}

	@Test
	public void TextExcelCanPrintString()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel",
				"A1 = \"Hello! This string is too long.\"\r\nA1\r\nquit");
		verify.that(response).matches("A1 = \"Hello! This string is too long\\.\"");
	}

	@Test
	public void TextExcelCanPrintDate()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "B8 = 02/06/1976\r\nB8\r\nquit");
		verify.that(response).matches("B8 = 02/06/1976");
	}

	@Test
	public void TextExcelCanPrintInt()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "D3 = 1729\r\nD3\r\nquit");
		verify.that(response).matches("D3 = 1729");
	}

	@Test
	public void TextExcelCanPrintDecimal()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "D3 = 3.14159\r\nD3\r\nquit");
		verify.that(response).matches("D3 = 3\\.14159");
	}

	@Test
	public void TextExcelCanChangeType()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "D3 = 3\r\nD3 = \"hi\"\r\nprint\r\nquit");
		verify.that(getCell(response, 'D', 3)).matches("     hi     ");
	}

	@Test
	public void TextExcelCanContainMultipleValues()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "D3 = 3\r\nD4 = \"hi\"\r\nprint\r\nquit");
		verify.that(getCell(response, 'D', 3)).matches("    3\\.0     ");
		verify.that(getCell(response, 'D', 4)).matches("     hi     ");
	}

	@Test
	public void TextExcelCanInputToLastCell()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "G10 = \"hi\"\r\nprint\r\nquit");
		verify.that(getCell(response, 'G', 10)).matches("     hi     ");
	}

	@Test
	public void TextExcelEmptyCellisEmpty()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "G10 = \"hi\"\r\nF5\r\nquit");
		verify.that(response).matches("F5 = <empty>");
	}

	/*
	 * Checkpoint 3 tests
	 */

	@Test
	public void TextExcelCanClearSpreadsheet()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel",
				"A1 = \"one\"\r\nB3 = 3\r\nC5 = 1/1/2014\r\nclear\r\nprint\r\nquit");
		verify.that(getCell(response, 'A', 1)).matches("            ");
		verify.that(getCell(response, 'B', 3)).matches("            ");
		verify.that(getCell(response, 'C', 5)).matches("            ");
	}

	@Test
	public void TextExcelCanClearCell()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel",
				"A1 = \"one\"\r\nB3 = 3\r\nclear A1\r\nprint\r\nquit");
		verify.that(getCell(response, 'A', 1)).matches("            ");
		verify.that(getCell(response, 'B', 3)).matches("    3\\.0     ");
	}

	@Test
	public void TextExcelSingleNumberFormulaPrints()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "A1 = (3)\r\nA1\r\nquit");
		verify.that(response).matches("A1 = \\(3\\)");
	}

	@Test
	public void TextExcelComplexFormulaPrints()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "A1 = (3 * 12 / 45 - 6)\r\nA1\r\nquit");
		verify.that(response).matches("A1 = \\(3 \\* 12 / 45 - 6\\)");
	}

	@Test
	public void TextExcelSingleNumberFormulaEvaluates()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "A1 = (3)\r\nprint\r\nquit");
		verify.that(getCell(response, 'A', 1)).matches("    3\\.0     ");
	}

	@Test
	public void TextExcelAdditionFormulaEvaluates()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "A1 = (3 + 4.0 - 5)\r\nprint\r\nquit");
		verify.that(getCell(response, 'A', 1)).matches("    2\\.0     ");
	}

	@Test
	public void TextExcelMultiplicationFormulaEvaluates()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", "A1 = (3 * 4 / 5.0)\r\nprint\r\nquit");
		verify.that(getCell(response, 'A', 1)).matches("    2\\.4     ");
	}

	@Test
	// Note: this test should work if you evaluate left-to-right or follow order
	// of operations. If you did something else, feel free to remove or ignore the test.
	public void TextExcelComplexFormulaEvaluates()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel",
				"A1 = (4 * 12.5 / 5 + 4.5)\r\nprint\r\nquit");
		verify.that(getCell(response, 'A', 1)).matches("    14\\.5    ");
	}

	@Test
	public void TextExcelHandlesStringsWithCommands()
	{
		String response = ConsoleTester.getOutput("textExcel5.TextExcel",
				"A1 = \"clear\"\r\nA2 = \"quit\"\r\nA3 = \"print\"\r\nprint\r\nquit");
		verify.that(getCell(response, 'A', 1)).matches("   clear    ");
		verify.that(getCell(response, 'A', 2)).matches("    quit    ");
		verify.that(getCell(response, 'A', 3)).matches("   print    ");
	}

	/*
	 * Checkpoint 4 tests
	 */

	@Test
	public void TextExcelColumnSum()
	{
		String[] input =
		{
			"A1 = 76",
			"A2 = 43",
			"A3 = 15",
			"A4 = -8",
			"A5 = 84",
			"A6 = (sum A1:A5)",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 6)).matches(" 210\\.0 ");
	}

	@Test
	public void TextExcelColumnAverage()
	{
		String[] input =
		{
			"A1 = 76",
			"A2 = 43",
			"A3 = 15",
			"A4 = -8",
			"A5 = 84",
			"A6 = (avg A1:A5)",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 6)).matches(" 42\\.0 ");
	}

	@Test
	public void TextExcelRowSum()
	{
		String[] input =
		{
			"A1 = 76",
			"B1 = 43",
			"C1 = 15",
			"D1 = -8",
			"E1 = 84",
			"F1 = (sum A1:E1)",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'F', 1)).matches(" 210\\.0 ");
	}

	@Test
	public void TextExcelRowAverage()
	{
		String[] input =
		{
			"A1 = 76",
			"B1 = 43",
			"C1 = 15",
			"D1 = -8",
			"E1 = 84",
			"F1 = (avg A1:E1)",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'F', 1)).matches(" 42\\.0 ");
	}

	@Test
	public void TextExcelRectangleSum()
	{
		String[] input =
		{
			"A1 = 76",
			"B1 = 44",
			"A2 = 15",
			"B2 = -8",
			"A3 = 41",
			"B3 = 84",
			"A6 = (sum A1:B3)",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 6)).matches(" 252\\.0 ");
	}

	@Test
	public void TextExcelRectangleAverage()
	{
		String[] input =
		{
			"A1 = 76",
			"B1 = 44",
			"A2 = 15",
			"B2 = -8",
			"A3 = 41",
			"B3 = 84",
			"A6 = (avg A1:B3)",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 6)).matches(" 42\\.0 ");
	}

	@Test
	public void TextExcelSumSingleCell()
	{
		String[] input =
		{
			"A1 = 42",
			"A2 = (sum A1:A1)",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 2)).matches(" 42\\.0 ");
	}

	@Test
	public void TextExcelAvgSingleCell()
	{
		String[] input =
		{
			"A1 = 42",
			"A2 = (avg A1:A1)",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 2)).matches(" 42\\.0 ");
	}

	@Test
	public void TextExcelSumFormula()
	{
		String[] input =
		{
			"A1 = 76",
			"A2 = 43",
			"A3 = (1 + 2 + 3 + 4 + 5)",
			"A4 = -8",
			"A5 = 84",
			"A6 = (sum A1:A5)",
			"print",
			"quit"
		};
		String.join(newline, input);
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 6)).matches(" 210\\.0 ");
	}

	@Test
	public void TextExcelAvgFormula()
	{
		String[] input =
		{
			"A1 = 76",
			"A2 = 43",
			"A3 = (1 + 2 + 3 + 4 + 5)",
			"A4 = -8",
			"A5 = 84",
			"A6 = (avg A1:A5)",
			"print",
			"quit"
		};
		String.join(newline, input);
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 6)).matches(" 42\\.0 ");
	}

	@Test
	public void TextExcelReferencesPrintedAtPrompt()
	{
		String[] input =
		{
			"A1 = 15",
			"A2 = 27",
			"A3 = (A1 + A2)",
			"A3",
			"quit"
		};
		String.join(newline, input);
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(response).matches("A3 = \\(A1 \\+ A2\\)");
	}

	@Test
	public void TextExcelReferencesPrintedInSpreadsheet()
	{
		String[] input =
		{
			"A1 = 15",
			"A2 = 27",
			"A3 = (A1 + A2)",
			"print",
			"quit"
		};
		String.join(newline, input);
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 3)).matches(" 42\\.0 ");
	}

	@Test
	public void TextExcelReferencesChanged()
	{
		String[] input =
		{
			"A1 = 15",
			"A2 = 27",
			"A3 = (A1 + A2)",
			"A2 = 5",
			"print",
			"quit"
		};
		String.join(newline, input);
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 3)).matches(" 20\\.0 ");
	}

	@Test
	public void TextExcelReferenceChain()
	{
		String[] input =
		{
			"A1 = 1",
			"A2 = 1",
			"A3 = (A1 + A2)",
			"A4 = (A2 + A3)",
			"A5 = (A3 + A4)",
			"A6 = (A4 + A5)",
			"A7 = (A5 + A6)",
			"print",
			"quit"
		};
		String.join(newline, input);
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 7)).matches(" 13\\.0 ");
	}

	@Test
	public void TextExcelSumReference()
	{
		String[] input =
		{
			"G10 = 1",
			"A1 = (G10)",
			"A2 = (sum A1:A1)",
			"A3 = (sum A1:A2)",
			"A4 = (sum A1:A3)",
			"A5 = (sum A1:A4)",
			"A6 = (sum A1:A5)",
			"A7 = (sum A1:A6)",
			"print",
			"quit"
		};
		String.join(newline, input);
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 7)).matches(" 32\\.0 ");
	}

	/*
	 * Checkpoint 5 tests
	 */
	
	@Test
	public void TextExcelSortaSingle()
	{
		String[] input =
		{
			"A1 = 76",
			"sorta A1:A1",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 1)).matches(" 76\\.0 ");
	}
	
	@Test
	public void TextExcelSortdSingle()
	{
		String[] input =
		{
			"A1 = 76",
			"sortd A1:A1",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 1)).matches(" 76\\.0 ");
	}
	
	@Test
	public void TextExcelSortaColumn()
	{
		String[] input =
		{
			"A1 = 76",
			"A2 = 43",
			"A3 = 15",
			"A4 = -8",
			"A5 = 84",
			"sorta A1:A5",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 1)).matches(" -8\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 15\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 43\\.0 ");
		verify.that(getCell(response, 'A', 4)).matches(" 76\\.0 ");
		verify.that(getCell(response, 'A', 5)).matches(" 84\\.0 ");
	}
	
	@Test
	public void TextExcelSortdColumn()
	{
		String[] input =
		{
			"G1 = 76",
			"G2 = 43",
			"G3 = 15",
			"G4 = -8",
			"G5 = 84",
			"sortd G1:G5",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'G', 1)).matches(" 84\\.0 ");
		verify.that(getCell(response, 'G', 2)).matches(" 76\\.0 ");
		verify.that(getCell(response, 'G', 3)).matches(" 43\\.0 ");
		verify.that(getCell(response, 'G', 4)).matches(" 15\\.0 ");
		verify.that(getCell(response, 'G', 5)).matches(" -8\\.0 ");
	}
	
	@Test
	public void TextExcelSortaRow()
	{
		String[] input =
		{
			"A10 = 76",
			"B10 = 43",
			"C10 = 15",
			"D10 = -8",
			"E10 = 84",
			"sorta A10:E10",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 10)).matches(" -8\\.0 ");
		verify.that(getCell(response, 'B', 10)).matches(" 15\\.0 ");
		verify.that(getCell(response, 'C', 10)).matches(" 43\\.0 ");
		verify.that(getCell(response, 'D', 10)).matches(" 76\\.0 ");
		verify.that(getCell(response, 'E', 10)).matches(" 84\\.0 ");
	}
	
	@Test
	public void TextExcelSortdRow()
	{
		String[] input =
		{
			"C10 = 76",
			"D10 = 43",
			"E10 = 15",
			"F10 = -8",
			"G10 = 84",
			"sortd C10:G10",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'C', 10)).matches(" 84\\.0 ");
		verify.that(getCell(response, 'D', 10)).matches(" 76\\.0 ");
		verify.that(getCell(response, 'E', 10)).matches(" 43\\.0 ");
		verify.that(getCell(response, 'F', 10)).matches(" 15\\.0 ");
		verify.that(getCell(response, 'G', 10)).matches(" -8\\.0 ");
	}
	
	@Test
	public void TextExcelRectangleSorta()
	{
		String[] input =
		{
			"A1 = 76",
			"B1 = 44",
			"A2 = 15",
			"B2 = -8",
			"A3 = 41",
			"B3 = 84",
			"sorta A1:B3",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 1)).matches(" -8\\.0 ");
		verify.that(getCell(response, 'B', 1)).matches(" 15\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 41\\.0 ");
		verify.that(getCell(response, 'B', 2)).matches(" 44\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 76\\.0 ");
		verify.that(getCell(response, 'B', 3)).matches(" 84\\.0 ");
	}

	@Test
	public void TextExcelRectangleSortd()
	{
		String[] input =
		{
			"A1 = 76",
			"B1 = 44",
			"A2 = 15",
			"B2 = -8",
			"A3 = 41",
			"B3 = 84",
			"sortd A1:B3",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 1)).matches(" 84\\.0 ");
		verify.that(getCell(response, 'B', 1)).matches(" 76\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 44\\.0 ");
		verify.that(getCell(response, 'B', 2)).matches(" 41\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 15\\.0 ");
		verify.that(getCell(response, 'B', 3)).matches(" -8\\.0 ");
	}
	
	@Test
	public void TextExcelSortReference()
	{
		String[] input =
		{
			"G10 = 100",
			"A1 = 76",
			"A2 = 43",
			"A3 = 15",
			"A4 = (G10)",
			"A5 = 84",
			"sorta A1:A5",
			"G10 = 1",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 1)).matches(" 15\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 43\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 76\\.0 ");
		verify.that(getCell(response, 'A', 4)).matches(" 84\\.0 ");
		verify.that(getCell(response, 'A', 5)).matches(" 1\\.0 ");
	}
	
	@Test
	public void TextExcelSortaDuplicate()
	{
		String[] input =
		{
			"A1 = 0",
			"A2 = 1",
			"A3 = 1",
			"sorta A1:A3",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 1)).matches(" 0\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 1\\.0 ");
		
		String[] input2 =
		{
			"A1 = 1",
			"A2 = 0",
			"A3 = 1",
			"sorta A1:A3",
			"print",
			"quit"
		};
		response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input2));
		verify.that(getCell(response, 'A', 1)).matches(" 0\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 1\\.0 ");
		
		String[] input3 =
		{
			"A1 = 1",
			"A2 = 1",
			"A3 = 0",
			"sorta A1:A3",
			"print",
			"quit"
		};
		response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input3));
		verify.that(getCell(response, 'A', 1)).matches(" 0\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 1\\.0 ");
	}
	
	@Test
	public void TextExcelSortdDuplicate()
	{
		String[] input =
		{
			"A1 = 0",
			"A2 = 1",
			"A3 = 1",
			"sortd A1:A3",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 1)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 0\\.0 ");
		
		String[] input2 =
		{
			"A1 = 1",
			"A2 = 0",
			"A3 = 1",
			"sortd A1:A3",
			"print",
			"quit"
		};
		response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input2));
		verify.that(getCell(response, 'A', 1)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 0\\.0 ");
		
		String[] input3 =
		{
			"A1 = 1",
			"A2 = 1",
			"A3 = 0",
			"sortd A1:A3",
			"print",
			"quit"
		};
		response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input3));
		verify.that(getCell(response, 'A', 1)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 0\\.0 ");
	}
	
	@Test
	public void TextExcelSortaPermutation()
	{
		String[] input =
		{
			"A1 = 0",
			"A2 = 1",
			"A3 = 2",
			"sorta A1:A3",
			"print",
			"quit"
		};
		String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
		verify.that(getCell(response, 'A', 1)).matches(" 0\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 2\\.0 ");
		
		String[] input2 =
		{
			"A1 = 0",
			"A2 = 2",
			"A3 = 1",
			"sorta A1:A3",
			"print",
			"quit"
		};
		response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input2));
		verify.that(getCell(response, 'A', 1)).matches(" 0\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 2\\.0 ");
		
		String[] input3 =
		{
			"A1 = 1",
			"A2 = 0",
			"A3 = 2",
			"sorta A1:A3",
			"print",
			"quit"
		};
		response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input3));
		verify.that(getCell(response, 'A', 1)).matches(" 0\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 2\\.0 ");
		
		String[] input4 =
		{
			"A1 = 1",
			"A2 = 2",
			"A3 = 0",
			"sorta A1:A3",
			"print",
			"quit"
		};
		response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input4));
		verify.that(getCell(response, 'A', 1)).matches(" 0\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 2\\.0 ");
		
		String[] input5 =
		{
			"A1 = 2",
			"A2 = 0",
			"A3 = 1",
			"sorta A1:A3",
			"print",
			"quit"
		};
		response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input5));
		verify.that(getCell(response, 'A', 1)).matches(" 0\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 2\\.0 ");
		
		String[] input6 =
		{
			"A1 = 2",
			"A2 = 1",
			"A3 = 0",
			"sorta A1:A3",
			"print",
			"quit"
		};
		response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input6));
		verify.that(getCell(response, 'A', 1)).matches(" 0\\.0 ");
		verify.that(getCell(response, 'A', 2)).matches(" 1\\.0 ");
		verify.that(getCell(response, 'A', 3)).matches(" 2\\.0 ");
	}
	
	@Test
	public void TextExcelSortaGauntlet()
	{
		final int PERMUTATIONS = 100;
		final int NUM_COLS = 7;
		final int NUM_ROWS = 10;
		final int NUM_INTS = NUM_COLS * NUM_ROWS;
		
		for (int j = 0; j < PERMUTATIONS; j++)
		{
			// Shuffle enough integers to fill spreadsheet
			List<Integer> shuffledInts = new ArrayList<Integer>();
			for (int i = 0; i < NUM_INTS; i++) {
				shuffledInts.add(i);
			}
			Collections.shuffle(shuffledInts);
			
			// Generate string of commands to fill spreadsheet and sort it all
			String[] input = new String[NUM_INTS + 3];
			for (int i = 0; i < NUM_INTS; i++)
			{
				int row = i / NUM_COLS + 1;
				char col = (char) ('A' + i % NUM_COLS);
				input[i] = String.format("%c%d = %d", col, row, shuffledInts.get(i));
			}
			input[NUM_INTS] = "sorta A1:G10";
			input[NUM_INTS + 1] = "print";
			input[NUM_INTS + 2] = "quit";
							
			String response = ConsoleTester.getOutput("textExcel5.TextExcel", String.join(newline, input));
			
			// Check that every cell has been correctly sorted
			for (int i = 0; i < NUM_INTS; i++)
			{
				int row = i / NUM_COLS + 1;
				char col = (char) ('A' + i % NUM_COLS);
				verify.that(getCell(response, col, row)).matches(String.format(" %d\\.0 ", i));
			}
		}
	}

	/*
	 * Private helpers
	 */

	private String getCell(String response, char displayCol, int displayRow)
	{
		// Add 2 to row_length for final pipe + line ending
		int rowLength = (COLS * COLUMN_WIDTH + 2) * 2;

		if (System.getProperty("os.name").startsWith("Windows"))
		{
			// Adjust values based on line endings
			rowLength += 2;
		}

		int dataStart = rowLength + rowLength / 2 + COLUMN_WIDTH + 1;
		int gridLength = rowLength * ROWS + rowLength / 2 - 1;

		int row = displayRow - 1;
		int col = displayCol - 'A';
		int gridStart = response.indexOf("+----");
		if (gridStart < 0)
		{
			return "Couldn't get cell contents. Grid not displayed.";
		}
		String grid = response.substring(gridStart, gridStart + gridLength);
		int cellStart = dataStart + row * rowLength + col * COLUMN_WIDTH;
		return grid.substring(cellStart, cellStart + COLUMN_WIDTH - 1);
	}

	// Fisher-Yates shuffle
	static void shuffleArray(int[] arr)
	{
		Random rnd = new Random();
		for (int i = arr.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			int a = arr[index];
			arr[index] = arr[i];
			arr[i] = a;
		}
	}
}