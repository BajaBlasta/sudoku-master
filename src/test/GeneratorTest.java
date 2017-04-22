import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import org.junit.Test;

public class GeneratorTest {

	@Test
	public void testPrintSudoku() {
		short[] input = new short[] {
				0, 0, 8,  2, 5, 3,  1, 4, 0,	
				0, 3, 0,  4, 0, 0,  0, 0, 8,	
				1, 0, 5,  0, 6, 8,  3, 0, 0,	
				0, 0, 0,  0, 0, 0,  0, 3, 0,	
				9, 7, 0,  5, 0, 4,  0, 1, 6,	
				0, 5, 0,  0, 0, 0,  0, 0, 0,	
				0, 0, 6,  3, 8, 0,  2, 0, 4,	
				5, 0, 0,  0, 0, 9,  0, 8, 0,	
				0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		String expected =  "  8|253|14 \n" +
						   " 3 |4  |  8\n" +
						   "1 5| 68|3  \n" +
						   "-----------\n" +
						   "   |   | 3 \n" +
						   "97 |5 4| 16\n" +
						   " 5 |   |   \n" +
						   "-----------\n" +
						   "  6|38 |2 4\n" +
						   "5  |  9| 8 \n" +
						   " 89|127|6  \n";
		String actual = Generator.printSudoku(input);
		assertEquals(expected, actual);
	}

	@Test
	public void testPrintSudokuRemovals() {
		short[] input = new short[] {
				0, 0, 8,  2, 5, 3,  1, 4, 0,	
				0, 3, 0,  4, 0, 0,  0, 0, 8,	
				1, 0, 5,  0, 6, 8,  3, 0, 0,	
				0, 0, 0,  0, 0, 0,  0, 3, 0,	
				9, 7, 0,  5, 0, 4,  0, 1, 6,	
				0, 5, 0,  0, 0, 0,  0, 0, 0,	
				0, 0, 6,  3, 8, 0,  2, 0, 4,	
				5, 0, 0,  0, 0, 9,  0, 8, 0,	
				0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		String expected =  "  8|   |14 \n" +
						   " 3 |4  |  8\n" +
						   "1 5| 68|3  \n" +
						   "-----------\n" +
						   "   |   | 3 \n" +
						   "97 |5 4| 16\n" +
						   " 5 |   |   \n" +
						   "-----------\n" +
						   "  6|38 |2 4\n" +
						   "5  |  9| 8 \n" +
						   " 89|127|6  \n";
		LinkedList<Integer> removals = new LinkedList<>();
		removals.add(3);
		removals.add(4);
		removals.add(5);
		String actual = Generator.printSudoku(input, removals);
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void testGetSubgrid() {
		short[] input = new short[] {
				0, 0, 8,  2, 5, 3,  1, 4, 0,	
				0, 3, 0,  4, 0, 0,  0, 0, 8,	
				1, 0, 5,  0, 6, 8,  3, 0, 0,	
				0, 0, 0,  0, 0, 0,  0, 3, 0,	
				9, 7, 0,  5, 0, 4,  0, 1, 6,	
				0, 5, 0,  0, 0, 0,  0, 0, 0,	
				0, 0, 6,  3, 8, 0,  2, 0, 4,	
				5, 0, 0,  0, 0, 9,  0, 8, 0,	
				0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		HashSet<Short> subGrid = Generator.getSubGrid(3, input);
		assertEquals(subGrid.size(), 6);
		assertTrue(subGrid.contains((short)2));
		assertTrue(subGrid.contains((short)5));
		assertTrue(subGrid.contains((short)3));
		assertTrue(subGrid.contains((short)4));
		assertTrue(subGrid.contains((short)6));
		assertTrue(subGrid.contains((short)8));
	}
	
	@Test
	public void testGetSubgrid2() {
		short[] input = new short[] {
				0, 0, 8,  2, 5, 3,  1, 4, 0,	
				0, 3, 0,  4, 0, 0,  0, 0, 8,	
				1, 0, 5,  0, 6, 8,  3, 0, 0,	
				0, 0, 0,  0, 0, 0,  0, 3, 0,	
				9, 7, 0,  5, 0, 4,  0, 1, 6,	
				0, 5, 0,  0, 0, 0,  0, 0, 0,	
				0, 0, 6,  3, 8, 0,  2, 0, 4,	
				5, 0, 0,  0, 0, 9,  0, 8, 0,	
				0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		HashSet<Short> subGrid = Generator.getSubGrid(35, input);
		assertEquals(subGrid.size(), 3);
		assertTrue(subGrid.contains((short)1));
		assertTrue(subGrid.contains((short)3));
		assertTrue(subGrid.contains((short)6));
	}
	
	@Test
	public void testGetColumn() {
		short[] input = new short[] {
				0, 0, 8,  2, 5, 3,  1, 4, 0,	
				0, 3, 0,  4, 0, 0,  0, 0, 8,	
				1, 0, 5,  0, 6, 8,  3, 0, 0,	
				0, 0, 0,  0, 0, 0,  0, 3, 0,	
				9, 7, 0,  5, 0, 4,  0, 1, 6,	
				0, 5, 0,  0, 0, 0,  0, 0, 0,	
				0, 0, 6,  3, 8, 0,  2, 0, 4,	
				5, 0, 0,  0, 0, 9,  0, 8, 0,	
				0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		HashSet<Short> column = Generator.getColumn(35, input);
		assertEquals(column.size(), 3);
		assertTrue(column.contains((short)4));
		assertTrue(column.contains((short)6));
		assertTrue(column.contains((short)8));
	}
	
	@Test
	public void testGetColumn2() {
		short[] input = new short[] {
				0, 0, 8,  2, 5, 3,  1, 4, 0,	
				0, 3, 0,  4, 0, 0,  0, 0, 8,	
				1, 0, 5,  0, 6, 8,  3, 0, 0,	
				0, 0, 0,  0, 0, 0,  0, 3, 0,	
				9, 7, 0,  5, 0, 4,  0, 1, 6,	
				0, 5, 0,  0, 0, 0,  0, 0, 0,	
				0, 0, 6,  3, 8, 0,  2, 0, 4,	
				5, 0, 0,  0, 0, 9,  0, 8, 0,	
				0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		HashSet<Short> column = Generator.getColumn(2, input);
		assertEquals(column.size(), 4);
		assertTrue(column.contains((short)8));
		assertTrue(column.contains((short)5));
		assertTrue(column.contains((short)6));
		assertTrue(column.contains((short)9));
	}
	
	@Test
	public void testGetRow() {
		short[] input = new short[] {
				0, 0, 8,  2, 5, 3,  1, 4, 0,	
				0, 3, 0,  4, 0, 0,  0, 0, 8,	
				1, 0, 5,  0, 6, 8,  3, 0, 0,	
				0, 0, 0,  0, 0, 0,  0, 3, 0,	
				9, 7, 0,  5, 0, 4,  0, 1, 6,	
				0, 5, 0,  0, 0, 0,  0, 0, 0,	
				0, 0, 6,  3, 8, 0,  2, 0, 4,	
				5, 0, 0,  0, 0, 9,  0, 8, 0,	
				0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		HashSet<Short> row = Generator.getRow(60, input);
		assertEquals(row.size(), 5);
		assertTrue(row.contains((short)6));
		assertTrue(row.contains((short)3));
		assertTrue(row.contains((short)8));
		assertTrue(row.contains((short)2));
		assertTrue(row.contains((short)4));
	}
	
	@Test
	public void testGetRow2() {
		short[] input = new short[] {
				0, 0, 8,  2, 5, 3,  1, 4, 0,	
				0, 3, 0,  4, 0, 0,  0, 0, 8,	
				1, 0, 5,  0, 6, 8,  3, 0, 0,	
				0, 0, 0,  0, 0, 0,  0, 3, 0,	
				9, 7, 0,  5, 0, 4,  0, 1, 6,	
				0, 5, 0,  0, 0, 0,  0, 0, 0,	
				0, 0, 6,  3, 8, 0,  2, 0, 4,	
				5, 0, 0,  0, 0, 9,  0, 8, 0,	
				0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		HashSet<Short> row = Generator.getRow(10, input);
		assertEquals(row.size(), 3);
		assertTrue(row.contains((short)3));
		assertTrue(row.contains((short)4));
		assertTrue(row.contains((short)8));
	}
	
	@Test
	public void testGetOptions() {
		short[] input = new short[] {
				0, 0, 8,  2, 5, 3,  1, 4, 0,	
				0, 3, 0,  4, 0, 0,  0, 0, 8,	
				1, 0, 5,  0, 6, 8,  3, 0, 0,	
				0, 0, 0,  0, 0, 0,  0, 3, 0,	
				9, 7, 0,  5, 0, 4,  0, 1, 6,	
				0, 5, 0,  0, 0, 0,  0, 0, 0,	
				0, 0, 6,  3, 8, 0,  2, 0, 4,	
				5, 0, 0,  0, 0, 9,  0, 8, 0,	
				0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		ArrayList<Short> options = Generator.getOptions(0, input);
		assertEquals(options.size(), 2);
		assertTrue(options.contains((short)6));
		assertTrue(options.contains((short)7));
	}
	
	@Test
	public void testGetOptions2() {
		short[] input = new short[] {
				0, 0, 8,  2, 5, 3,  1, 4, 0,	
				0, 3, 0,  4, 0, 0,  0, 0, 8,	
				1, 0, 5,  0, 6, 8,  3, 0, 0,	
				0, 0, 0,  0, 0, 0,  0, 3, 0,	
				9, 7, 0,  5, 0, 4,  0, 1, 6,	
				0, 5, 0,  0, 0, 0,  0, 0, 0,	
				0, 0, 6,  3, 8, 0,  2, 0, 4,	
				5, 0, 0,  0, 0, 9,  0, 8, 0,	
				0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		ArrayList<Short> options = Generator.getOptions(40, input);
		assertEquals(options.size(), 1);
		assertTrue(options.contains((short)3));
	}

	@Test
	public void testSolver() {
		short[] input = new short[] {
			1, 2, 4,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 8, 0
		};
		short[] solution = new short[81];
		Generator.solveSudoku(input, solution);
		assertEquals(1, solution[0]);
		assertEquals(2, solution[1]);
		assertEquals(4, solution[2]);
		assertEquals(8, solution[79]);
		for(int i = 0; i < solution.length; i++) {
			assertTrue(solution[i] > 0);
		}
	}

	@Test
	public void testSolver2() {
		short[] input = new short[] {
			0, 0, 8,  2, 5, 3,  1, 4, 0,	
			0, 3, 0,  4, 0, 0,  0, 0, 8,	
			1, 0, 5,  0, 6, 8,  3, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 3, 0,	
			9, 7, 0,  5, 0, 4,  0, 1, 6,	
			0, 5, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 6,  3, 8, 0,  2, 0, 4,	
			5, 0, 0,  0, 0, 9,  0, 8, 0,	
			0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		short[] expected = new short[] {
			6, 9, 8,  2, 5, 3,  1, 4, 7,
			2, 3, 7,  4, 9, 1,  5, 6, 8,
			1, 4, 5,  7, 6, 8,  3, 2, 9,
			8, 6, 1,  9, 7, 2,  4, 3, 5,
			9, 7, 2,  5, 3, 4,  8, 1, 6,
			3, 5, 4,  8, 1, 6,  9, 7, 2,
			7, 1, 6,  3, 8, 5,  2, 9, 4,
			5, 2, 3,  6, 4, 9,  7, 8, 1,
			4, 8, 9,  1, 2, 7,  6, 5, 3
		};
		short[] solution = new short[81];
		Generator.solveSudoku(input, solution);
		for(int i = 0; i < solution.length; i++) {
			assertEquals(expected[i], solution[i]);
		}
	}

	@Test
	public void testSolver3() {
		short[] input = new short[] {
			1, 0, 0,  0, 0, 0,  0, 0, 3,	
			7, 0, 2,  3, 5, 0,  0, 0, 0,	
			0, 3, 0,  0, 0, 8,  0, 6, 0,	
			5, 0, 0,  0, 2, 0,  4, 0, 0,	
			0, 0, 7,  0, 0, 0,  2, 0, 0,	
			0, 0, 4,  0, 7, 0,  0, 0, 8,	
			0, 9, 0,  8, 0, 0,  0, 5, 0,	
			0, 0, 0,  0, 1, 9,  7, 0, 6,	
			8, 0, 0,  0, 0, 0,  0, 0, 1
		};
		short[] expected = new short[] {
			1, 4, 8,  6, 9, 2,  5, 7, 3,  
			7, 6, 2,  3, 5, 1,  8, 4, 9,  
			9, 3, 5,  7, 4, 8,  1, 6, 2,  
			5, 8, 9,  1, 2, 6,  4, 3, 7,  
			6, 1, 7,  4, 8, 3,  2, 9, 5,  
			3, 2, 4,  9, 7, 5,  6, 1, 8,  
			2, 9, 1,  8, 6, 7,  3, 5, 4,  
			4, 5, 3,  2, 1, 9,  7, 8, 6,  
			8, 7, 6,  5, 3, 4,  9, 2, 1
		};
		short[] solution = new short[81];
		Generator.solveSudoku(input, solution);
		for(int i = 0; i < solution.length; i++) {
			assertEquals(expected[i], solution[i]);
		}
	}

	@Test
	public void testSolver4() {
		short[] input = new short[] {
			1, 2, 4,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 8, 0
		};
		short[] solution = new short[81];
		int solutions = Generator.solveSudoku(input, solution);
		assertEquals(2, solutions);
	}
	
	@Test
	public void testSolver5() {
		short[] input = new short[] {
			1, 0, 0,  0, 0, 0,  0, 0, 3,	
			7, 0, 2,  3, 5, 0,  0, 0, 0,	
			0, 3, 0,  0, 0, 8,  0, 6, 0,	
			5, 0, 0,  0, 2, 0,  4, 0, 0,	
			0, 0, 7,  0, 0, 0,  2, 0, 0,	
			0, 0, 4,  0, 7, 0,  0, 0, 8,	
			0, 9, 0,  8, 0, 0,  0, 5, 0,	
			0, 0, 0,  0, 1, 9,  7, 0, 6,	
			8, 0, 0,  0, 0, 0,  0, 0, 1
		};
		short[] solution = new short[81];
		int solutions = Generator.solveSudoku(input, solution);
		assertEquals(1, solutions);
	}
	
	@Test
	public void testSolver6() {
		short[] input = new short[] {
			1, 1, 0,  0, 0, 0,  0, 0, 3,	
			7, 0, 2,  3, 5, 0,  0, 0, 0,	
			0, 3, 0,  0, 0, 8,  0, 6, 0,	
			5, 0, 0,  0, 2, 0,  4, 0, 0,	
			0, 0, 7,  0, 0, 0,  2, 0, 0,	
			0, 0, 4,  0, 7, 0,  0, 0, 8,	
			0, 9, 0,  8, 0, 0,  0, 5, 0,	
			0, 0, 0,  0, 1, 9,  7, 0, 6,	
			8, 0, 0,  0, 0, 0,  0, 0, 1
		};
		short[] solution = new short[81];
		int solutions = Generator.solveSudoku(input, solution);
		assertEquals(0, solutions);
	}
	
	@Test
	public void testGenerateSudoku() {
		short[] sudoku = Generator.generateSudoku();
		for(int i = 0; i < sudoku.length; i++) {
			HashSet<Short> subGrid = Generator.getSubGrid(i, sudoku);
			HashSet<Short> row = Generator.getRow(i, sudoku);
			HashSet<Short> column = Generator.getColumn(i, sudoku);
			for(short j = 1; j < 9; j++) {
				assertTrue(subGrid.contains(j));
				assertTrue(row.contains(j));
				assertTrue(column.contains(j));
			}
		}
	}
	
	@Test
	public void testIsUniquelySolvable() {
		short[] input = new short[] {
			1, 2, 4,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 8, 0
		};
		assertTrue(! Generator.isUniquelySolvable(input));
	}
	
	@Test
	public void testIsUniquelySolvable2() {
		short[] input = new short[] {
			0, 0, 8,  2, 5, 3,  1, 4, 0,	
			0, 3, 0,  4, 0, 0,  0, 0, 8,	
			1, 0, 5,  0, 6, 8,  3, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 3, 0,	
			9, 7, 0,  5, 0, 4,  0, 1, 6,	
			0, 5, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 6,  3, 8, 0,  2, 0, 4,	
			5, 0, 0,  0, 0, 9,  0, 8, 0,	
			0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		assertTrue(Generator.isUniquelySolvable(input));
	}
	
	@Test
	public void testIsUniquelySolvable3() {
		short[] input = new short[] {
			1, 1, 0,  0, 0, 0,  0, 0, 3,	
			7, 0, 2,  3, 5, 0,  0, 0, 0,	
			0, 3, 0,  0, 0, 8,  0, 6, 0,	
			5, 0, 0,  0, 2, 0,  4, 0, 0,	
			0, 0, 7,  0, 0, 0,  2, 0, 0,	
			0, 0, 4,  0, 7, 0,  0, 0, 8,	
			0, 9, 0,  8, 0, 0,  0, 5, 0,	
			0, 0, 0,  0, 1, 9,  7, 0, 6,	
			8, 0, 0,  0, 0, 0,  0, 0, 1
		};
		assertTrue(! Generator.isUniquelySolvable(input));
	}
	
	@Test
	public void testIsUniquelySolvable4() {
		short[] input = new short[] {
			1, 1, 0,  0, 0, 0,  0, 0, 3,	
			7, 0, 2,  3, 5, 0,  0, 0, 0,	
			0, 3, 0,  0, 0, 8,  0, 6, 0,	
			5, 0, 0,  0, 2, 0,  4, 0, 0,	
			0, 0, 7,  0, 0, 0,  2, 0, 0,	
			0, 0, 4,  0, 7, 0,  0, 0, 8,	
			0, 9, 0,  8, 0, 0,  0, 5, 0,	
			0, 0, 0,  0, 1, 9,  7, 0, 6,	
			8, 0, 0,  0, 0, 0,  0, 0, 1
		};
		LinkedList<Integer> removals = new LinkedList<Integer>();
		removals.add(1);
		assertTrue(Generator.isUniquelySolvable(input, removals));
	}
	
	@Test
	public void testIsSolvable() {
		short[] input = new short[] {
			1, 2, 4,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 8, 0
		};
		assertTrue(Generator.isSolvable(input));
	}
	
	@Test
	public void testIsSolvable2() {
		short[] input = new short[] {
			0, 0, 8,  2, 5, 3,  1, 4, 0,	
			0, 3, 0,  4, 0, 0,  0, 0, 8,	
			1, 0, 5,  0, 6, 8,  3, 0, 0,	
			0, 0, 0,  0, 0, 0,  0, 3, 0,	
			9, 7, 0,  5, 0, 4,  0, 1, 6,	
			0, 5, 0,  0, 0, 0,  0, 0, 0,	
			0, 0, 6,  3, 8, 0,  2, 0, 4,	
			5, 0, 0,  0, 0, 9,  0, 8, 0,	
			0, 8, 9,  1, 2, 7,  6, 0, 0
		};
		assertTrue(Generator.isSolvable(input));
	}
	
	@Test
	public void testIsSolvable3() {
		short[] input = new short[] {
			1, 1, 0,  0, 0, 0,  0, 0, 3,	
			7, 0, 2,  3, 5, 0,  0, 0, 0,	
			0, 3, 0,  0, 0, 8,  0, 6, 0,	
			5, 0, 0,  0, 2, 0,  4, 0, 0,	
			0, 0, 7,  0, 0, 0,  2, 0, 0,	
			0, 0, 4,  0, 7, 0,  0, 0, 8,	
			0, 9, 0,  8, 0, 0,  0, 5, 0,	
			0, 0, 0,  0, 1, 9,  7, 0, 6,	
			8, 0, 0,  0, 0, 0,  0, 0, 1
		};
		assertTrue(! Generator.isSolvable(input));
	}
	
	@Test
	public void testGeneratePuzzle() {
		short[] solution = Generator.generateSudoku();
		short[] sudoku = Generator.generatePuzzle(solution, 40);
		int count = 0;
		for(int i = 0; i < sudoku.length; i++) {
			if(sudoku[i] > 0) {
				count++;
			}
		}
		assertEquals(40, count);
		assertTrue(Generator.isUniquelySolvable(sudoku));
	}
	
	@Test
	public void testGeneratePuzzle2() {
		short[] solution = Generator.generateSudoku();
		short[] sudoku = Generator.generatePuzzle(solution, 24);
		int count = 0;
		for(int i = 0; i < sudoku.length; i++) {
			if(sudoku[i] > 0) {
				count++;
			}
		}
		assertEquals(25, count);
		assertTrue(Generator.isUniquelySolvable(sudoku));
	}
}
