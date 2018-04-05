package code;

import given.Entry;
import given.iAdaptablePriorityQueue;
import given.iBinarySearchTree;

import java.util.ArrayList;
import java.util.Comparator;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 *
 */
//  ﬁrst implement the array based binary tree functionality
public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

    private ArrayList<Entry<Key, Value>> heap = new ArrayList<>();
    private Comparator<Key> comp;

    public ArrayBasedHeap() { }

    private int parent(int j) { return (j - 1) / 2; }

    private int rightChild(int j) { return 2 * j + 2; }

    private int leftChild(int j) { return 2 * j + 1; }

    private boolean hasLeft(int j) { return leftChild(j) < heap.size(); }

    private boolean hasRight(int j) { return rightChild(j) < heap.size(); }

    private void swap(int i, int j) {
        Entry<Key, Value> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private void upHeap(int j) {
        while (j > 0) {
            int parent = parent(j);
            if (comp.compare(heap.get(j).getKey(), heap.get(parent).getKey()) >= 0) break;
            swap(parent, j);
            j = parent;
        }
    }

    private void downHeap(int j) {
        while (hasLeft(j)) {
            int leftChildIndex = leftChild(j);
            int smallChildIndex = leftChildIndex;
            if (hasRight(j)) {
                int rightChildIndex = rightChild(j);
                if (comp.compare(heap.get(leftChildIndex).getKey(), heap.get(rightChildIndex).getKey()) > 0)
                    smallChildIndex = rightChildIndex;
            }
            if (comp.compare(heap.get(j).getKey(), heap.get(smallChildIndex).getKey()) <= 0) break;
            swap(j, smallChildIndex);
            j = smallChildIndex;
        }
    }
    // remove entry at given index
    public Entry<Key,Value> removeAtIndex(int index){
        if(index<0 || index>=size()){return null;}
        Entry<Key,Value> result = heap.get(index);
        swap(index, heap.size() - 1);
        heap.remove(heap.size() - 1);
        if (index < heap.size()) {
            upHeap(index);
        }
        downHeap(index);

        return result;
    }

    //****
    //find index of k if not exits return null
    //can be a recursive algorithm
    private int heapSearch(int p,Key k){
        if (isEmpty())
            return -1;
        int compare = comp.compare(heap.get(p).getKey(), k);
        if(compare==0)return p;
        if(compare>0){return -1;}
        else{
            if(hasLeft(p)) {
                int leftPart = heapSearch(leftChild(p), k);
                if (leftPart > 0) {
                    return leftPart;
                }
            }
            if(hasRight(p)){
                int rightPart = heapSearch(rightChild(p), k);
                if(rightPart>0) {
                    return rightPart;
                }
            }
        }

        return -1;
    }
    private int indexOfKey(Key k) {
        /*if (isEmpty())
            return -1;
        for (int i = 0; i < size(); i++) {
            if (comp.compare(heap.get(i).getKey(), k) == 0) return i;
        }
        */
        return heapSearch(0,k);
    }

    //find index of given value
    private int indexOfValue(Value v) {
        if (isEmpty())
            return -1;
        for (int i = 0; i < size(); i++) {
            if (heap.get(i).getValue().equals(v)) return i;
        }
        return -1;
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.size() == 0;
    }

    @Override
    public void setComparator(Comparator<Key> C) {
        comp = C;
    }

    @Override
    public void insert(Key k, Value v) {
        //check key maybe
        Entry<Key, Value> newItem = new Entry<>(k, v);
        heap.add(newItem);
        upHeap(heap.size() - 1);
    }

    @Override
    public Entry<Key, Value> pop() {
        if (heap.isEmpty()) return null;
        Entry<Key, Value> result = heap.get(0);
        removeAtIndex(0);
        return result;
    }

    @Override
    public Entry<Key, Value> top() {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }


    @Override
    public Value remove(Key k) {

        int indexK = indexOfKey(k);
        if (indexK == -1) return null;
        return removeAtIndex(indexK).getValue();
    }

    // Return null if the entry does not exists
    //remove Entry and insert it with new key
    @Override
    public Key replaceKey(Entry<Key, Value> entry, Key k) {
        int indexK = indexOfKey(entry.getKey());
        if (indexK == -1 || !heap.get(indexK).equals(entry) ) return null;
        Key oldKey = entry.getKey();
        removeAtIndex(indexK);
        insert(k, entry.getValue());
        return oldKey;
    }

    // Return null if the entry with the value does not exists
    @Override
    public Key replaceKey(Value v, Key k) {
        int indexV = indexOfValue(v);
        if (indexV == -1) return null;

        Key oldKey = heap.get(indexV).getKey();
        removeAtIndex(indexV);
        insert(k, v);
        return oldKey;
    }

    // Return null if the entry does not exists
    // Make sure to match both the key and the value before replacing anything!
    @Override
    public Value replaceValue(Entry<Key, Value> entry, Value v) {
        int indexK = indexOfKey(entry.getKey());
        if (indexK == -1 || comp.compare(heap.get(indexK).getKey(), entry.getKey()) != 0) return null;

        if (!heap.get(indexK).getValue().equals(entry.getValue()))
            return null;

        Value oldValue = entry.getValue();
        heap.get(indexK).setValue(v);
        return oldValue;
    }
}
