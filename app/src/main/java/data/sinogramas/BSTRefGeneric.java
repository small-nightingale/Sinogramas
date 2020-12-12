/*
 * Undocumented methods are getters and setters
 */
package data.sinogramas;
import java.util.LinkedList;
/**
 * @author dequi
 */
public class BSTRefGeneric<T extends Comparable<T>> {
    private LinkedList<T> list;
    private NodeGeneric<T> root;
    QueueDynamicArrayGeneric queue = new QueueDynamicArrayGeneric<T>();

    /**
     * Class constructor
     */
    public BSTRefGeneric() {
        this.list = new LinkedList<>();
        this.root = null;
    }

    public LinkedList<T> getList() {
        return this.list;
    }
    public void setList(LinkedList<T> list) {
        this.list = list;
    }
    public NodeGeneric<T> getRoot() {
        return this.root;
    }
    public void setRoot(NodeGeneric<T> root) {
        this.root = root;
    }
    public QueueDynamicArrayGeneric getQueue() {
        return this.queue;
    }
    public void setQueue(QueueDynamicArrayGeneric queue) {
        this.queue = queue;
    }

    private NodeGeneric<T> insert(T data, NodeGeneric<T> p) {
        if(p == null)
            p = new NodeGeneric<>(data);
        else
            if(((T) p.getData()).compareTo(data) > 0)
                p.setPrev(insert(data, p.getPrev()));
            else
                if(((T) p.getData()).compareTo(data) < 0)
                    p.setNext(insert(data, p.getNext()));
                else {
                    System.out.println("Not inserted");
                    return p;
                }
        return p;
    }
    public void insert(T data) {
        this.root = insert(data, this.root);
    }

    private NodeGeneric<T> remove(T data, NodeGeneric<T> p){
        if(p != null)
            if(p.getData().compareTo(data) > 0)
                p.setPrev(remove(data, p.getPrev()));
            else
                if(p.getData().compareTo(data) < 0)
                    p.setNext(remove(data, p.getNext()));
                else
                    if(p.getPrev() == null && p.getNext() == null)
                        p = null;
                    else
                        if(p.getPrev() == null)
                            p = p.getNext();
                        else
                            if(p.getNext() == null)
                                p = p.getPrev();
                            else {
                                NodeGeneric<T> t = findMin(p.getNext());
                                p.setData(t.getData());
                                p.setNext(remove(p.getData(), p.getNext()));
                            }
        else
            System.out.println("Item not in tree and not removed");
        return p;
    }
    public void remove(T data){
        this.root = remove(data, this.root);
    }

    private NodeGeneric<T> findMin(NodeGeneric<T> p) {
        if(p != null)
            while(p.getPrev() != null)
                p = p.getPrev();
        return p;
    }

    public void traverseBST() {
        System.out.print("The tree is:");
        if(root != null)
            traverse(root);
        else
            System.out.print(" " + "Empty");
        System.out.println();
    }
    private void traverse(NodeGeneric<T> ptr) {
        if(ptr.getPrev() != null)
            traverse(ptr.getPrev());
        System.out.print(" " + ptr.getData());
        if(ptr.getNext() != null)
            traverse(ptr.getNext());
    }
    
    public boolean contains(NodeGeneric<T> p, T data) {
        if (data.compareTo(p.getData()) == 0)
            return true;
        else if (data.compareTo(p.getData()) > 0)
            contains(p.getNext(), data);
        else
            contains(p.getPrev(), data);
        return false;
    }

    public void levelOrder(NodeGeneric<T> root) {
        LinkedList<NodeGeneric<T>> listOrder = new LinkedList<>();
        if (root!=null) {
            listOrder.offer(root);
            while(list.size()>0) {
                NodeGeneric<T> node = listOrder.poll();
                System.out.println(node.getData()+" ");
                if(node.getPrev() != null) listOrder.offer(node.getPrev());
                if(node.getNext() != null) listOrder.offer(node.getNext());
            }
        }
    }

    public LinkedList<T> inOrderToQueue(NodeGeneric<T> node) {
        if (node == null) return this.list;

        inOrderToQueue(node.getPrev()); /* first recur on left child */
        this.list.offer(node.getData()); /* then enqueue the data of node */
        inOrderToQueue(node.getNext()); /* now recur on right child */

        return this.list;
    }


}
