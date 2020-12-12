/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.sinogramas;

/**
 *
 * @author dequi
 */
public class HeapSort<T extends Comparable<T>> {

    private void swapElements(T[] a, int i, int j) {
        T temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    private void moveDown(T[] array, int parent, int size) {
        boolean flag = false;
        T largest = null;
        int child = 2*parent+1;
        T temp = array[parent];
        while(child < size && !flag) {
            largest = array[child];
            if(child+1 < size && array[child+1].compareTo(array[child]) > 0)
                largest = array[++child];
                if(largest.compareTo(temp) > 0) {
                    array[parent] = largest;
                    parent = child;
                }
                else
                    flag = true;
                    child = 2*parent+1;
                }
            array[parent] = temp;
    }
    private void makeHeap(T[] array, int n) {
        for (int i = n/2-1; i>=0; i--)
            moveDown(array, i, n);
        }
    public void heapSort(T[] array, int n) {
        makeHeap(array, n);
        for(int i=n-1; i>0; i--) {
            swapElements(array, 0, i);
            moveDown(array, 0, i);
        }
    }
}
