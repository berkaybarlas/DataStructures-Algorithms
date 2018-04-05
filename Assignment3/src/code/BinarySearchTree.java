package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import given.iMap;
import given.Entry;
import given.iBinarySearchTree;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {
    private BinaryTreeNode<Key,Value> root;
    private int size = 0;
    private Comparator<Key> comp;

    public BinarySearchTree(Comparator<Key> c){
        root = new BinaryTreeNode<Key,Value>(null,null,null,null,null);
        comp = c;
    }

    public BinarySearchTree(){
        //this(new DefaultComparator<Key>());
        root = new BinaryTreeNode<Key,Value>(null,null,null,null,null);
    }

    public BinaryTreeNode<Key,Value> treeSearch(BinaryTreeNode<Key,Value> node, Key k ){
        if(isExternal(node)){ return node; }
        int compare = comp.compare(k,node.getKey());
        if(compare == 0){
            return node;
        }else if(compare < 0){
            if(node.getLeftNode()!=null)
                return treeSearch(node.getLeftNode(),k);
            else
                return node;
        }else{
            if(node.getRightNode()!=null)
                return treeSearch(node.getRightNode(),k);
            else
                return node;
        }
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size==0; }

    @Override
    public Value get(Key k) {
        if(comp.compare(k,treeSearch(root,k).getKey()) == 0)
            return treeSearch(root,k).getValue();
        return null;
    }

    @Override
    public Value put(Key k, Value v) {
        if(isEmpty()){
            root.setKey(k);
            root.setValue(v);
            size++;
            return null;
        }
        BinaryTreeNode<Key,Value> pNode = treeSearch(root,k);
        int compare = comp.compare(k,pNode.getKey());

        if(compare==0){
            Value temp = pNode.getValue();
            pNode.setValue(v);
            return temp;
        }else{
            if(compare<0){
                pNode.setLeftNode(new BinaryTreeNode<Key,Value>(k,v,null,null,pNode));
                size++;
                return null;
            }else{
                pNode.setRightNode(new BinaryTreeNode<Key,Value>(k,v,null,null,pNode));
                size++;
                return null;
            }
        }

    }

    @Override
    public Value remove(Key k) {

        boolean replaced = false ;
        BinaryTreeNode<Key, Value> node = treeSearch(root,k);
        if(comp.compare(node.getKey(),k)==0){
            Value val = node.getValue();
            BinaryTreeNode<Key, Value> parent = node.getParentNode();
            BinaryTreeNode<Key, Value> leftNode = node.getLeftNode();
            BinaryTreeNode<Key, Value> rightNode = node.getRightNode();
            if(leftNode!=null && rightNode==null){
                if(parent!=null) {
                    if (isLeftChild(node))
                        parent.setLeftNode(leftNode);
                    else
                        parent.setRightNode(leftNode);
                    leftNode.setParentNode(parent);
                }else{
                    setRoot(leftNode);
                }
            }else if(leftNode==null && rightNode!=null){
                if(parent!=null) {
                    if (isLeftChild(node))
                        parent.setLeftNode(rightNode);
                    else if(isRightChild(node))
                        parent.setRightNode(rightNode);
                    rightNode.setParentNode(parent);
                }else{
                    setRoot(rightNode);
                }
            }else if(leftNode!=null && rightNode!=null){

                BinaryTreeNode<Key, Value> rightMost = leftNode.getRightNode();


                if(rightMost!=null){
                    while(rightMost.getRightNode()!=null){
                        rightMost = rightMost.getRightNode();
                    }
                    Key oldKey = rightMost.getKey();
                    Value oldValue = remove(rightMost.getKey());
                    replaced = true;

                    node.setKey(oldKey);
                    node.setValue(oldValue);

                }else{
                    if(parent!=null) {
                        if (isLeftChild(node))
                            parent.setLeftNode(leftNode);
                        else
                            parent.setRightNode(leftNode);
                        leftNode.setParentNode(parent);
                    }else
                        setRoot(leftNode);
                    rightNode.setParentNode(leftNode);
                    leftNode.setRightNode(rightNode);
                }

            }else{
                if(parent!=null) {
                    if (isLeftChild(node))
                        node.getParentNode().setLeftNode(null);
                    else
                        node.getParentNode().setRightNode(null);
                }
            }


            if(!replaced) {
                size--;
                node.setParentNode(null);
                node.setLeftNode(null);
                node.setRightNode(null);
                node.setValue(null);
                node.setKey(null);
            }
            return val;
        }
        return null;
    }

    @Override
    public Iterable<Key> keySet() {
        ArrayList<Key> iter = new ArrayList<>(size());
        for(BinaryTreeNode<Key, Value> node : getNodesInOrder())
            iter.add(node.getKey());
        return iter;
    }
    private int maxDepth(BinaryTreeNode<Key, Value> node){
        if(node == null)
            return 0;
        else{
            int leftSize = maxDepth(node.getLeftNode());;
            int rightSize = maxDepth(node.getRightNode());
            return 1 + Math.max(leftSize,rightSize);
        }

    }
    @Override
    public int height() {
        return maxDepth(getRoot());
    }

    public void setRoot(BinaryTreeNode<Key, Value> newNode){
        if(newNode != null) {
            newNode.setParentNode(null);
            root = newNode;
        }else{
            root.setValue(null);
            root.setKey(null);
            root.setRightNode(null);
            root.setLeftNode(null);
            root.setRightNode(null);
        }
    }
    @Override
    public BinaryTreeNode<Key, Value> getRoot() {
        return root;
    }

    @Override
    public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
        return node.getParentNode();
    }

    @Override
    public boolean isInternal(BinaryTreeNode<Key, Value> node) {
        if(node == null){ return false; }
        if(node.getKey()!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean isExternal(BinaryTreeNode<Key, Value> node) {
        if(node == null){ return true; }
        if(node.getKey()==null)
            return true;
        return false;
    }
    @Override
    public boolean isRoot(BinaryTreeNode<Key, Value> node) {
        return node==root;
        //return node.getParentNode()==null;
    }

    @Override
    public BinaryTreeNode<Key, Value> getNode(Key k) {
        BinaryTreeNode<Key, Value> tempNode = treeSearch(root,k);
        if(comp.compare(k,tempNode.getKey()) == 0)
            return tempNode;
        return null;
    }

    @Override
    public Value getValue(Key k) {
        if(getNode(k) != null)
            return getNode(k).getValue();
        return null;
    }

    @Override
    public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
        return node.getLeftNode();
    }

    @Override
    public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
        return node.getRightNode();
    }
    @Override
    public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
        if(isRoot(node)){
            return null;
        }else if(isLeftChild(node)){
            BinaryTreeNode<Key, Value> rightSibling = node.getParentNode().getRightNode();
            return rightSibling;
        }else if(isRightChild(node)){
            BinaryTreeNode<Key, Value> leftSibling = node.getParentNode().getLeftNode();
            return leftSibling;
        }
        return null;
    }

    @Override
    public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
        BinaryTreeNode<Key, Value> parentNode = node.getParentNode();
        if(parentNode==null) return false;
        return node.equals(parentNode.getLeftNode());
    }
    @Override
    public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
        BinaryTreeNode<Key, Value> parentNode = node.getParentNode();
        if(parentNode==null) return false;
        return node.equals(parentNode.getRightNode());
    }


    private void inOrderSubtree(BinaryTreeNode<Key, Value> p,List<BinaryTreeNode<Key, Value>> snapList ){
        if(p.getLeftNode()!=null){
            inOrderSubtree(p.getLeftNode(),snapList);
        }
        snapList.add(p);
        if(p.getRightNode() != null){
            inOrderSubtree(p.getRightNode(),snapList);
        }
    }
    @Override
    public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
        List<BinaryTreeNode<Key, Value>> inOrderLists = new ArrayList<>();
        if(!isEmpty()){
            inOrderSubtree(getRoot(),inOrderLists);
        }
        return inOrderLists;
    }

    @Override
    public void setComparator(Comparator<Key> C) {
        comp = C;
    }

    @Override
    public Comparator<Key> getComparator() {
        return comp;
    }
//********************
    // Returns the node with the smallest key that is larger than or equal to k
    @Override
    public BinaryTreeNode<Key, Value> ceiling(Key k) {
        checkKey(k);
        BinaryTreeNode<Key, Value> p = treeSearch(getRoot(),k);
        if(p != null) {
            int compare = comp.compare(k, p.getKey());
            if (compare == 0) return p;
            while (!isRoot(p)) {
                BinaryTreeNode<Key, Value> parent = p.getParentNode();
                if (compare < 0) {
                    return p;
                } else {
                    p = parent;
                    compare = comp.compare(k, p.getKey());
                }
            }
        }
        return  null;
    }
//******************
    // Returns the node with the largest key that is smaller than or equal to k
    @Override
    public BinaryTreeNode<Key, Value> floor(Key k) {
        checkKey(k);
        BinaryTreeNode<Key, Value> p = treeSearch(getRoot(),k);
        int compare = comp.compare(k,p.getKey());
        if(compare==0) return p;//exact macth
        while(!isRoot(p)) {
                BinaryTreeNode<Key, Value> parent = p.getParentNode();
                if(compare>0){
                    return p;
                } else {
                    p = parent;
                    compare = comp.compare(k,p.getKey());
                }
        }
        return null;
    }
//use in the things that gives k
    protected boolean checkKey(Key key) throws IllegalArgumentException {
        try {
             return (comp.compare(key,key) == 0); // see if key can be compared to itself
             } catch (ClassCastException e) {
            throw new IllegalArgumentException("Incompatible key");
             }
         }

}
