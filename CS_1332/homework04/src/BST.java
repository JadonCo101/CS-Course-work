import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The collection of data is null");
        } else {
            for (T element : data) {
                if (element == null) {
                    throw new java.lang.IllegalArgumentException("An element in the collection is null");
                } else {
                    add(element);
                }
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data inputted is invalid or null.");
        } else if (root == null) {
            root = new BSTNode<>(data);
            size++;
        } else {
            addHelp(root, data);
        }
    }

    /**
     *
     * @param node = node that the recursive call is checking
     * @param data = the data attempting to be added to the BST
     */
    private void addHelp(BSTNode<T> node, T data) {
        if (node.getLeft() == null && data.compareTo(node.getData()) < 0) {
            BSTNode<T> addedNode = new BSTNode<>(data);
            node.setLeft(addedNode);
            size++;
        } else if (node.getRight() == null && data.compareTo(node.getData()) > 0) {
            BSTNode<T> addedNode = new BSTNode<>(data);
            node.setRight(addedNode);
            size++;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                addHelp(node.getLeft(), data);
            } else {
                addHelp(node.getRight(), data);
            }
        }

    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data is null");
        }
        BSTNode<T> res = new BSTNode<>(null);

        root = removeHelp(root, data, res);
        return res.getData();
    }

    /**
     *
     * @param node the node we are checking
     * @param data the data we are looking for
     * @param r the return value for the node reference
     * @return the node to return
     */
    private BSTNode<T> removeHelp(BSTNode<T> node, T data, BSTNode<T> r) {
        if (node == null) {
            throw new java.util.NoSuchElementException("The data is not in the BST");
        } else if (data.compareTo(node.getData()) == 0) {
            r.setData(node.getData());

            if (node.getLeft() == null && node.getRight() == null) {
                size--;
                return null;
            } else if (node.getLeft() == null && node.getRight() != null) {
                size--;
                return node.getRight();
            } else if (node.getLeft() != null && node.getRight() == null) {
                size--;
                return node.getLeft();
            } else {
                root = balance(node);
            }

        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelp(node.getLeft(), data, r));
        } else {
            node.setRight(removeHelp(node.getRight(), data, r));
        }
        return node;
    }



    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data you are searching for is not valid or null");
        }
        return (getHelp(root, data)).getData();
    }

    /**
     *
     * @param node node that is recursively checked by the method
     * @param data the data being looked for in the binary search tree
     * @return returns the node that has the specfic data or throws exception
     */
    private BSTNode<T> getHelp(BSTNode<T> node, T data) {
        if (node == null) {
            throw new java.util.NoSuchElementException("The data is not in the BST");
        } else {
            if (data.compareTo(node.getData()) > 0) {
                return getHelp(node.getRight(), data);
            } else if (data.compareTo(node.getData()) < 0) {
                return getHelp(node.getLeft(), data);
            } else {
                return node;
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data inputted is null or invalid");
        }
        return containHelp(root, data);
    }

    /**
     *
     * @param node the node that the recursive call is checking
     * @param data the data that is being searched for in the binary search tree
     * @return true or false depending on if the item is in the list
     */
    private boolean containHelp(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                return containHelp(node.getLeft(), data);
            } else if (data.compareTo(node.getData()) > 0) {
                return containHelp(node.getRight(), data);
            } else if (data.compareTo(node.getData()) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> returnList = new ArrayList<T>();
        preHelp(root, returnList);
        return returnList;
    }
    /**
     *
     * @param curr the current node that the preorder method is checking
     * @param list an Arraylist full of the data from the binary search tree in preorder
     */
    private void preHelp(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            list.add(curr.getData());
            preHelp(curr.getLeft(), list);
            preHelp(curr.getRight(), list);
        }
        return;
    }
    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> returnList = new ArrayList<T>();
        inHelp(root, returnList);
        return returnList;
    }
    /**
     *
     * @param curr the current node that the preorder method is checking
     * @param list an Arraylist full of the data from the binary search tree in inorder
     */
    private void inHelp(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            inHelp(curr.getLeft(), list);
            list.add(curr.getData());
            inHelp(curr.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> returnList = new ArrayList<T>();
        postHelp(root, returnList);
        return returnList;
    }
    /**
     *
     * @param curr the current node that the preorder method is checking
     * @param list an Arraylist full of the data from the binary search tree in postorder
     */
    private void postHelp(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            postHelp(curr.getLeft(), list);
            postHelp(curr.getRight(), list);
            list.add(curr.getData());
        }
    }
    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<>();
        List<T> returnList = new ArrayList<>();

        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> node = q.remove();
            if (node != null) {
                returnList.add(node.getData());
                q.add(node.getLeft());
                q.add(node.getRight());
            }
        }
        return returnList;

    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelp(root);
    }

    /**
     *
     * @param node the node being checked
     * @return the sum of the biggest of the children node
     */
    private int heightHelp(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        return Math.max(heightHelp(node.getLeft()), heightHelp(node.getRight())) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * 
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        ArrayList<T> res = new ArrayList<>();
        maxDepth(root, 0, res);
        return res;
    }

    /**
     *
     * @param n the node we are looking at
     * @param depth the current depth we are on
     * @param res the array to return with max values
     */
    private void maxDepth(BSTNode<T> n, int depth, ArrayList<T> res) {
        if (res.size() <= depth) {
            res.add(null);
        }
        if (res.get(depth) == null) {
            res.set(depth, n.getData());
        } else {
            res.set(depth,
                    res.get(depth).compareTo(n.getData()) >= 0 ? res.get(depth) : n.getData());
        }
        if (n.getLeft() != null) {
            maxDepth(n.getLeft(), depth + 1, res);
        }
        if (n.getRight() != null) {
            maxDepth(n.getRight(), depth + 1, res);
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
    public BSTNode<T> getRoot() {
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
