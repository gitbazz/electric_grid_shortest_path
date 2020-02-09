/**
 * DLList represents a doubly linked list.
 *
 * @author Bazillah Zargar
 */

public class DLList<T> implements DLListADT<T> {
	private DLNode<T> front; // reference to the first node of the DLL
	private DLNode<T> rear; // reference to the last node of the DLL
	private int count; // number of data items in the DLL

	// Creates an empty list
	public DLList() {
		front = null;
		rear = null;
		count = 0;
	}

	/**
	 * Adds a new DLNode storing the given dataItem and value to the rear of the DLL
	 * 
	 * @param dataItem is the dataItem to be stored in the node
	 * @param value    is the value of the data item to be stored in the node
	 */
	public void insert(T dataItem, int value) {
		DLNode<T> newNode = new DLNode<T>(dataItem, value);
		// check if the DLL is empty
		if (rear == null) {
			newNode.setNext(null);
			newNode.setPrevious(null);
			front = newNode;
			rear = newNode;
			count++;
		}
		// if DLL is not empty
		else {
			rear.setNext(newNode);
			newNode.setNext(null);
			newNode.setPrevious(rear);
			rear = newNode;
			count++;
		}
	}

	/**
	 * Returns the integer value associated to the specified dataItem.
	 * 
	 * @param dataItem is the dataItem being searched for in the DLL
	 * @return integer value associated with the given dataItem
	 */
	public int getDataValue(T dataItem) throws InvalidDataItemException {
		DLNode<T> current = front;
		try {
			while ((!current.getData().equals(dataItem)) && (!current.equals(null))) {
				current = current.getNext();
			}
		} catch (NullPointerException e) {
			throw new InvalidDataItemException("This dataItem is not in the list");
		}
		if (current.getData().equals(null)) {
			throw new InvalidDataItemException("This dataItem is not in the list");
		} else {
			return current.getValue();
		}
	}

	/**
	 * Changes the value of dataItem in the DLL to newValue. Throws an
	 * InvalidDataItemException if the given dataItem is not in the list.
	 * 
	 * @param dataItem is the dataItem being searched for in the DLL
	 * @param newValue is the new integer value to be stored
	 */
	public void changeValue(T dataItem, int newValue) throws InvalidDataItemException {
		DLNode<T> current = front;
		try {
			while ((!current.getData().equals(dataItem)) && (!current.equals(null))) {
				current = current.getNext();
			}
		} catch (NullPointerException e) {
			throw new InvalidDataItemException("This dataItem is not in the list");
		}

		if (current.getData().equals(dataItem)) {
			current.setValue(newValue);
		} else {
			throw new InvalidDataItemException("Data item not in the priority queue.");
		}
	}

	/**
	 * Removes and returns the data item in the list with smallest associated value.
	 * If several items in the list have the same associated value, any one of them
	 * is returned. Throws an EmptyListException if the list is empty.
	 * 
	 * @return the dataItem in the list with the smallest value
	 */
	public T getSmallest() throws EmptyListException {
		DLNode<T> current = front;
		DLNode<T> smallest = front;

		// if list is empty
		if (isEmpty()) {
			throw new EmptyListException("The list is empty.");
		}

		// if list contains only 1 node
		if (count == 1) {
			T smallestItem = front.getData();
			front = null;
			rear = null;
			count--;
			return smallestItem;
		}

		// if list contains more than 1 node
		while (current != null) {
			if (current.getValue() < smallest.getValue()) {
				smallest = current;
			}
			current = current.getNext();
		}
		T smallestItem = smallest.getData();

		// check if the smallest item is at the front of the list
		if (smallest.getPrevious() == null) {
			front = smallest.getNext();
		} else {
			(smallest.getPrevious()).setNext(smallest.getNext());
		}
		// check if the smallest item is at the rear of the list
		if (smallest.getNext() == null) {
			rear = smallest.getPrevious();
		} else {
			(smallest.getNext()).setPrevious(smallest.getPrevious());
		}
		count--;
		return smallestItem;
	}

	/**
	 * Checks is the list is empty
	 * 
	 * @return true if the list is empty and false otherwise
	 */
	public boolean isEmpty() {
		return (count == 0);
	}

	/**
	 * Checks the size of the list
	 * 
	 * @return the number of data items in the list.
	 */
	public int size() {
		return count;
	}

	/**
	 * Returns a string representation of the DLL.
	 * 
	 * @return String representation of this DLL with the elements in the form:
	 *         “List: dataItem1,value1; dataItem2,value2; ...”
	 */
	public String toString() {
		String str = "List: ";
		DLNode<T> current = front;
		while (current != null) {
			str = str + current.getData() + "," + current.getValue();
			if (current.getNext() != null) {
				str = str + "; ";
			}
			current = current.getNext();
		}
		return str;
	}
}
