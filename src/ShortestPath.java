
/**
 * Finds the shortest path from the WPC cell to the destination cell C
 * 
 * @author Bazillah Zargar
 */

import java.io.IOException;

public class ShortestPath {
	// reference the object representing the city map where WPC and C are located
	Map cityMap;

	/**
	 * Constructor for the class
	 * 
	 * @param the given input file
	 */
	public ShortestPath(String filename) {
		try {
			cityMap = new Map(filename);
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		}
	}

	/**
	 * Find a path from the WPC cell to the destination cell C using a DLL
	 * 
	 * @param pass as command line argument the name of the input file
	 */
	public static void main(String[] args) {

		ShortestPath path = new ShortestPath(args[0]);
		try {
			// Create an empty doubly linked list
			DLList<MapCell> DLL = new DLList<MapCell>();

			// Get the starting WPC cell
			MapCell cell = path.cityMap.getStart();

			/**
			 * Insert starting cell in the linked list with a distance value of zero, and
			 * mark the cell as markInList.
			 */
			DLL.insert(cell, 0);
			cell.markInList();

			boolean pathExists = false;
			int distance = 0;

			/**
			 * While the doubly linked list is not empty and the customer cell has not been
			 * reached: Remove from the linked list the cell with the smallest distance
			 * value and mark it as out of the list
			 */
			while (!DLL.isEmpty() && !cell.isCustomer()) {
				MapCell neighbourCell;
				MapCell smallest = (MapCell) DLL.getSmallest();
				smallest.markOutList();

				// If the smallest distance cell is the destination/customer cell, exit the loop
				if (smallest.isCustomer()) {
					pathExists = true;
					distance = 1 + smallest.getDistanceToStart();
					break;
				}

				/**
				 * Consider each of smallest distance cells's unmarked neighbouring cells that
				 * can continue the path.
				 */
				while ((path.nextCell(smallest) != null) && (!path.nextCell(smallest).isMarked())) {
					neighbourCell = path.nextCell(smallest);
					distance = 1 + smallest.getDistanceToStart();
					/**
					 * If the stored distance from the neighbouring cell to the initial cell is
					 * greater than the traversed distance, set the distance of the neighbouring
					 * cell to the newly calcuated distance. Then set the predecessor of the
					 * neighbour cell to the current smallest cell.
					 */
					if (neighbourCell.getDistanceToStart() > distance) {
						neighbourCell.setDistanceToStart(distance);
						neighbourCell.setPredecessor(smallest);
					}
					int totalDistance = neighbourCell.getDistanceToStart();
					if ((neighbourCell.isMarkedInList()) && (totalDistance < DLL.getDataValue(neighbourCell))) {
						DLL.changeValue(neighbourCell, totalDistance);
					}
					/**
					 * If the neighbour cell is not marked as in the list, add it to the doubly
					 * linked list with the calculated distance value
					 */
					if (!neighbourCell.isMarkedInList()) {
						DLL.insert(neighbourCell, totalDistance);
						neighbourCell.markInList();
					}
				}

			}
			/**
			 * Print the number of cells in the shortest path from the initial cell to the
			 * customer cell.
			 */
			if (pathExists) {
				System.out.println("The destination has been reached.");
				System.out.println("There are " + distance
						+ " cells in the shortest path from the initial cell to the destination.");
			}
			// If there is no path from the initial cell to the destination
			else {
				System.out.println("There is no path from the initial cell to the customer.");

			}

		} catch (EmptyListException e) {
			System.out.println("The list is empty.");
		} catch (IllegalArgumentException a) {
			System.out.println("The argument is invalid.");
		} catch (InvalidMapException e) {
			System.out.println("The map is invalid.");
		} catch (InvalidNeighbourIndexException n) {
			System.out.println("The neighbour cell's index value is invalid.");
		}

	}

	/**
	 * Find the next cell in the path from the WPC cell to the destination cell C
	 * 
	 * @param the current cell
	 * @return the first unmarked cell that can be added to the current path. If
	 *         there are no unmarked cells adjacent to the current one that can be
	 *         used to continue the path, returns null.
	 */
	private MapCell nextCell(MapCell cell) {
		MapCell bestCell = null;

		// If current cell is power station or omni switch
		if (cell.isPowerStation() || cell.isOmniSwitch()) {
			/**
			 * Check if neighbour exists and if neighbour cell is unmarked
			 */
			for (int i = 0; i < 4; i++) {
				if ((cell.getNeighbour(i) != null) && (!cell.getNeighbour(i).isMarked())) {
					// Check if the neighbour is customer or omni switch.
					if ((cell.getNeighbour(i).isCustomer()) || (cell.getNeighbour(i).isOmniSwitch())) {
						bestCell = cell.getNeighbour(i);
						return bestCell;
					}
					// Check if the neighbour is vertical switch.
					if ((i == 0 || i == 2) && (cell.getNeighbour(i).isVerticalSwitch())) {
						bestCell = cell.getNeighbour(i);
						return bestCell;
					}
					// Check if the neighbour is horizontal switch.
					if ((i == 1 || i == 3) && (cell.getNeighbour(i).isHorizontalSwitch())) {
						bestCell = cell.getNeighbour(i);
						return bestCell;
					}

				}
			}
		}

		// If current cell is a vertical switch
		else if (cell.isVerticalSwitch()) {
			/**
			 * Check if upper or lower neighbours exist and if neighbour cell is unmarked.
			 */
			for (int i = 0; i < 4; i += 2) {
				if ((cell.getNeighbour(i) != null) && (!cell.getNeighbour(i).isMarked())) {
					// Check if the neighbour is customer or omni switch.
					if ((cell.getNeighbour(i).isCustomer()) || (cell.getNeighbour(i).isOmniSwitch())) {
						bestCell = cell.getNeighbour(i);
						return bestCell;
					}
					// Check if the neighbour is vertical switch.
					if (cell.getNeighbour(i).isVerticalSwitch()) {
						bestCell = cell.getNeighbour(i);
						return bestCell;
					}
				}
			}
		}

		// If current cell is a horizontal switch
		else if (cell.isHorizontalSwitch()) {
			/**
			 * Check if right or left neighbours exist and if neighbour cell is unmarked.
			 */
			for (int i = 1; i < 4; i += 2) {
				if ((cell.getNeighbour(i) != null) && (!cell.getNeighbour(i).isMarked())) {
					// Check if the neighbour is customer or omni switch.
					if ((cell.getNeighbour(i).isCustomer()) || (cell.getNeighbour(i).isOmniSwitch())) {
						bestCell = cell.getNeighbour(i);
						return bestCell;
					}
					// Check if the neighbour is horizontal switch.
					if (cell.getNeighbour(i).isHorizontalSwitch()) {
						bestCell = cell.getNeighbour(i);
						return bestCell;
					}
				}
			}
		}

		return bestCell;
	}

}
