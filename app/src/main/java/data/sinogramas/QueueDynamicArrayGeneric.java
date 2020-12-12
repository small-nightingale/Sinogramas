package data.sinogramas;

import java.util.LinkedList;

/**
 * This class is a hybrid between a dynamic queue and a stack.
 * @author dequi
 * @author small-nightingale
 * @version 2.0
 * @since 08/09/2020
 */
public class QueueDynamicArrayGeneric<T>{
    private int front;
    private int rear;
    private int count;  //This keeps tracks of the elements of the array
    private int size;   //This is the fixed size of the array
    private T[] queueArray;

    /**
     * Class constructor, the initial values starts at 0 and the array with a size of 16
     * The size of the array doubles if needed.
     */
    public QueueDynamicArrayGeneric() {
        front = rear = count =0;
        queueArray = (T[]) new Object[16];
        this.size = queueArray.length;
    }

    /**
     * Checks whether queue has no elements
     * @return true if the queue is empty, false otherwise
     */
    public boolean empty() {
        return count <=0;
    }
    /**
     * Checks whether queue has any elements
     * @return true if the queue is full, false otherwise
     */
    public boolean full() {
        return count>=this.size;
    }

    /**
     * Takes the front element of the queue, removes it.
     * @return the element removed
     */
    public T dequeue() {
        T item = null;
        if(!this.empty()) {
            item = queueArray[front];
            front++;
            count--;
        }
        return item;
    }

    /**
     * Inserts an element at the end of the queue
     * If the queue is full, the size of it will double
     * @param item: element to insert
     */
    public void enqueue(T item) {
        if (this.rear == this.size) {
            T[] newArray = (T[]) new Comparable[2 * this.size];
            for (int i = 0; i < this.size; i++) {
                newArray[i] = queueArray[i];
            }
            this.queueArray = newArray;
            this.size = queueArray.length;
        }
        queueArray[rear] = item;
        rear++;
        count++;
    }

    /**
     * Gives the length of the queue; a.k.a. position of the last element added
     * @return count: Length;
     */
    public int length() {
        return this.count;
    }
 
    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder("[");
        int iterator = front; 
        for (int i=0; i<count; i++) {
            toPrint.append(queueArray[iterator] + ",");
            iterator++;
            if (iterator%this.length()==0) {
                iterator=0;
            }
        }
        if (toPrint.length()>1) {
            toPrint.setCharAt(toPrint.lastIndexOf(","), ']');
        } else {
            toPrint.append(']');
        }
        
        return toPrint.toString();
    }

    /**
     * Peeks and gets the element at the given position
     * @param position: position to check
     * @return Element at the given position
     */
    public T getItem(int position) {
        return this.queueArray[position];
    }
}