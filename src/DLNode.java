/**
 * DLNode represents a node in a doubly linked list.
 *
 * @author Bazillah Zargar
 */

public class DLNode<T> {

	private T dataItem; // data item stored in this node
	private DLNode<T> next; // reference to the next node in the linked list
	private DLNode<T> previous; // reference to the previous node in the linked list
	private int value; // value of the data item stored in this node

	/**
	 * Creates a node with a data item and value
	 * 
	 * @param data  is the data item to be stored in the node
	 * @param value is the value of the data item to be stored
	 */
	public DLNode(T data, int value) {
		dataItem = data;
		this.value = value;
	}

	/**
	 * Returns the value of the data item stored in this node.
	 *
	 * @return the value stored in the current node.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the dataItem stored in this node.
	 *
	 * @return the dataItem stored in the current node.
	 */
	public T getData() {
		return dataItem;
	}

	/**
	 * Returns the node that follows this one.
	 *
	 * @return the node that follows the current one
	 */
	public DLNode<T> getNext() {
		return next;
	}

	/**
	 * Returns the node that precedes this one.
	 *
	 * @return the node that precedes the current one
	 */
	public DLNode<T> getPrevious() {
		return previous;
	}

	/**
	 * Sets the dataItem stored in this node.
	 *
	 * @param data is the dataItem to be stored in the current node.
	 */
	public void setData(T data) {
		dataItem = data;
	}

	/**
	 * Sets the node that follows this one.
	 *
	 * @param nextNode the node to be set to follow the current one
	 */
	public void setNext(DLNode<T> nextNode) {
		next = nextNode;
	}

	/**
	 * Sets the node that precedes this one.
	 *
	 * @param prevNode is the node to be set that precedes the current one
	 */
	public void setPrevious(DLNode<T> prevNode) {
		previous = prevNode;
	}

	/**
	 * Sets the value to be stored in this node.
	 *
	 * @param val is the value to be stored in the current node.
	 */
	public void setValue(int val) {
		value = val;
	}
}
