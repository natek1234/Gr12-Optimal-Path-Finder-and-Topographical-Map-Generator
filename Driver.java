import java.awt.*;
import java.io.FileNotFoundException;

// A class that contains the main method (driver) for the Unit 2 Task.
public class Driver
{

	public static void main(String[] args) throws FileNotFoundException {

		//construct DrawingPanel, and get its Graphics context
		DrawingPanel panel = new DrawingPanel(844, 480);
		Graphics g = panel.getGraphics();

		//Test Step 1 - construct mountain map data
		MapDataDrawer map = new MapDataDrawer("Colorado_844x480.dat", 480, 844);

		//Test Step 2 - min, max, 
		int min = map.findMinValue();
		System.out.println("Min value in map: "+min);

		int max = map.findMaxValue();
		System.out.println("Max value in map: "+max);


		//Test Step 3 - draw the map
		map.drawMap(g);

		//Test Step 4 - draw lowest-elevation path starting from row 5 (chosen randomly). Returns the total elevation change.
		g.setColor(Color.RED); //can set the color of the 'brush' before drawing, then method doesn't need to worry about it

		//int change = map.drawLeastElevChangePath(g,0);


		//map.drawMap(g); //use this to get rid of all red lines previously drawn.

		// Draw all paths and obtain the row of the path with least elevation change.
		int bestRow = map.indexOfLeastElevChangePath(g);

		g.setColor(Color.GREEN); 	//set brush to green for drawing best path
		int leastChange = map.drawLeastElevChangePath(g, bestRow);
		System.out.println("The Lowest-Elevation-Change Path starts at row: "+bestRow+" and gives a total change of: "+leastChange);


	}


}
