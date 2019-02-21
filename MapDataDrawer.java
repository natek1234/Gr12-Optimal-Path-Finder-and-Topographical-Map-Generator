import java.util.*;
import java.io.*;
import java.awt.*;
//Author: Ketan Vasudeva
//Date: April 4th, 2018
//Course: ICS4U1
//Purpose: A class used for reading in and processing topographic data for a mountain range.
public class MapDataDrawer
{
	// Member variables below.
	public int[][] grid;

	

	// precondition: filename refers to a data file in the same project folder as this, rows/cols refers to the 
	//				 numbers of rows and columns in the file
	// postcondition: reads the file and stores its data in an appropriate data structure
	public MapDataDrawer(String filename, int rows, int cols) throws FileNotFoundException {
		// initialize grid 
		//read the data from the file into the grid
		
		File file = new File(filename);
		
		//Scanner using above file.
		Scanner input = new Scanner(file);
		
		//Creates grid.
		grid = new int[rows][cols];
		
		//Collects the data from the file into the 2d array.
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < cols; c++)
			{
				grid[r][c] = input.nextInt();
			}
		}

	}

	// precondition: none
	// postcondition: returns the min. elevation value in the entire grid
	public int findMinValue(){
		//Sets default min value.
		int min = grid[0][0]; 
		//Looks through entire array for smallest value.
		for(int r = 0; r < grid.length; r++)
		{
			for(int c = 0; c < grid[0].length; c++)
			{
				if(min > grid[r][c])
				{
					min = grid[r][c];
				}
			}
		}
		//Returns the smallest value.
		return min;    
	}

	// precondition: none
	// postcondition: returns the max. elevation value in the entire grid
	public int findMaxValue(){
		//Sets default max value.
		int max = grid[0][0];
		//Looks through entire array for largest value.
		for(int r = 0; r < grid.length; r++)
		{
			for(int c = 0; c < grid[0].length; c++)
			{
				if(max < grid[r][c])
				{
					max = grid[r][c];
				}
			}
		}
		//Returns max value.
		return max;
	}

	// precondition: g is a non-null Graphics object
	// postcondition: Draws the grid using the given Graphics object. Colors should be grayscale values 0-255 and 
	// 				  scaled based on min/max values in the grid.
	public void drawMap(Graphics g){

		//Finds the max and min values.
		int max = findMaxValue();
		int min = findMinValue();
		
		//Initializes variable scale.
		int scale;
		//Loops through data set and associates elevation to a shade of grey or black.
		for(int r = 0; r < grid.length; r++)
		{
			for(int c = 0; c < grid[0].length; c++)
			{
				scale =(int) ( (((double)grid[r][c] - min) / (double)(max-min)) * 255 );
				g.setColor(new Color(scale, scale, scale));
				g.fillRect(c,r,1,1);
				
			}
		}
	}

	// precondition: g is a non-null Graphics object, row is a value between 0 and the number of rows in the provided data file.
	// postcondition: Finds the lowest-elevation-change path from West-to-East starting at the given row.
	// Chooses a forward step out of 3 possible forward locations using the algorithm you've chosen (describe it below!).
	// Returns the total change in elevation for the path that is found.
	public int drawLeastElevChangePath(Graphics g, int row){
		
		//Initializes necessary variables.
		int max = findMaxValue();
		int min = findMinValue();
		int forward = 0;
		int topRight = 0;
		int bottomRight = 0;
		int change = 0;
		

		//Loops through the elevations along a row.
		for(int c = 0; c < grid[0].length; c++)
		{
			//Checks the possible options if available and finds the elevation change (top right, bottom right, and forward).
			if(c != (grid[0].length - 1))
			{
				forward = Math.abs(grid[row][c] - grid[row][c+1]);
			}

			if(row != 0 && c != (grid[0].length - 1))
			{
				topRight = Math.abs(grid[row][c] - grid[row-1][c+1]);
			}
			else
			{
				topRight = (max - min) + 1;
			}

			if(row != (grid.length - 1) && c != (grid[0].length - 1))
			{
				bottomRight = Math.abs(grid[row][c] - grid[row+1][c+1]);
			}
			else
			{
				bottomRight =  (max - min) + 1;
			}

			//Fills the current location a coloured square.
			g.fillRect(c, row, 1, 1);
			
			//Determines the path of least elevation and adds that change to the overall change. Also moves row if necessary.
			if(forward <= topRight && forward <= bottomRight)
			{
				change = change + forward;
			}
			else if(topRight < forward && topRight < bottomRight && row != 0)
			{
				row--;
				change = change + topRight;
								
			}
			else if(bottomRight < forward && bottomRight < topRight && row != grid.length - 1)
			{
				row++;
				change = change + bottomRight;
			}
			//Resets variables.
			forward = 0;
			topRight = 0;
			bottomRight = 0;
		}

		//Returns overall change.
		return change;
		
	}


	// precondition: g is a non-null Graphics object
	// postcondition: Returns the index of the row that was calculated to have the lowest-elevation-change path in the 
	// entire grid using the algorithm of your choice. Draws all paths during its search. 
	// Hint: Call the drawLeastElevChangePath() as a helper method to draw each row. Keep track of the best path seen throughout 
	//		 the algorithm..
	public int indexOfLeastElevChangePath(Graphics g){
		//Sets default best row.
		int changeLarge = drawLeastElevChangePath(g, 0);
		int bestRow = 0;

		//Loops through each row, drawing the path of least elevation and finding the path of lowest elevation change.
		for(int row = 1; row < grid.length; row++)
		{
			int change = drawLeastElevChangePath(g, row);
			
			if(changeLarge > change)
			{
				changeLarge = change;
				bestRow = row;
			}

		}

		//Returns row with least elevation change.
		return bestRow;

	}
		

}


