import java.util.Collection;

/**
 * Your implementation of an AVL.
 *
 * @author Jadon Co
 * @version 1.0
 * @userid jco9
 * @GTID 903725118
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data collection is null");
        }

        for (T element : data) {
            if (element == null) {
                throw new java.lang.IllegalArgumentException("A value in the data collection is null");
            }
            add(element);

        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * 
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data inputted is invalid or null.");
        } else if (root == null) {
            root = new AVLNode<>(data);
            updateHAndB(root);
            size++;
            return;
        } else {
            root = addHelp(root, data);
        }
    }

    /**
     *
     * @param node - the current node that we are on recursively
     * @param data - the data attempting to be added to the AVL Tree
     * @return - returns the node in recursive order
     */
    private AVLNode<T> addHelp(AVLNode<T> node, T data) {
        if (data.compareTo(node.getData()) == 0) {
            return node;
        } else if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                AVLNode<T> newNode = new AVLNode<>(data);
                updateHAndB(newNode);
                node.setLeft(newNode);
                size++;
            } else {
                AVLNode<T> newChild = addHelp(node.getLeft(), data);
                node.setLeft(newChild);
            }
        } else {
            if (node.getRight() == null) {
                AVLNode<T> newNode = new AVLNode<>(data);
                updateHAndB(newNode);
                node.setRight(newNode);
                size++;
            } else {
                AVLNode<T> newChild = addHelp(node.getRight(),data);
                node.setRight(newChild);
            }
        }

        updateHAndB(node);
        return balance(node);

    }




    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("the data passed in is null");
        }
        AVLNode<T> res = new AVLNode<>(null);
        root = rHelper(root, data, res);
        return res.getData();
    }

    /**
     *
     * @param node - the node we are on recursively
     * @param data - the data we are attempting to remove from the AVL Tree
     * @param r - a fake node that keeps the data of the removed node
     * @return returns the node recursively
     */
    private AVLNode<T> rHelper(AVLNode<T> node, T data, AVLNode<T> r) {
        if (node == null) {
            throw new java.util.NoSuchElementException("The data is not in the BST");
        } else if (data.compareTo(node.getData()) == 0) {
            r.setData(node.getData());
            if (node.getLeft() == null && node.getRight() == null) {
                size--;
                updateHAndB(node);
                return null;
            } else if (node.getLeft() == null && node.getRight() != null) {
                size--;
                updateHAndB(node);
                return node.getRight();
            } else if (node.getLeft() != null && node.getRight() == null) {
                size--;
                updateHAndB(node);
                return node.getLeft();
            } else {
                AVLNode<T> predecessor = node.getLeft();
                T temp = node.getData();
                while (predecessor.getRight() != null) {
                    predecessor = predecessor.getRight();
                }

                node.setLeft(rHelper(node.getLeft(), predecessor.getData(), r));
                node.setData(predecessor.getData());
                r.setData(temp);
                updateHAndB(node);
                return node;
            }

        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(rHelper(node.getLeft(), data, r));
        } else {
            node.setRight(rHelper(node.getRight(), data, r));
        }
        updateHAndB(node);
        return balance(node);


    }

    /**
     * a helper method that helps rebalance the AVL Tree
     * @param node - takes in a node that starts the rebalance and checks the BF in order to know when to rotate
     * @return the node recursively after its rotated
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (Math.abs(node.getBalanceFactor()) > 1) {
            if (node.getBalanceFactor() > 0) {
                if (node.getLeft().getBalanceFactor() < 0) {
                    node.setLeft(rotateLeft(node.getLeft()));
                }
                node = rotateRight(node);
            } else {
                if (node.getRight().getBalanceFactor() > 0) {
                    node.setRight(rotateRight(node.getRight()));
                }
                node = rotateLeft(node);
            }
        }
        return node;

    }

    /**
     * a helper method that rotates the function from the node given to the left
     * @param node takes in the node that needs to be rotated
     * @return returns recursively the node that has been rotated
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> other = node.getRight();
        node.setRight(other.getLeft());
        other.setLeft(node);

        updateHAndB(node);
        updateHAndB(other);
        return other;
    }

    /**
     * a helper method that rotates the function from the node given to the right
     * @param node takes in the node that needs to be rotated
     * @return returns recursively the node that has been rotated
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> other = node.getLeft();
        node.setLeft(other.getRight());
        other.setRight(node);

        updateHAndB(node);
        updateHAndB(other);
        return other;
    }

    /**
     * another helper method that recalculates the height and balance height/factor of the nodes in the AVL Tree
     * @param curr the current node that is being updated
     */
    private void updateHAndB(AVLNode<T> curr) {
        if (curr.getRight() == null && curr.getLeft() == null) {
            curr.setHeight(0);
            curr.setBalanceFactor(0);
        } else if (curr.getLeft() != null && curr.getRight() != null) {
            curr.setHeight(Math.max(curr.getRight().getHeight(), curr.getLeft().getHeight()) + 1);
            curr.setBalanceFactor(curr.getLeft().getHeight() - curr.getRight().getHeight());
        } else if (curr.getRight() == null && curr.getLeft() != null) {
            curr.setHeight(curr.getLeft().getHeight() + 1);
            curr.setBalanceFactor(curr.getLeft().getHeight() + 1);
        } else {
            curr.setHeight(curr.getRight().getHeight() + 1);
            curr.setBalanceFactor(-1 - curr.getRight().getHeight());
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data being searched for is null");
        }
        return getHelp(root, data);
    }

    /**
     *
     * @param node node recursively checked by  method
     * @param data the data being searched for
     * @return returns node with data or throws exception
     */
     private T getHelp(AVLNode<T> node, T data) {
        if (node == null) {
            throw new java.util.NoSuchElementException("The data isn't in the AVL Tree");
        } else {
            if (data.compareTo(node.getData()) < 0) {
                return getHelp(node.getLeft(), data);
            } else if (data.compareTo(node.getData()) > 0) {
                return getHelp(node.getRight(), data);
            } else {
                return node.getData();
            }
        }
    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("the data is null");
        }

        return containHelp(root, data);
    }

    /**
     *
     * @param node the node recursively being checked
     * @param data the data that is being looked for
     * @return true or false depending on if data is found in the AVL
     */
    private boolean containHelp(AVLNode<T> node, T data) {
        if (node == null) {
            return false;
        } else {
            if (data.compareTo(node.getData()) > 0) {
                return containHelp(node.getRight(), data);
            } else if (data.compareTo(node.getData()) < 0) {
                return containHelp(node.getLeft(), data);
            } else if (data.compareTo(node.getData()) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the height of the root of the tree.
     * 
     * Should be O(1). 
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }


    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with 
     * the deepest depth. 
     * 
     * Should be recursive. 
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        return maxHelper(root);
    }

    /**
     *
     * @param curr takes in the current node for the helper function for finding the node on the
     *             max Depth of the AVL Tree
     * @return returns the node that is at the max depth of the AVl Tree
     */
    private T maxHelper(AVLNode<T> curr) {

        if (curr == null) {
            return null;
        } else if (curr.getBalanceFactor() > 0) {
            return maxHelper(curr.getLeft());
        } else if (curr.getBalanceFactor() < 0) {
            return maxHelper(curr.getRight());
        } else {
            if (curr.getHeight() == 0) {
                return curr.getData();
            }
            return maxHelper(curr.getRight());

        }


    }


    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data. 
     * 
     * The second case means the successor node will be one of the node(s) we 
     * traversed left from to find data. Since the successor is the SMALLEST element 
     * greater than data, the successor node is the lowest/last node 
     * we traversed left from on the path to the data node.
     *
     * This should NOT be used in the remove method.
     * 
     * Should be recursive. 
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data is null");
        }
        AVLNode<T> node = new AVLNode<>(null);
        return (sucessorHelper(root, node, data)).getData();
    }

    /**
     *
     * @param curr the current node that the recursive call is on
     * @param holder a node that finds the successor and stroes in this node
     * @param data the data that is being used to find the successor
     * @return returns the node recursively to find the successor
     */
    private AVLNode<T> sucessorHelper(AVLNode<T> curr, AVLNode<T> holder, T data) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("data is not in the tree");
        }
        if (curr.getData().compareTo(data) < 0) {
            return sucessorHelper(curr.getRight(), holder, data);
        } else if (curr.getData().compareTo(data) > 0) {
            holder.setData(curr.getData());
            return sucessorHelper(curr.getLeft(), holder, data);
        } else {
            if (curr.getRight() == null) {
                return holder;
            } else {
                AVLNode<T> successor = curr.getRight();
                while (successor.getLeft() != null) {
                    successor = successor.getLeft();
                }
                return successor;
            }
        }

    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
