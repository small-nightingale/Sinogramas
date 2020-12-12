package data.sinogramas;
import java.util.LinkedList;
/**
 * @author dequi
 */
public class AVLTreeGeneric<T extends Comparable<T>> {
    private NodeGeneric<T> root; //Root of the tree

    /**
     * Constructor
     */
    public AVLTreeGeneric() {
        super();
    }

    public NodeGeneric<T> getRoot() {
        return this.root;
    }
    public void setRoot(NodeGeneric<T> r) {
        this.root = r;
    }

    public NodeGeneric<T> insert(NodeGeneric<T> node, T key) {
        /* 1.  Perform the normal BST insertion */
        if (node == null) return (new NodeGeneric<T>(key));
        if (node.getData().compareTo(key) > 0) node.setPrev(insert(node.getPrev(), key));
        else if (node.getData().compareTo(key) < 0) node.setNext(insert(node.getNext(), key));
        else return node; // Duplicate keys not allowed

        /* 2. Update height of this ancestor node */
        node.setHeight(1 + max(height(node.getPrev()), height(node.getNext())));
        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && node.getPrev().getData().compareTo(key) > 0) return rightRotate(node);
        // Right Right Case
        if (balance < -1 && node.getNext().getData().compareTo(key) < 0) return leftRotate(node);
        // Left Right Case
        if (balance > 1 && node.getPrev().getData().compareTo(key) < 0) {
            node.setPrev(leftRotate(node.getPrev()));
            return rightRotate(node);
        }
        // Right Left Case
        if (balance < -1 && node.getNext().getData().compareTo(key) > 0) {
            node.setNext(rightRotate(node.getNext()));
            return leftRotate(node);
        }
        /* return the (unchanged) node pointer */
        return node;
    }

    /**
     * Gets the height of the tree
     * @param node: Node to check
     * @return height of the tree
     */
    public int height(NodeGeneric<T> node) {
        if (node == null) return 0;
        return node.getHeight();
    }

    /**
     * Right rotate subtree rooted
     * @param currentNode: Node to rotate
     * @return the new root
     */
    public NodeGeneric<T> rightRotate(NodeGeneric<T> currentNode) {
        NodeGeneric<T> prevNode = currentNode.getPrev();
        NodeGeneric<T> nextNode = prevNode.getNext();
        // Perform rotation 
        prevNode.setNext(currentNode);
        currentNode.setPrev(nextNode);
        // Update heights 
        currentNode.setHeight(max(height(currentNode.getPrev()), height(currentNode.getNext())) + 1);
        prevNode.setHeight(max(height(prevNode.getPrev()), height(prevNode.getNext())) + 1);
        // Return new root 
        return prevNode;
    } 

    /**
     * left rotate subtree rooted
     * @param currentNode: node to rotate
     * @return the root rotated node
     */
    public NodeGeneric<T> leftRotate(NodeGeneric<T> currentNode) {
        NodeGeneric<T> nextNode = currentNode.getNext();
        NodeGeneric<T> prevNode = nextNode.getPrev();
        // Perform rotation 
        nextNode.setPrev(currentNode);
        currentNode.setNext(prevNode);
        //  Update heights 
        currentNode.setHeight(max(height(currentNode.getPrev()), height(currentNode.getNext())) + 1);
        nextNode.setHeight(max(height(nextNode.getPrev()), height(nextNode.getNext())) + 1);
        // Return new root 
        return nextNode;
    } 

    /**
     * Balance factor of node nodeToBalance
     * @param nodeToBalance
     * @return difference between the nodes
     */
    public int getBalance(NodeGeneric<T> nodeToBalance) {
        if (nodeToBalance == null) return 0;
        return height(nodeToBalance.getPrev()) - height(nodeToBalance.getNext());
    } 

    public int size(NodeGeneric<Unihan> node) {
        if (node == null) return 0;
        else return(size(node.getPrev()) + 1 + size(node.getNext()));
    }
    public NodeGeneric<Unihan> find(NodeGeneric<Unihan> R, char c) {
        // Base Cases: root is null or key is present at root 
        if (R==null || R.getData().getCharacter() == c) return R;
        // Key is greater than root's key 
        if (R.getData().getCharacter() < c) return find(R.getNext(), c);
        // Key is smaller than root's key 
        return find(R.getPrev(), c); 
    }
    public NodeGeneric<T> minValueNode(NodeGeneric<T> node) {
        NodeGeneric<T> current = node;
        /* loop down to find the leftmost leaf */
        while (current.getPrev() != null) {
            current = current.getPrev();
        }
        return current;  
    }
    public NodeGeneric<T> deleteNode(NodeGeneric<T> root, T key) {
        
        NodeGeneric<T> n = new NodeGeneric<>(key);
        // STEP 1: PERFORM STANDARD BST DELETE  
        if (root == null)  
            return root;  
  
        // If the key to be deleted is smaller than  
        // the root's key, then it lies in left subtree  
        if (root.getData().compareTo(n.getData()) > 0)  
            root.setPrev(deleteNode(root.getPrev(), key));  
  
        // If the key to be deleted is greater than the  
        // root's key, then it lies in right subtree  
        else if (root.getData().compareTo(n.getData()) < 0)  
            root.setNext(deleteNode(root.getNext(), key));   
  
        // if key is same as root's key, then this is the node  
        // to be deleted  
        else
        {  
  
            // node with only one child or no child  
            if ((root.getPrev() == null) || (root.getNext() == null))  
            {  
                NodeGeneric<T> temp = null;  
                if (temp == root.getPrev())  
                    temp = root.getNext();  
                else
                    temp = root.getPrev();  
  
                // No child case  
                if (temp == null)  
                {  
                    temp = root;  
                    root = null;  
                }  
                else // One child case  
                    root = temp; // Copy the contents of  
                                // the non-empty child  
            }  
            else
            {  
  
                // node with two children: Get the inorder  
                // successor (smallest in the right subtree)  
                NodeGeneric<T> temp = minValueNode(root.getNext());  
  
                // Copy the inorder successor's data to this node  
                root.setData(temp.getData());  
  
                // Delete the inorder successor  
                root.setNext(deleteNode(root.getNext(), temp.getData()));  
            }  
        }  
            // If the tree had only one node then return  
        if (root == null)  
            return root;  
  
        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE  
        root.setHeight(max(height(root.getPrev()), height(root.getNext())) + 1);  
  
        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether  
        // this node became unbalanced)  
        int balance = getBalance(root);  
  
        // If this node becomes unbalanced, then there are 4 cases  
        // Left Left Case  
        if (balance > 1 && getBalance(root.getPrev()) >= 0)  
            return rightRotate(root);  
  
        // Left Right Case  
        if (balance > 1 && getBalance(root.getPrev()) < 0)  
        {  
            root.setPrev(leftRotate(root.getPrev()));  
            return rightRotate(root);  
        }  
  
        // Right Right Case  
        if (balance < -1 && getBalance(root.getNext()) <= 0)  
            return leftRotate(root);  
  
        // Right Left Case  
        if (balance < -1 && getBalance(root.getNext()) > 0)  
        {  
            root.setNext(rightRotate(root.getNext()));  
            return leftRotate(root);  
        }  
  
        return root;  
    }
    public void preOrder(NodeGeneric<T> node) {
        if (node != null) {
            System.out.print(node.getData() + " ");  
            preOrder(node.getPrev());  
            preOrder(node.getNext());  
        }  
    }
    public void inOrder(NodeGeneric<T> node) {
        if (node != null) {
            inOrder(node.getPrev()); 
            System.out.print(node.getData() + " ");  
            inOrder(node.getNext());  
        }  
    }
    public void levelOrder(NodeGeneric<T> root){
        QueueDynamicArrayGeneric<NodeGeneric> queue = new QueueDynamicArrayGeneric<>();
        if (root != null) {
            queue.enqueue(root);
            while (!queue.empty()) {
                NodeGeneric t = queue.dequeue();
                System.out.print(t.getData() + " ");
                if(t.getPrev() != null) queue.enqueue(t.getPrev());
                if(t.getNext() != null) queue.enqueue(t.getNext());
            }
        }
    }
    public void level(NodeGeneric<T> root) {
        LinkedList<NodeGeneric<T>> list = new LinkedList<>();
        if (root!=null) {
            list.offer(root);
            while (list.size()>0) {
                NodeGeneric<T> node = list.poll();
                System.out.println(node.getData()+ " ");
                if (node.getPrev()!=null) list.offer(node.getPrev());
                if (node.getNext()!=null) list.offer(node.getNext());
            }
        }
    }

    /**
     * Compares to integers and returns the greatest
     * @param a: first integer
     * @param b: second integer
     * @return greatest integer between the lastest
     */
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

}
