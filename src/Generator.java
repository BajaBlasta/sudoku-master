import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class Generator {
	public static Random rand = new Random();
	public final static int SIZE = 3;
	
	public static void printSudoku(short[] sudoku) {
		for(int i = 0; i < (SIZE*SIZE); i++) {
			if(i > 0 && i%SIZE == 0) {
				for(int j = 0; j < (SIZE*SIZE)+SIZE-1; j++)
					System.out.print('-');
				System.out.println();
			}
			for(int j = 0; j < (SIZE*SIZE); j++) {
				if(j > 0 && j%SIZE == 0)
					System.out.print('|');
				short value = sudoku[i*(SIZE*SIZE) + j];
				char c;
				if(value == 0)
					c = ' ';
				else if(value < 10)
					c = (char) ('0' + value);
				else
					c = (char) ('a' + (value - 10));
				System.out.print(c);
			}
			System.out.println();
		}
	}
	
	public static HashSet<Short> getSubGrid(int index, short[] sudoku) {
		HashSet<Short> subGrid = new HashSet<Short>();
		int gridRow = index / (SIZE*SIZE*SIZE);
		int gridColumn = (index % (SIZE*SIZE)) / SIZE;
		for(int i = 0; i < SIZE; i++)
			for(int j = 0; j < SIZE; j++) {
				int pos = (gridRow * SIZE + i) * (SIZE*SIZE) + gridColumn * SIZE + j;
				if(sudoku[pos] > 0)
					subGrid.add(sudoku[pos]);
			}
		return subGrid;
	}
	
	public static HashSet<Short> getColumn(int index, short[] sudoku) {
		HashSet<Short> column = new HashSet<Short>();
		int columnIndex = index % (SIZE*SIZE);
		for(int i = 0; i < (SIZE*SIZE); i++) {
			int pos = i*(SIZE*SIZE) + columnIndex;
			if(sudoku[pos] > 0)
				column.add(sudoku[pos]);
		}
		return column;
	}
	
	public static HashSet<Short> getRow(int index, short[] sudoku) {
		HashSet<Short> row = new HashSet<Short>();
		int rowIndex = index / (SIZE*SIZE);
		for(int i = 0; i < (SIZE*SIZE); i++) {
			int pos = rowIndex * (SIZE*SIZE) + i;
			if(sudoku[pos] > 0)
				row.add(sudoku[pos]);
		}
		return row;
	}
	
	public static ArrayList<Short> getOptions(int index, short[] sudoku) {
		if(index >= (SIZE*SIZE*SIZE*SIZE))
			return new ArrayList<>();
		ArrayList<Short> options = new ArrayList<Short>();
		for(int i = 1; i <= (SIZE*SIZE); i++)
			options.add((short) i);
		for(short s : getRow(index, sudoku))
			options.remove(new Short(s));
		for(short s : getColumn(index, sudoku))
			options.remove(new Short(s));
		for(short s : getSubGrid(index, sudoku))
			options.remove(new Short(s));
		return options;
	}

	/**
	 * @param input        sudoku to solve
	 * @param solution     solution will be put in here
	 * @return returns     -1 for invalid input
	 *                     0 for no solution
	 *                     1 for one solution
	 *                     2 for 2 or more solutions (if there are more than two it will return 2 still)
	 */
	public static int solveSudoku(short[] input, short[] solution) {
		if((input.length != solution.length) || (input.length != (SIZE*SIZE*SIZE*SIZE)))
			return -1;
		short[] tempSolution = new short[solution.length];
		for(int i = 0; i < tempSolution.length; i++)
			tempSolution[i] = input[i];
		LinkedList<ArrayList<Short>> stack = new LinkedList<ArrayList<Short>>();
		ArrayList<Short> options;
		boolean forward = true;
		int index = 0;
		int solutionCount = 0;
		while(index < input.length && index >= 0) {
			if(input[index] > 0) {
				if(forward)
					index++;
				else
					index--;
			} else {
				if(forward) {
					options = getOptions(index, tempSolution);
					if(options.size() > 0) {
						short choice = options.remove(rand.nextInt(options.size()));
						tempSolution[index] = choice;
						stack.addLast(options);
						index++;
					} else {
						forward = false;
						index--;
					}
				} else {
					options = stack.removeLast();
					if(options.size() > 0) {
						short choice = options.remove(rand.nextInt(options.size()));
						tempSolution[index] = choice;
						stack.addLast(options);
						forward = true;
						index++;
					} else {
						tempSolution[index] = 0;
						index--;
					}
				}
			}
			if(index == input.length) {
			  for(int i = 0; i < tempSolution.length; i++) {
			    solution[i] = tempSolution[i];
			  }
			  solutionCount++;
			  index--;
			  forward = false;
			}
			if(solutionCount > 1) {
			  return 2;
			}
		}
		return solutionCount;
	}
	
	public static short[] generateSudoku() {
		short[] input = new short[81];
		short[] solution = new short[81];
		solveSudoku(input, solution);
		return solution;
	}

	/*
	public static short[] generateSudoku() {
		short[] sudoku = new short[(SIZE*SIZE*SIZE*SIZE)];
		LinkedList<ArrayList<Short>> stack = new LinkedList<ArrayList<Short>>();
		ArrayList<Short> options = getOptions(stack.size(), sudoku);
		while(stack.size() < (SIZE*SIZE*SIZE*SIZE)) {
			if(options.size() > 0) {
				//if we have options choose one randomly
				short choice = options.remove(rand.nextInt(options.size()));
				sudoku[stack.size()] = choice;
				//add remaining options to the stack
				stack.addLast(options);
				options = getOptions(stack.size(), sudoku);
			} else {
				//if not backtrack
				options = stack.removeLast();
				//clear the previously set number
				sudoku[stack.size()] = 0;
			}
		}
		return sudoku;
	}
	*/
	
	public static boolean isUniqelySolvable(short[] sudoku) {
	  short[] solution = new short[sudoku.length];
	  int numSolutions = solveSudoku(sudoku, solution);
	  return numSolutions == 1;
	}
	
	public static boolean isSolvable(short[] sudoku) {
	  short[] solution = new short[sudoku.length];
	  int numSolutions = solveSudoku(sudoku, solution);
	  return numSolutions > 0;
	}
	
	public static void testSolver() {
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
		solveSudoku(input, solution);
		printSudoku(solution);
	}
	
	public static void testSolver2() {
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
		short[] solution = new short[81];
		solveSudoku(input, solution);
		printSudoku(solution);
	}
	
	public static void testSolver3() {
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
		solveSudoku(input, solution);
		printSudoku(solution);
	}
	
	public static void testSolver4() {
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
		int solutions = solveSudoku(input, solution);
		System.out.println("number of solutions:" + solutions);
		printSudoku(solution);
	}
	
	public static void testSolver5() {
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
		int solutions = solveSudoku(input, solution);
		System.out.println("number of solutions:" + solutions);
		printSudoku(solution);
	}
	
	public static void testSolver6() {
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
		int solutions = solveSudoku(input, solution);
		System.out.println("number of solutions:" + solutions);
		printSudoku(solution);
	}
}
