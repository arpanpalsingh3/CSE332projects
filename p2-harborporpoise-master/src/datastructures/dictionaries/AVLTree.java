package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.worklists.ArrayStack;

/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 *    children array or left and right fields in AVLNode.  This will 
 *    instead mask the super-class fields (i.e., the resulting node 
 *    would actually have multiple copies of the node fields, with 
 *    code accessing one pair or the other depending on the type of 
 *    the references used to access the instance).  Such masking will 
 *    lead to highly perplexing and erroneous behavior. Instead, 
 *    continue using the existing BSTNode children array.
 * 4. If this class has redundant methods, your score will be heavily
 *    penalized.
 * 5. Cast children array to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so we recommend you make
 *    private methods that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    // inner class for AVLNode
    public class AVLNode extends BSTNode {

        public int height;

        public AVLNode(K key, V value) {
            super(key, value);
            height = 0;
        }
    }

    // creates an empty AVLTree
    public AVLTree() {
        super();
    }
    // insert nodes into the AVL tree but with all AVL balance conditions

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        AVLNode current = findInsert(key);
        V oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    public AVLNode findInsert(K key) {
        // array stack path to keep track of where I've traveled in tree
        ArrayStack<AVLNode> path = new ArrayStack<>();
        // if the tree is empty, put whatever you have at the top
        if (this.root == null) {
            this.root = new AVLNode(key, null);
            this.size++;
            return (AVLNode) this.root;
        }
        // mostly copy paste from BST
        // current is top of the tree
        AVLNode current = (AVLNode) this.root;
        int direction = 0;
        int child = -1;
        // to keep track of my nodes that break some AVL property
        AVLNode badParent = null;

        // travel through the tree to get to the bottom in the proper direction
        while (current != null) {
            // keep track of my path that I'm going through
            path.add(current);
            direction = Integer.signum(key.compareTo(current.key));
            if (direction == 0) { // means we hve found the key
                return current;
            }
            // use direction to decide which child to go to
            // if direction is -1, go to left, if 0, go to right
            child = Integer.signum(direction + 1);
            current = (AVLNode) current.children[child];
        }
        // current now represents the new node we want to add
        current = new AVLNode(key, null);
        // increment size, since something new is being added
        this.size++;
        // the parent of current is the LAST thing we added to the path
        AVLNode parent = path.peek();
        // add current to path as well
        path.add(current);
        // add current as a child of parent
        parent.children[child] = current;
        // if the other node of this parent isn't null
        if (parent.children[1 - child] != null) {
            // check the parents height
            parent.height += direction;
        } else { // if the other node IS null
            badParent = this.updateHeight(path);
        }
        if (badParent != null) { // this means that our imbalance is somewhere other than the root
            // so we fix the sub tree with rotations where the problem is
            int subDirection = Integer.signum(key.compareTo(badParent.key));
            int subTree = Integer.signum(subDirection + 1);
            badParent.children[subTree] = this.rotationFix(path);
        }
        return current;

    }


    private AVLNode updateHeight(ArrayStack<AVLNode> path) {
        AVLNode parent;
        AVLNode child = null;
        AVLNode grandchild;
        while(path.size() >= 2) {
            // extract from the path
            grandchild = child;
            child = path.next();
            parent = path.peek();
            if (child == parent.children[1]) {
                parent.height++;
            } else {
                parent.height--;
            }

            if (Math.abs(parent.height) == 2) {
                // when at root
                if (path.size() == 1) {
                    // make a new path
                    this.newRotationPath(path, parent, child, grandchild);
                    // make a rotation
                    this.root = this.rotationFix(path);
                    return null;
                } else {
                    path.next();
                    AVLNode parentOfProblem = path.peek();
                    this.newRotationPath(path, parent, child, grandchild);
                    return parentOfProblem;
                }
            }
        }
        return null;
    }

    // helper method for creating new ArrayStack
    private void newRotationPath(ArrayStack<AVLNode> path, AVLNode parent, AVLNode child, AVLNode grandchild) {
        path.clear();
        path.add(parent);
        path.add(child);
        path.add(grandchild);
    }

    private AVLNode rotationFix(ArrayStack<AVLNode> path) {
        // extract the information from my ArrayStack Path
        AVLNode grandchild = path.next();
        AVLNode child = path.next();
        AVLNode parent = path.next();
        K first = parent.key;
        K second = child.key;
        K third = grandchild.key;
        // get direction by comparing parent and grandchild
        int direction = Integer.signum(third.compareTo(first));
        int side = Integer.signum(direction + 1);
        AVLNode leftover;

        // checking cases
        if (check(first, third, second) || check(second, third, first)) {
            // rotating
            parent.children[side] = grandchild;
            leftover = (AVLNode)grandchild.children[side];
            child.children[1 - side] = leftover;
            grandchild.children[side] = child;
            AVLNode temp = child;
            child = grandchild;
            grandchild = temp;
            child.height += direction;
            grandchild.height += direction;
        }

        leftover = (AVLNode)child.children[1 - side];
        parent.children[side] = leftover;
        child.children[1 - side] = parent;
        child.height += (direction * -1);
        parent.height += (direction * -2);
        return child;
    }

    // helper method for checking cases
    private boolean check(K side1, K middle, K side2) {
        return side2.compareTo(middle) < 0 && middle.compareTo(side1) < 0;
    }

}









