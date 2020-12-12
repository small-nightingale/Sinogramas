package data.sinogramas;
import java.util.ArrayList;
/**
 * @author dequi
 */
public class MaxHeap {
    private ArrayList<Unihan> heap;
    private int maxsize;
    private int size; 

    /**
     * Class constructor: It initializes an empty max heap with a given maximun capacity
     */
    public MaxHeap() {
        this.maxsize = maxsize; 
        this.size = 0; 
        heap = new ArrayList<>();
        heap.add(0, new Unihan(Double.MAX_VALUE)); // Creates an fake Unihan object with score equals to Double.MAX_VALUE
    } 
  
    // Returns position of parent 
    private int parent(int pos) 
    { 
        return pos / 2; 
    } 
  
    // Below two functions return left and 
    // right children. 
    private int leftChild(int pos) 
    { 
        return (2 * pos); 
    } 
    private int rightChild(int pos) 
    { 
        return (2 * pos) + 1; 
    } 
  
    // Returns true of given node is leaf 
    private boolean isLeaf(int pos) {
        if (pos >= (size / 2) && pos <= size) { 
            return true; 
        } 
        return false; 
    } 
  
    private void swap(int fpos, int spos) {
        Unihan temp;
        temp = heap.get(fpos);
        heap.set(fpos, heap.get(spos));
        heap.set(spos, temp);
    }

    /**
     * It max heapifies the given subtree recursively
     * It assumes that the left and right subtrees are already heapified, we only need to fix the root.
     * @param position: Position to be checked
     */
    private void maxHeapify(int position) {
        if (isLeaf(position)) return;
        if (heap.get(position).getScore() < heap.get(leftChild(position)).getScore() ||
                heap.get(position).getScore() < heap.get(rightChild(position)).getScore()) {
            if (heap.get(leftChild(position)).getScore() > heap.get(rightChild(position)).getScore()) {
                swap(position, leftChild(position));
                maxHeapify(leftChild(position));
            } 
            else { 
                swap(position, rightChild(position));
                maxHeapify(rightChild(position));
            } 
        } 
    }

    /**
     * Inserts a new element to max heap
     * @param element: element to insert
     */
    public void insert(Unihan element) {
        this.heap.add(++size, element);
        int current = size; // Traverse up and fix violated property
        while (heap.get(current).getScore() > heap.get(parent(current)).getScore()) {
            swap(current, parent(current)); 
            current = parent(current); 
        }
    }

    /**
     * Removes an element from MaxHeap
     * @return the element removed
     */
    public Unihan extractMax() {
        Unihan popped = this.heap.get(1);
        this.heap.set(1, heap.get(this.size--));
        maxHeapify(1);
        return popped; 
    } 
}
