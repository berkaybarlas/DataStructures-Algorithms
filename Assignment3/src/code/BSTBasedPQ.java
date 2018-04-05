package code;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

    public BSTBasedPQ() {
        super();
    }
    @Override
    public void insert(Key k, Value v) {
        put(k,v);
    }

    @Override
    public Entry<Key, Value> pop() {
        if(isEmpty()) return null;
        BinaryTreeNode<Key, Value> node = (BinaryTreeNode<Key, Value>) top();
        remove(node.getKey());
        return node;
    }

    @Override
    public Entry<Key, Value> top() {
        if(isEmpty()) return null;
        BinaryTreeNode<Key, Value> node = super.getRoot();
        while(super.getLeftChild(node)!=null ){
            node = super.getLeftChild(node);
        }
        return node;
    }

    @Override
    public Key replaceKey(Entry<Key, Value> entry, Key k) {
        BinaryTreeNode<Key, Value> node =  getNode(entry.getKey());
        Value val = node.getValue();
        Key oldKey = node.getKey();
        remove(node.getKey());
        insert(k,val);
        return oldKey;
    }

    @Override
    public Key replaceKey(Value v, Key k) {
        BinaryTreeNode<Key, Value> node = null;
        for(BinaryTreeNode<Key,Value> n: getNodesInOrder()){
            if(n.getValue().equals(v)) {
                node = n;
                break;
            }
        }
        if(node!=null) {
            Key oldKey = node.getKey();
            remove(node.getKey());
            insert(k, v);
            return oldKey;
        }
        return null;
    }

    @Override
    public Value replaceValue(Entry<Key, Value> entry, Value v) {
        BinaryTreeNode<Key, Value> node =  getNode(entry.getKey());
        Value result = node.getValue();
        node.setValue(v);
        return result;
    }

}
