/**
 * Undocumentated methods are getters and setters
 */
package data.sinogramas;

/**
 * This class is our own implementation of Nodes so we can handle structures with references
 * @author dequi
 * @author small-nightingale
 * @version 2.0
 * @since 15/09/2020
 */

public class NodeGeneric<T> {
    private T data;
    private NodeGeneric<T> next; // right
    private NodeGeneric<T> prev; // left
    private int height;

    /**
     * Constructor of a Node: stores a value in memory and sets the next node to null
     * @param data: key to store
     */
    public NodeGeneric(T data) {
        this.data = data;
        height = 1; //Initial height
    }

    public T getData() {
        return this.data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public NodeGeneric<T> getNext() {
        return this.next;
    }
    public void setNext(NodeGeneric<T> next) {
        this.next = next;
    }
    public NodeGeneric<T> getPrev() {
        return this.prev;
    }
    public void setPrev(NodeGeneric<T> prev) {
        this.prev = prev;
    }
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int h) {
        this.height = h;
    }

    /**
     * This method gives a visual representation of a node, if next attribute is not empty, the nodes looks like a list
     * @return a string with the data of a node within square brackes
     */
    public String toString() {
        StringBuilder toPrint = new StringBuilder("[");
        NodeGeneric<T> ref = this;
        while(ref!=null) {
            if(ref.getData()!=null) {
                toPrint.append("["+ref.getData()+"]");
            }
            ref = ref.getNext();
        }
        toPrint.append("]");
        return toPrint.toString();
    }
}